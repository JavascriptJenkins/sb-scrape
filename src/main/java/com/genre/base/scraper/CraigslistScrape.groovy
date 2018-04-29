package com.genre.base.scraper

import org.springframework.stereotype.Component


@Component
interface CraigslistScrape {

    void toggleCraigslistScraperProcess(boolean activateScraper)

}