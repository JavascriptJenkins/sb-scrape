package com.genre.base.scraper.impl

import com.genre.base.email.EmailManager
import com.genre.base.email.objects.CraigslistObject
import com.genre.base.scraper.CraigslistScrape
import com.genre.base.objects.ScraperObject
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.SearchObject
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class CraigslistScrapeImpl implements CraigslistScrape {


    ScrapeManager scrapeManager

    int searchObjectCounter = 0
    int maxRunCounter = 0
    int maxRun = 10

    ArrayList<SearchObject> searchObjectArrayList = new ArrayList<>()

    ArrayList<String> emailListCars = new ArrayList<>()

    @Autowired
    EmailManager emailManager



    void toggleCraigslistScraperProcess(boolean activateScraper){

        //emailListCars.add("cam.malia92@gmail.com")
        emailListCars.add("unwoundcracker@gmail.com")
        emailListCars.add("mcmahoncj@gmail.com")

        SearchObject searchObject = new SearchObject()
        searchObject.setUrl("https://portland.craigslist.org/search/cta?query=mazda+cx5+2016&min_price=10000&max_price=16000")
        searchObject.setEmailList(emailListCars)

        SearchObject searchObject1 = new SearchObject()
        searchObject1.setUrl("https://portland.craigslist.org/search/cta?query=mazda+cx5+2015&min_price=10000&max_price=16000")
        searchObject1.setEmailList(emailListCars)

        SearchObject searchObject2 = new SearchObject()
        searchObject2.setUrl("https://sacramento.craigslist.org/search/cta?query=2015+mazda+cx5&searchNearby=2&nearbyArea=187&nearbyArea=373&nearbyArea=96&nearbyArea=1&nearbyArea=97&nearbyArea=456&min_price=10000&max_price=18000")
        searchObject2.setEmailList(emailListCars)

        SearchObject searchObject3 = new SearchObject()
        searchObject3.setUrl("https://sacramento.craigslist.org/search/cta?query=2016+mazda+cx5&searchNearby=2&nearbyArea=187&nearbyArea=373&nearbyArea=96&nearbyArea=1&nearbyArea=97&nearbyArea=456&min_price=10000&max_price=18000")
        searchObject3.setEmailList(emailListCars)

        SearchObject searchObject4 = new SearchObject()
        searchObject4.setUrl("https://losangeles.craigslist.org/search/cta?query=2016+mazda+cx5&searchNearby=2&nearbyArea=63&nearbyArea=104&nearbyArea=103&nearbyArea=209&nearbyArea=62&nearbyArea=208&min_price=10000&max_price=18000")
        searchObject4.setEmailList(emailListCars)

        SearchObject searchObject5 = new SearchObject()
        searchObject5.setUrl("https://losangeles.craigslist.org/search/cta?query=2015+mazda+cx5&searchNearby=2&nearbyArea=63&nearbyArea=104&nearbyArea=103&nearbyArea=209&nearbyArea=62&nearbyArea=208&min_price=10000&max_price=18000")
        searchObject5.setEmailList(emailListCars)

        SearchObject searchObject6 = new SearchObject()
        searchObject6.setUrl("https://austin.craigslist.org/search/cta?query=2016+mazda+cx5&min_price=10000&max_price=18000")
        searchObject6.setEmailList(emailListCars)

        SearchObject searchObject7 = new SearchObject()
        searchObject7.setUrl("https://seattle.craigslist.org/search/cta?query=2015+mazda+cx5&searchNearby=2&nearbyArea=217&nearbyArea=471&nearbyArea=466&nearbyArea=461&nearbyArea=177&nearbyArea=325&min_price=10000&max_price=18000")
        searchObject7.setEmailList(emailListCars)

        SearchObject searchObject8 = new SearchObject()
        searchObject8.setUrl("https://sfbay.craigslist.org/search/cta?query=2015+mazda+cx5&min_price=10000&max_price=18000")
        searchObject8.setEmailList(emailListCars)

        SearchObject searchObject9 = new SearchObject()
        searchObject9.setUrl("https://sfbay.craigslist.org/search/cta?query=2016+mazda+cx5&min_price=10000&max_price=18000")
        searchObject9.setEmailList(emailListCars)

        SearchObject searchObject10 = new SearchObject()
        searchObject10.setUrl("https://newyork.craigslist.org/search/cto?query=mazda+cx5+2015&searchNearby=2&nearbyArea=349&nearbyArea=249&nearbyArea=561&nearbyArea=250&nearbyArea=168&nearbyArea=170&min_price=10000&max_price=18000")
        searchObject10.setEmailList(emailListCars)

        searchObjectArrayList.add(searchObject)
        searchObjectArrayList.add(searchObject2)
        searchObjectArrayList.add(searchObject3)
        searchObjectArrayList.add(searchObject4)
        searchObjectArrayList.add(searchObject5)
        searchObjectArrayList.add(searchObject6)
        searchObjectArrayList.add(searchObject7)
        searchObjectArrayList.add(searchObject8)
        searchObjectArrayList.add(searchObject9)
        searchObjectArrayList.add(searchObject10)



        while(activateScraper){

            if(maxRunCounter < maxRun){
                println("scrape executed: " + maxRunCounter + " --> times")
                Thread.sleep(4000) // 4 seconds
                if(maxRunCounter >= searchObjectArrayList.size()){
                    searchObjectCounter = 0 // reset the object counter to 0 if the searchObjects index gets too high and will throw null pointer
                }
                executeSeleniumSearch(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList())

//                executeSendToKafkaProducer(searchObjectArrayList.get(searchObjectCounter).getUrl(), searchObjectArrayList.get(searchObjectCounter).getEmailList())

                searchObjectCounter ++
                maxRunCounter ++

            } else {
                println("scraper ended because it reached maxRuns: " + maxRunCounter)
                break;
            }
        }
    }



    private void executeSeleniumSearch(String url, ArrayList<String> emailList){
        ArrayList<ScraperObject> craigslistObjectsLocalToSearch = new ArrayList<>()
        //System.setProperty("webdriver.gecko.driver","/Users/genreboy/Downloads/chromedriver.exe");

        int upperBound = 2000
        int lowerBound = 500

        Random random = new Random()
        int randomNumber = random.nextInt(upperBound - lowerBound) + lowerBound

        Thread.sleep(randomNumber)
        WebDriver driver = new ChromeDriver()
        try{
            driver.get(url) // goes to a url
        } catch (Exception ex){
            // ignore the exception and try again
            Thread.wait(20000)// wait 20 seconds
            println("CAUGHT A SELENIUM EXCEPTION BRAJ: "+ex)
            scrapeManager.toggleCraigslistScraperProcess(true) // start over
        }

        // get list of all craigslist search element results
        List<WebElement> resultElements = driver.findElementsByClassName("result-row")
        WebElement titleElement
        WebElement priceElement

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

                // filter out crap
                if(titleElement.getText().contains("- FINANCE ONLINE")){
                    // do nothing
                }else{
                    // add to the list to send to the email recipients
                    craigslistObjectsLocalToSearch.add(new ScraperObject(price:priceElement.getText(), postTitle: titleElement.getText(), url: titleElement.getAttribute("href")))
                }


                // add to database
                //craigslistObjectDao.save(new ScraperObject(price:priceElement.getText(), postTitle: titleElement.getText(), url: titleElement.getAttribute("href")))
            }
        }

        // send the email of the results
        String emailBody = emailManager.formatCraigslistObjectsToEmailHTML(craigslistObjectsLocalToSearch)
        emailManager.generateAndSendEmail(emailBody, emailList) // takes a string as the body content of email (html formatted)

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
