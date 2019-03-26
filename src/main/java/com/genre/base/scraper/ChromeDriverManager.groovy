package com.genre.base.scraper

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.stereotype.Component

@Component
interface ChromeDriverManager {

    ChromeDriver getChromeDriver(ChromeOptions options)
    RemoteWebDriver getRemoteChromeDriver(ChromeOptions options)

    void quitDriver(ChromeDriver driver) // this will be called and the end of a successful scrape cycle
    void quitDriverRemote(RemoteWebDriver driver) // this will be called and the end of a successful scrape cycle

}
