package com.genre.base.scraper.impl

import com.genre.base.scraper.ChromeDriverManager
import com.genre.base.scraper.DfoGoalieScrape
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.executers.ExecuteGoalieScrape
import com.genre.base.scraper.objects.page.DfoGoaliePage
import com.genre.base.scraper.repo.GoalieVORepo
import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.utilities.SysUtil
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class DfoGoalieScrapeImpl implements DfoGoalieScrape , Runnable {


    @Autowired
    ScrapeManager scrapeManager

    @Autowired
    GoalieVORepo goalieVORepo

    @Autowired
    SysUtil sysUtil

    @Autowired
    ChromeDriverManager chromeDriverManager

    @Autowired
    ExecuteGoalieScrape executeGoalieScrape

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    void checkStartingGoalies(){

        logger.info(ScrapeConstants.DFO_GOALIE_SCRAPE_TYPE+" ----> executed. ");
        Thread.sleep(sysUtil.getRandomNumber()); // sleep random between 5-15 seconds

        executeSeleniumSearch(ScrapeConstants.NHL_STARTING_GOALIE_SCRAPE_URL)
    }


    private void executeSeleniumSearch(String url){

        boolean success = false

        ChromeOptions options = new ChromeOptions() // reference ChromeDriverManagerImpl for chrome options you can use


        //        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
//        // options.addArguments("--headless"); // only if you are ACTUALLY running headless
//        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
//        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
//        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc




        // infinite loop
      //  while(2 > 1){
            logger.info('-----> In loop, waiting for scrape to start. ')
            Thread.sleep(sysUtil.getRandomNumber()) // sleep random time before scraping
            logger.info('-----> In loop, waiting over.  Scrape Started. ')
            ChromeDriver driver = null
            driver = chromeDriverManager.getChromeDriver(options)
            success = executeGoalieScrape.executeGoalieScrape(url,driver)
            if(success){
                logger.info('-----> Scrape success.  Extracting data. ')
                extractData(driver)
                chromeDriverManager.quitDriver(driver)
            } else {
                logger.info('-----> Scrape failed.  Trying again. ')
                chromeDriverManager.quitDriver(driver)
            }
       // }
    }

    // extract data from the page and save it to database
    void extractData(ChromeDriver driver){

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

                String goalieText = element.text
                ArrayList dataPoints = goalieText.split("\\r?\\n") // split by /n symbol
                // ArrayList dataPoints = goalieText.findAll( /\n+/ )*.toString()
                logger.info('datapoints split')

                // NOTE: dataPoints has 30 index entries for each element.
                // This seems to be consistent.

                // goalie name, teamName, isConfirmed, dateTimeOfGame

                String gameDateTime = dataPoints[1]
                String goalieName = dataPoints[3]
                String isConfirmed = dataPoints[5]
                int isConfirmedInt = 0

                // if goalie is confirmed, flip integer to true (1)
                if(ScrapeConstants.CONFIRMED.equals(isConfirmed)){
                    isConfirmedInt = 1
                }


                // search to see if we already have this goalie data
                GoalieVO goalieVO = goalieVORepo.findByDateTimeOfGameAndName(gameDateTime, goalieName)

                // if data comes back from the database and isConfirmed == 0, then
                // check to see if isConfirmed == on the scraped data and update it if so
                if(goalieVO != null
                        && goalieVO.isConfirmed == 0
                        && isConfirmedInt == 1){
                    // data needs to be updated - goalie is confirmed now
                    goalieVO.isConfirmed = isConfirmedInt // set to new value from page
                    goalieVO.updateTimeStamp = new Date() // update the timestamp
                    goalieVORepo.save(goalieVO) // save the updated object

                } else if(goalieVO != null
                        && (goalieVO.isConfirmed == isConfirmedInt)){
                    // do nothing - data from page matches data from database
                } else {
                    // no data came back from database - this means we have a new goalie record to insert
                    // save the goalie to database
                    goalieVORepo.save(new GoalieVO(
                            name:goalieName,
                            dateTimeOfGame: gameDateTime,
                            isConfirmed:isConfirmedInt,
                            updateTimeStamp: new Date(),
                            createTimeStamp: new Date()
                    ))
                }

            }
        } catch (Exception ex){
            logger.info('--------> Caught exeption while extracting data.  Ignoring and will try again on next loop. ')
        }

    }

    @Override
    void run() {
        checkStartingGoalies()
    }
}
