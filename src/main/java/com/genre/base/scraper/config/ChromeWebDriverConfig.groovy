package com.genre.base.scraper.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ChromeWebDriverConfig {

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    @Bean
    boolean initChromeDriver(){
//        System.setProperty("webdriver.gecko.driver","/Users/genreboy/Downloads/chromedriver.exe")
        logger.info("INIT CHROME DRIVER SETTINGS")
        System.setProperty("webdriver.gecko.driver","chromedriver/chromedriver")
        return true
    }


}
