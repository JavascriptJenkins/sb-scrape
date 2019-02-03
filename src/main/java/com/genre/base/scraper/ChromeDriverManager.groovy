package com.genre.base.scraper

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Component

@Component
interface ChromeDriverManager {

    ChromeDriver getChromeDriver(ChromeOptions options)

    void quitDriver(ChromeDriver driver) // this will be called and the end of a successful scrape cycle

}
