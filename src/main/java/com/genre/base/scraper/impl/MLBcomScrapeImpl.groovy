package com.genre.base.scraper.impl

import com.genre.base.objects.ScraperObject
import com.genre.base.scraper.MLBcomScrape
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.SearchObject
import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.objects.LastGamesVO
import com.genre.base.scraper.objects.page.MLBPlayerPage
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class MLBcomScrapeImpl implements MLBcomScrape {


    @Autowired
    ScrapeManager scrapeManager

    private final Logger logger = LoggerFactory.getLogger(this.getClass())


    int searchObjectCounter = 0;
    int maxRunCounter = 0;
    int maxRun = 10;

    ArrayList<SearchObject> searchObjectArrayList = new ArrayList<>();

    ArrayList<String> emailListMLB = new ArrayList<>();

    boolean toggleMLBScrape(boolean activateScraper){

        emailListMLB.add("unwoundcracker@gmail.com")

        SearchObject searchObject = new SearchObject()
        searchObject.setUrl("http://m.angels.mlb.com/player/545361/mike-trout")
        searchObject.setEmailList(emailListMLB)

        searchObjectArrayList.add(searchObject)


        while(activateScraper){

            if(maxRunCounter < maxRun){
                logger.info(ScrapeConstants.MLB_SCRAPE_TYPE+" executed: " + maxRunCounter + " --> times");
                Thread.sleep(4000); // 4 seconds
                if(maxRunCounter >= searchObjectArrayList.size()){
                    searchObjectCounter = 0; // reset the object counter to 0 if the searchObjects index gets too high and will throw null pointer
                }
                executeSeleniumSearch(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList());

//                executeSendToKafkaProducer(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList())

                searchObjectCounter ++;
                maxRunCounter ++;

            } else {
                logger.info(ScrapeConstants.MLB_SCRAPE_TYPE+" scraper ended because it reached maxRuns: " + maxRunCounter)
                break;
            }
        }




    }












    private void executeSeleniumSearch(String url, ArrayList<String> emailList){
        ArrayList<ScraperObject> craigslistObjectsLocalToSearch = new ArrayList<>()
        WebDriver driver = new ChromeDriver()

        initScrape(url,driver) // driver.get(url)

        MLBPlayerPage mlbPlayerPage = new MLBPlayerPage(driver)
        List<WebElement> allTableRows = mlbPlayerPage.allTrElements

        List<LastGamesVO> lastGamesVOList = new ArrayList()

        for(WebElement element:allTableRows){
            if(element.text.contains("Last 7 Games")){
                println(element.text)
                String lastGames = element.text

                ArrayList dataPoints = lastGames.findAll( /\d+/ )*.toString() // split the datapoints by whitespace

                    LastGamesVO lastGamesVO = new LastGamesVO(
                            lastGamesType: dataPoints.get(0),
                            AB:dataPoints.get(1),
                            R:dataPoints.get(2),
                            H:dataPoints.get(3),
                            HR:dataPoints.get(4),
                            RBI:dataPoints.get(5),
                            BB:dataPoints.get(6),
                            SO:dataPoints.get(7),
                            SB:dataPoints.get(8),
                            AVG:"."+dataPoints.get(9), // add decimal
                            OBP:"."+dataPoints.get(10), // add decimal
                            SLG:"."+dataPoints.get(11) // add decimal
                    )
                lastGamesVOList.add(lastGamesVO)
                logger.info("Last7GamesVO: "+lastGamesVO)
            }


            if(element.text.contains("Last 15 Games")){
                println(element.text)
                String lastGames = element.text

                ArrayList dataPoints = lastGames.findAll( /\d+/ )*.toString() // split the datapoints by whitespace

                LastGamesVO lastGamesVO = new LastGamesVO(
                        lastGamesType: dataPoints.get(0),
                        AB:dataPoints.get(1),
                        R:dataPoints.get(2),
                        H:dataPoints.get(3),
                        HR:dataPoints.get(4),
                        RBI:dataPoints.get(5),
                        BB:dataPoints.get(6),
                        SO:dataPoints.get(7),
                        SB:dataPoints.get(8),
                        AVG:"."+dataPoints.get(9), // add decimal
                        OBP:"."+dataPoints.get(10), // add decimal
                        SLG:"."+dataPoints.get(11) // add decimal
                )
                lastGamesVOList.add(lastGamesVO)
                logger.info("Last15GamesVO: "+lastGamesVO)
            }


            if(element.text.contains("Last 30 Games")){
                println(element.text)
                String lastGames = element.text
                ArrayList dataPoints = lastGames.findAll( /\d+/ )*.toString() // split the datapoints by whitespace

                LastGamesVO lastGamesVO = new LastGamesVO(
                        lastGamesType: dataPoints.get(0),
                        AB:dataPoints.get(1),
                        R:dataPoints.get(2),
                        H:dataPoints.get(3),
                        HR:dataPoints.get(4),
                        RBI:dataPoints.get(5),
                        BB:dataPoints.get(6),
                        SO:dataPoints.get(7),
                        SB:dataPoints.get(8),
                        AVG:"."+dataPoints.get(9), // add decimal
                        OBP:"."+dataPoints.get(10), // add decimal
                        SLG:"."+dataPoints.get(11) // add decimal
                )
                lastGamesVOList.add(lastGamesVO)
                logger.info("Last30GamesVO: "+lastGamesVO)
            }

            if(lastGamesVOList.size() >= 3) {
                // break out of the while loop after the app get's the 3 objects it needs
                break
            }

        }

        driver.quit() // kill the browser brah
    }


    void initScrape(String url, ChromeDriver driver){
        try{
            driver.get(url); // goes to a url
        } catch (Exception ex){
            // ignore the exception and try again
            Thread.wait(20000)// wait 20 seconds
            logger.info("CAUGHT A SELENIUM EXCEPTION BRAJ: "+ex)
            scrapeManager.toggleCraigslistScraperProcess(true) // start over
        }
    }

}
