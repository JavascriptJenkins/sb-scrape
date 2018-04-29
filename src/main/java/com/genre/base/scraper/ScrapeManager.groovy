package com.genre.base.scraper

import org.springframework.stereotype.Component


@Component
interface ScrapeManager {

    void toggleCraigslistScraperProcess(boolean toggle)
    void toggleMLBScrape(boolean toggle)





}