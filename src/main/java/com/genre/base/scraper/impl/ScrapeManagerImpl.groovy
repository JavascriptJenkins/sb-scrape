package com.genre.base.scraper.impl

import com.genre.base.CraigslistScrape
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.SearchObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class ScrapeManagerImpl implements ScrapeManager {

    @Autowired
    CraigslistScrape craigslistScrape


    void toggleCraigslistScraperProcess(boolean activateScraper){
        craigslistScrape.toggleCraigslistScraperProcess(activateScraper)
    }






}
