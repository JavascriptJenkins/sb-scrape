package com.genre.base.scraper.executers

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.stereotype.Component

// NOTE: executers do the actual execution of the scrape.
// This layer of abstraction was put in place to seperate the chromium driver from
// the execution flow logic in the above interface.
@Component
interface ExecuteGoalieScrape {

    // this contains try catch block for handling exceptions
    boolean executeGoalieScrape(String url, ChromeDriver driver)

    boolean executeGoalieScrape(String url, RemoteWebDriver driver)

}
