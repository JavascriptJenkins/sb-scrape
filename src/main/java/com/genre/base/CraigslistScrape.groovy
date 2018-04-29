package com.genre.base

import org.springframework.stereotype.Component


@Component
interface CraigslistScrape {

    void toggleCraigslistScraperProcess(boolean activateScraper)

}