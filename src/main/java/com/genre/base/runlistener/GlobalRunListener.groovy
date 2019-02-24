package com.genre.base.runlistener

import com.genre.base.email.EmailManager
import com.genre.base.scraper.ChromeDriverManager
import com.genre.base.scraper.DfoGoalieScrape
import com.genre.base.scraper.MLBcomScrape
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.executers.ExecuteGoalieScrape
import com.genre.base.scraper.impl.DfoGoalieScrapeImpl
import com.genre.base.scraper.repo.GoalieVORepo
import com.genre.base.scraper.repo.UserVORepo
import com.genre.base.scraper.task.ScrapeMailerTask
import com.genre.base.utilities.SysUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.scheduling.support.PeriodicTrigger
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct
import java.util.concurrent.TimeUnit


@Component
class GlobalRunListener implements ApplicationListener<ApplicationReadyEvent>{

    @Autowired
    MLBcomScrape mlBcomScrape

    @Autowired
    DfoGoalieScrape dfoGoalieScrape

    @Autowired
    ThreadPoolTaskScheduler threadPoolTaskScheduler

    @Autowired
    ScrapeManager scrapeManager

    @Autowired
    GoalieVORepo goalieVORepo

    @Autowired
    SysUtil sysUtil

    @Autowired
    ChromeDriverManager chromeDriverManager

    @Autowired
    ExecuteGoalieScrape executeGoalieScrape

    @Autowired
    EmailManager emailManager

    @Autowired
    ScrapeMailerTask scrapeMailerTask

    @Autowired
    UserVORepo userVORepo

    @Override
    void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        println("THE APP STARTED OMG")
        println("STARTING GOALIE SCRAPE")
        init()
    }

    void init(){

        //dfoGoalieScrape.checkStartingGoalies()
        PeriodicTrigger periodicTrigger = new PeriodicTrigger(1000, TimeUnit.MICROSECONDS)
        periodicTrigger.setFixedRate(false)


        threadPoolTaskScheduler.schedule(new DfoGoalieScrapeImpl(
                scrapeManager:scrapeManager,
                goalieVORepo:goalieVORepo,
                sysUtil:sysUtil,
                chromeDriverManager:chromeDriverManager,
                executeGoalieScrape:executeGoalieScrape), periodicTrigger)


        threadPoolTaskScheduler.schedule(new ScrapeMailerTask(
                goalieVORepo:goalieVORepo,
                emailManager: emailManager,
                sysUtil:sysUtil,
                userVORepo:userVORepo), periodicTrigger)


    }

    // this is a hack to make spring inject beans early
    @PostConstruct
    void loadBeans(){
        println("LOADING BEANS BABY")
    }
}
