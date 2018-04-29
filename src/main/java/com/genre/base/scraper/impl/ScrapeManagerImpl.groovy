package com.genre.base.scraper.impl

import com.genre.base.scraper.CraigslistScrape
import com.genre.base.scraper.MLBcomScrape
import com.genre.base.scraper.ScrapeManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ScrapeManagerImpl implements ScrapeManager {

    @Autowired
    CraigslistScrape craigslistScrape

    @Autowired
    MLBcomScrape mlBcomScrape


    void toggleCraigslistScraperProcess(boolean activateScraper){
        craigslistScrape.toggleCraigslistScraperProcess(activateScraper)
    }



    void toggleMLBScrape(boolean toggle){
        mlBcomScrape.toggleMLBScrape(toggle)
    }


}
