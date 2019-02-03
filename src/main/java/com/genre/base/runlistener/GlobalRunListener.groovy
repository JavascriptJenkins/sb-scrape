package com.genre.base.runlistener

import com.genre.base.scraper.DfoGoalieScrape
import com.genre.base.scraper.MLBcomScrape
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct


@Component
class GlobalRunListener implements ApplicationListener<ApplicationReadyEvent>{

    @Autowired
    MLBcomScrape mlBcomScrape

    @Autowired
    DfoGoalieScrape dfoGoalieScrape


    @Override
    void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        println("THE APP STARTED OMG")
        println("STARTING GOALIE SCRAPE")
        init()
    }

    void init(){
        dfoGoalieScrape.checkStartingGoalies()
    }

    // this is a hack to make spring inject beans early
    @PostConstruct
    void loadBeans(){
        println("LOADING BEANS BABY")
    }
}
