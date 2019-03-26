package com.genre.base.scraper.executers.impl

import com.genre.base.scraper.executers.ExecuteGoalieScrape
import org.openqa.selenium.WebDriverException
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

import java.lang.reflect.InvocationTargetException
import java.util.concurrent.TimeUnit
import org.openqa.selenium.TimeoutException

@Component
class ExecuteGoalieScrapeImpl implements ExecuteGoalieScrape{

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    // Returning true means what happened was good and thread will continue.
    // Returning false means something bad happened and the calling class
    // will kill the thread and try it again.
    boolean executeGoalieScrape(String url, ChromeDriver driver){

        try {
            // Set the page load timeout to 5 seconds.
            // Timeout is here so it doesnt wait for video javascript crap to load
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

            driver.get(url); // goes to a url
            return true

        } catch (TimeoutException e) {
            // Ignore the exception.
            logger.info('Ignoring timeout exception')
            return true // return true here because a timeout is expected behavior
        } catch (IllegalMonitorStateException imex){
            logger.info("CAUGHT IllegalMonitorStateException EXCEPTION")
            closeDriver(driver)
            return false
        } catch(ConnectException conex){
            logger.info("CAUGHT ConnectException")
            closeDriver(driver)
            return false
        } catch(InvocationTargetException invoex){
            logger.info("CAUGHT InvocationTargetException")
            closeDriver(driver)
        } catch(WebDriverException wdex){
            logger.info("CAUGHT WebDriverException")
            closeDriver(driver)
            return false
        } catch (Exception ex){
            // ignore the exception
            logger.info("CAUGHT GENERAL EXCEPTION")
            closeDriver(driver)
            return false
        }
    }

    boolean executeGoalieScrape(String url, RemoteWebDriver driver){

        try {
            // Set the page load timeout to 5 seconds.
            // Timeout is here so it doesnt wait for video javascript crap to load
            driver.manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);

            driver.get(url); // goes to a url
            return true

        } catch (TimeoutException e) {
            // Ignore the exception.
            logger.info('Ignoring timeout exception')
            return true // return true here because a timeout is expected behavior
        } catch (IllegalMonitorStateException imex){
            logger.info("CAUGHT IllegalMonitorStateException EXCEPTION")
            closeDriver(driver)
            return false
        } catch(ConnectException conex){
            logger.info("CAUGHT ConnectException")
            closeDriver(driver)
            return false
        } catch(InvocationTargetException invoex){
            logger.info("CAUGHT InvocationTargetException")
            closeDriver(driver)
        } catch(WebDriverException wdex){
            logger.info("CAUGHT WebDriverException")
            closeDriver(driver)
            return false
        } catch (Exception ex){
            // ignore the exception
            logger.info("CAUGHT GENERAL EXCEPTION")
            closeDriver(driver)
            return false
        }
    }

    boolean closeDriver(driver){
        try{
            logger.info('SESSIONID: ',((RemoteWebDriver)driver).getSessionId().toString())
            if(((RemoteWebDriver)driver).getSessionId().toString().length() > 1){
                driver.close()
                return true
            }
        } catch (Exception ex){
            logger.info("CAUGHT EXCEPTION TRYING TO CLOSE DRIVER")
            return false
        }
    }

}
