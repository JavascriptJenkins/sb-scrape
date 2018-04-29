package com.genre.base.scraper.impl

import com.genre.base.CraigslistScrape
import com.genre.base.objects.ScraperObject
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.SearchObject
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.stereotype.Component


@Component
class CraigslistScrapeImpl implements CraigslistScrape {


    ScrapeManager scrapeManager


    void toggleCraigslistScraperProcess(boolean activateScraper){
        int searchObjectCounter = 0;
        int maxRunCounter = 0;
        int maxRun = 10;
        ArrayList<SearchObject> searchObjectArrayList = new ArrayList<>();

        ArrayList<String> emailListCars = new ArrayList<>();
        emailListCars.add("cam.malia92@gmail.com")
        emailListCars.add("unwoundcracker@gmail.com")

        SearchObject searchObject = new SearchObject()
        searchObject.setUrl("https://minneapolis.craigslist.org/search/sso?query=audi+a8l&max_price=5000")
        searchObject.setEmailList(emailListCars)

        SearchObject searchObject1 = new SearchObject()
        searchObject1.setUrl("https://minneapolis.craigslist.org/search/sso?query=bmw+convertible&max_price=5000")
        searchObject1.setEmailList(emailListCars)

        SearchObject searchObject2 = new SearchObject()
        searchObject2.setUrl("https://minneapolis.craigslist.org/search/sso?query=saab+convertible&max_price=5000")
        searchObject2.setEmailList(emailListCars)

        searchObjectArrayList.add(searchObject)
        searchObjectArrayList.add(searchObject1)
        searchObjectArrayList.add(searchObject2)



        while(activateScraper){

            if(maxRunCounter < maxRun){
                println("scrape executed: " + maxRunCounter + " --> times");
                Thread.sleep(4000); // 4 seconds
                if(maxRunCounter >= searchObjectArrayList.size()){
                    searchObjectCounter = 0; // reset the object counter to 0 if the searchObjects index gets too high and will throw null pointer
                }
                executeSeleniumSearch(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList());

//                executeSendToKafkaProducer(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList())

                searchObjectCounter ++;
                maxRunCounter ++;

            } else {
                println("scraper ended because it reached maxRuns: " + maxRunCounter)
                break;
            }
        }
    }



    private void executeSeleniumSearch(String url, ArrayList<String> emailList){
        ArrayList<ScraperObject> craigslistObjectsLocalToSearch = new ArrayList<>()
        //System.setProperty("webdriver.gecko.driver","/Users/genreboy/Downloads/chromedriver.exe");
        WebDriver driver = new ChromeDriver()
        try{
            driver.get(url); // goes to a url
        } catch (Exception ex){
            // ignore the exception and try again
            Thread.wait(20000)// wait 20 seconds
            println("CAUGHT A SELENIUM EXCEPTION BRAJ: "+ex)
            scrapeManager.toggleCraigslistScraperProcess(true) // start over
        }

        // get list of all craigslist search element results
        List<WebElement> resultElements = driver.findElementsByClassName("result-row")
        WebElement titleElement;
        WebElement priceElement;

        for(WebElement element: resultElements){
            if(isElementPresent(By.className("result-title"), element)){
                titleElement = element?.findElement(By.className("result-title"))
            }

            if(isElementPresent(By.className("result-price"), element)){
                priceElement = element?.findElement(By.className("result-price"))
            }

            if(titleElement && priceElement != null){
                println("Title: "+titleElement.getText())
                println("Price: "+priceElement.getText())
                println("Title URL: "+titleElement.getAttribute("href")) // parses out the link
                //println(element.getText())

                // add to the list to send to the email recipients
                craigslistObjectsLocalToSearch.add(new ScraperObject(price:priceElement.getText(), postTitle: titleElement.getText(), url: titleElement.getAttribute("href")))

                // add to database
                //craigslistObjectDao.save(new ScraperObject(price:priceElement.getText(), postTitle: titleElement.getText(), url: titleElement.getAttribute("href")))
            }
        }

        // send the email of the results
//        String emailBody = formatCraigslistObjectsToEmailHTML(craigslistObjectsLocalToSearch)
//        generateAndSendEmail(emailBody, emailList) // takes a string as the body content of email (html formatted)

        driver.quit() // kill the browser brah
    }

    static boolean isElementPresent(By by, WebElement element)
    {
        boolean present
        try {
            element.findElement(by)
            present = true
        } catch (Exception e) {
            present = false
        }
        return present
    }









}
