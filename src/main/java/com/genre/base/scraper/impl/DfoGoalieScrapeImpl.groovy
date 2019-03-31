package com.genre.base.scraper.impl

import com.genre.base.scraper.ChromeDriverManager
import com.genre.base.scraper.DatapointFinder
import com.genre.base.scraper.DfoGoalieScrape
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.executers.ExecuteGoalieScrape
import com.genre.base.scraper.objects.page.DfoGoaliePage
import com.genre.base.scraper.repo.GoalieVORepo
import com.genre.base.scraper.repo.NhlGameVORepo
import com.genre.base.scraper.repo.ScrapeAuditVORepo
import com.genre.base.scraper.repo.ScrapeTypeVORepo
import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.scraper.repo.objects.nhl.NhlGameVO
import com.genre.base.scraper.repo.objects.nhl.ScrapeAuditVO
import com.genre.base.scraper.repo.objects.nhl.ScrapeTypeVO
import com.genre.base.utilities.SysUtil
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component


@Component
class DfoGoalieScrapeImpl implements DfoGoalieScrape , Runnable {


    @Autowired
    ScrapeManager scrapeManager

    @Autowired
    GoalieVORepo goalieVORepo

    @Autowired
    NhlGameVORepo nhlGameVORepo

    @Autowired
    ScrapeAuditVORepo scrapeAuditVORepo

    @Autowired
    ScrapeTypeVORepo scrapeTypeVORepo

    @Autowired
    SysUtil sysUtil

    @Autowired
    ChromeDriverManager chromeDriverManager

    @Autowired
    ExecuteGoalieScrape executeGoalieScrape

    @Autowired
    DatapointFinder datapointFinder

    @Autowired
    Environment environment

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    void checkStartingGoalies(){

        logger.info(ScrapeConstants.DFO_GOALIE_SCRAPE_TYPE+" ----> executed. ");
        Thread.sleep(sysUtil.getRandomNumber()); // sleep random between 5-15 seconds

        executeSeleniumSearch(ScrapeConstants.NHL_STARTING_GOALIE_SCRAPE_URL)
    }


    private void executeSeleniumSearch(String url){

       // Optional<ScrapeTypeVO> optional = scrapeTypeVORepo.findById(ScrapeConstants.GOALIE_NOTIFICATION_ST)

        // create audit object
        ScrapeAuditVO scrapeAuditVO = new ScrapeAuditVO(
                start: new Date(),
                createTimeStamp: new Date())

        // save it and assign id
        ScrapeAuditVO result = scrapeAuditVORepo.save(scrapeAuditVO)
        scrapeAuditVO.setScrape_audit_id(result.getScrape_audit_id())
        scrapeAuditVO.setScrapeTypeVO(new ScrapeTypeVO(scrape_type_id: ScrapeConstants.GOALIE_NOTIFICATION_ST))


        boolean success = false

        ChromeOptions options = null
        boolean runningLocal = false

        // if running headless, we assume we are running in remote selenium grid
        if(Boolean.parseBoolean(environment.getProperty('run.headless'))){
            options = getRemoteDriverOptions()
        } else {
            options = getLocalDriverOptions()
            runningLocal = true
        }


        // infinite loop
      //  while(2 > 1){
            logger.info('-----> In loop, waiting for scrape to start. ')
            Thread.sleep(sysUtil.getRandomNumber()) // sleep random time before scraping
            logger.info('-----> In loop, waiting over.  Scrape Started. ')
            RemoteWebDriver driver = null
            //ChromeDriver driver = null
            if(runningLocal){
                driver = chromeDriverManager.getChromeDriver(options)
            } else {
                driver = chromeDriverManager.getRemoteChromeDriver(options)
            }

            success = executeGoalieScrape.executeGoalieScrape(url,driver)
            if(success){
                logger.info('-----> Scrape success.  Extracting data. ')
                extractData(driver, scrapeAuditVO)
                scrapeAuditVO.setSuccess(success)
                scrapeAuditVO.setEnd(new Date())
                scrapeAuditVO.setDuration(new Date(scrapeAuditVO.getEnd().getTime() - scrapeAuditVO.getStart().getTime()))
                scrapeAuditVO.setUpdateTimeStamp(new Date())
                scrapeAuditVORepo.save(scrapeAuditVO)
                chromeDriverManager.quitDriverRemote(driver)
            } else {
                logger.info('-----> Scrape failed.  Trying again. ')
                scrapeAuditVO.setSuccess(success)
                scrapeAuditVO.setUpdateTimeStamp(new Date())
                scrapeAuditVORepo.save(scrapeAuditVO)
                chromeDriverManager.quitDriverRemote(driver)
            }
       // }
    }

    // extract data from the page and save it to database
    void extractData(RemoteWebDriver driver, ScrapeAuditVO scrapeAuditVO){

        try{
            // dfoGoaliePage.startingGoaliesCard[0].text
            DfoGoaliePage dfoGoaliePage = new DfoGoaliePage(driver)

            // This gets the
            dfoGoaliePage.startingGoaliesCard[0].text

            List<WebElement> allGoaliesList = dfoGoaliePage.startingGoaliesCard

            // List<LastGamesVO> lastGamesVOList = new ArrayList()

            // Each item in this list is 2 goalies - 1 for each team
            for(WebElement element : allGoaliesList){

                logger.info('Got another goalie element')
                // each one of these has goalie data for 2 goalies/1 game
                String goalieText = element.text
                ArrayList dataPoints = goalieText.split("\\r?\\n") // split by /n symbol
                // ArrayList dataPoints = goalieText.findAll( /\n+/ )*.toString()
                logger.info('datapoints split')

                // NOTE: dataPoints has 30 index entries for each element.
                // This seems to be consistent.

                // goalie name, teamName, isConfirmed, dateTimeOfGame
                NhlGameVO nhlGameVO = datapointFinder.getGoalieData(dataPoints)

                for(GoalieVO goalieVOFromPage : nhlGameVO.getGoalieVOList() ){
                    runCheck(goalieVOFromPage, nhlGameVO)
                }


            }
        } catch (Exception ex){
            logger.info('--------> Caught exeption while extracting data.  Ignoring and will try again on next loop, '+ex)
            scrapeAuditVO.setSuccess(false)
            scrapeAuditVO.setException(ex.toString())
            scrapeAuditVO.setUpdateTimeStamp(new Date())
            scrapeAuditVORepo.save(scrapeAuditVO)
        }

    }

    @Override
    void run() {
        checkStartingGoalies()
    }


    void runCheck(GoalieVO goalieVOFromPage, NhlGameVO nhlGameVOFromPage){
        // search to see if we already have this goalie data
//        GoalieVO goalieVO =
//                goalieVORepo.findByDateTimeOfGameAndName(gameDateTime, goalieVOFromPage.name)

        NhlGameVO nhlGameVO =
                nhlGameVORepo.findByDateTimeOfGame(nhlGameVOFromPage.getDateTimeOfGame())
        //goalieVOFromPage.setGoalie_id(0)
        // if no existing game, handle the situation


        // if an nhl game comes back, and it has goalies,
        // and those goalies have a different conformation than the
        // page object
        if(nhlGameVO != null){
            for(GoalieVO goalieVO:nhlGameVO.getGoalieVOList()){
                //nhlGameVO.setNhl_game_id(0) // make hibernate happy
                // if data comes back from the database and isConfirmed == 0, then
                // check to see if isConfirmed == on the scraped data and update it if so
                if(goalieVO != null
                        && (goalieVO.isConfirmed == 0 && goalieVOFromPage.isConfirmed == 1)
                        && (goalieVO.goalie_id == goalieVOFromPage.goalie_id)){
                    // data needs to be updated - goalie is confirmed now
                    goalieVO.isConfirmed = goalieVOFromPage.isConfirmed // set to new value from page
                    goalieVO.updateTimeStamp = new Date() // update the timestamp
                    goalieVORepo.save(goalieVO) // save the updated object

                } else if(goalieVO != null
                        && (goalieVO.isConfirmed == goalieVOFromPage.isConfirmed)
                        && (goalieVO.goalie_id == goalieVOFromPage.goalie_id)){
                    // do nothing - data from page matches data from database
                }
            }
        } else {

            nhlGameVOFromPage.setCreateTimeStamp(new Date())

            for(GoalieVO goalieVO : nhlGameVOFromPage.getGoalieVOList()){
                goalieVO.setNhlGameVO(nhlGameVOFromPage)
            }

            NhlGameVO saveResult = nhlGameVORepo.save(nhlGameVOFromPage)
            println(saveResult)


        }




     //   GoalieVO goalieVO = goalieVORepo.findB

    }

    ChromeOptions getLocalDriverOptions(){
        ChromeOptions options = new ChromeOptions() // reference ChromeDriverManagerImpl for chrome options you can use


        options.setHeadless(true)
        //        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        //options.addArguments("command-executor=http://selenium-hub:4444/wd/hub"); // https://stackoverflow.com/a/43840128/1689770
//        options.addArguments("--headless"); // only if you are ACTUALLY running headless
//        options.addArguments("--remote-debugging-port=9222"); // only if you are ACTUALLY running headless
//
//        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--whitelisted-ips="); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
//        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
//        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
        return options
    }


    ChromeOptions getRemoteDriverOptions(){
        ChromeOptions options = new ChromeOptions() // reference ChromeDriverManagerImpl for chrome options you can use


        options.setHeadless(true)
        //        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
        //options.addArguments("command-executor=http://selenium-hub:4444/wd/hub"); // https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--headless"); // only if you are ACTUALLY running headless
        options.addArguments("--remote-debugging-port=9222"); // only if you are ACTUALLY running headless

        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--whitelisted-ips="); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
        return options
    }


}
