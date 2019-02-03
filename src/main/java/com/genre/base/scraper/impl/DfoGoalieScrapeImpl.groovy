package com.genre.base.scraper.impl

import com.genre.base.objects.ScraperObject
import com.genre.base.scraper.DfoGoalieScrape
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.SearchObject
import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.objects.LastGamesVO
import com.genre.base.scraper.objects.page.DfoGoaliePage
import com.genre.base.scraper.objects.page.MLBPlayerPage
import com.genre.base.scraper.repo.GoalieVORepo
import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.utilities.SysUtil
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.lang.reflect.InvocationTargetException
import java.security.Timestamp
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


@Component
class DfoGoalieScrapeImpl implements DfoGoalieScrape {


    @Autowired
    ScrapeManager scrapeManager

    @Autowired
    GoalieVORepo goalieVORepo

    @Autowired
    SysUtil sysUtil

    private final Logger logger = LoggerFactory.getLogger(this.getClass())


    int searchObjectCounter = 0;
    int maxRunCounter = 0;
    int maxRun = 10000;

    ArrayList<SearchObject> searchObjectArrayList = new ArrayList<>();

    ArrayList<String> emailList = new ArrayList<>();

    String CONFIRMED = "Confirmed"


    boolean checkStartingGoalies(){

        emailList.add("unwoundcracker@gmail.com")

        SearchObject searchObject = new SearchObject()
        searchObject.setUrl("https://www.dailyfaceoff.com/starting-goalies/")
        searchObject.setEmailList(emailList)

        searchObjectArrayList.add(searchObject)


        if(maxRunCounter < maxRun){
            logger.info(ScrapeConstants.DFO_GOALIE_SCRAPE_TYPE+" executed: " + maxRunCounter + " --> times");
            Thread.sleep(sysUtil.getRandomNumber()); // sleep random between 5-15 seconds
            if(maxRunCounter >= searchObjectArrayList.size()){
                searchObjectCounter = 0; // reset the object counter to 0 if the searchObjects index gets too high and will throw null pointer
            }
            executeSeleniumSearch(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList());

//                executeSendToKafkaProducer(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList())

            searchObjectCounter ++;
            maxRunCounter ++;

        } else {
            logger.info(ScrapeConstants.MLB_SCRAPE_TYPE+" scraper ended because it reached maxRuns: " + maxRunCounter)
        }


        return true
    }


    private void executeSeleniumSearch(String url, ArrayList<String> emailList){
        ArrayList<ScraperObject> craigslistObjectsLocalToSearch = new ArrayList<>()

        ChromeOptions options = new ChromeOptions()
//        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
//        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
//        // options.addArguments("--headless"); // only if you are ACTUALLY running headless
//        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
//        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
//        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
        ChromeDriver driver = new ChromeDriver(options)


        initScrape(url,driver) // driver.get(url)

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
            if(CONFIRMED.equals(isConfirmed)){
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

        driver.quit() // kill the browser brah
        checkStartingGoalies()
    }


    void initScrape(String url, ChromeDriver driver){

        try{
            // Set the page load timeout to 5 seconds.
            // Timeout is here so it doesnt wait for video javascript crap to load
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

            driver.get(url); // goes to a url
        } catch (TimeoutException e) {
            // Ignore the exception.
            logger.info('Ignoring timeout exception')
        } catch (IllegalMonitorStateException imex){
            logger.info("CAUGHT IllegalMonitorStateException EXCEPTION")
            driver.close()
            reTryScrape()
        } catch(ConnectException conex){
            logger.info("CAUGHT ConnectException")
            fireRetryLoop(driver)
        } catch(InvocationTargetException invoex){
            logger.info("CAUGHT InvocationTargetException")
            fireRetryLoop(driver)
        } catch(WebDriverException wdex){
            logger.info("CAUGHT WebDriverException")
            reTryScrape()
          //  reTryScrape()
        } catch (Exception ex){
            // ignore the exception
            logger.info("CAUGHT GENERAL EXCEPTION")
            driver.close()
            reTryScrape()
        } finally {
            reTryScrape()
        }
    }


    void reTryScrape(){
        checkStartingGoalies()
    }


    // This loop will try calling close over and over until it works...
    void fireRetryLoop(driver){
        try{
            driver.close()
        } catch (Exception ex){
            fireRetryLoop(driver)
        }
    }


}
