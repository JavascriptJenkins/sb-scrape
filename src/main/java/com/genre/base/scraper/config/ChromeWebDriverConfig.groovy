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
        //System.setProperty("webdriver.chrome.driver","chromedriver")
        String currentDir = System.getProperty("user.dir");
        System.out.println("Current dir using System:" +currentDir);

        URL location = ChromeWebDriverConfig.class.getProtectionDomain().getCodeSource().getLocation();
        System.out.println("CODE SOURCE WHERE ITS LOOKING: "+location.getFile());


        File f = new File("."); // current directory

        File[] files = f.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                System.out.print("directory:");
            } else {
                System.out.print("     file:");
            }
            System.out.println(file.getCanonicalPath());
        }
        return true
    }


}
