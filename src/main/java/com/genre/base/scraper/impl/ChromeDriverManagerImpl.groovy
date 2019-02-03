package com.genre.base.scraper.impl

import com.genre.base.scraper.ChromeDriverManager
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class ChromeDriverManagerImpl implements ChromeDriverManager{

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    // pass in options, get back a new driver
    ChromeDriver getChromeDriver(ChromeOptions options){
        ChromeDriver driver = new ChromeDriver(options)
        return driver
    }

    void quitDriver(ChromeDriver driver){
        try {
            if(driver != null){
                driver.close() // kill the browser brah
            }
        } catch (Exception ex){
            logger.info('----------> Caught exception trying to kill driver at end of successful scrape cycle. ')
        }
    }

}

// NOTE: example driver options here.
//        options.addArguments("start-maximized"); // https://stackoverflow.com/a/26283818/1689770
//        options.addArguments("enable-automation"); // https://stackoverflow.com/a/43840128/1689770
//        // options.addArguments("--headless"); // only if you are ACTUALLY running headless
//        options.addArguments("--no-sandbox"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-infobars"); //https://stackoverflow.com/a/43840128/1689770
//        options.addArguments("--disable-dev-shm-usage"); //https://stackoverflow.com/a/50725918/1689770
//        options.addArguments("--disable-browser-side-navigation"); //https://stackoverflow.com/a/49123152/1689770
//        options.addArguments("--disable-gpu"); //https://stackoverflow.com/questions/51959986/how-to-solve-selenium-chromedriver-timed-out-receiving-message-from-renderer-exc
