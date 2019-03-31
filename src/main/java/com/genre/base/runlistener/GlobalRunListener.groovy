package com.genre.base.runlistener

import com.genre.base.email.EmailManager
import com.genre.base.scraper.ChromeDriverManager
import com.genre.base.scraper.DatapointFinder
import com.genre.base.scraper.DfoGoalieScrape
import com.genre.base.scraper.MLBcomScrape
import com.genre.base.scraper.ScrapeManager
import com.genre.base.scraper.executers.ExecuteGoalieScrape
import com.genre.base.scraper.impl.DfoGoalieScrapeImpl
import com.genre.base.scraper.repo.GoalieVORepo
import com.genre.base.scraper.repo.NhlGameVORepo
import com.genre.base.scraper.repo.ScrapeAuditVORepo
import com.genre.base.scraper.repo.ScrapeTypeVORepo
import com.genre.base.scraper.repo.SubscriptionVORepo
import com.genre.base.scraper.repo.UserVORepo
import com.genre.base.scraper.task.ScrapeMailerTask
import com.genre.base.scraper.testdata.TestData
import com.genre.base.utilities.SysUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.core.env.Environment
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
    NhlGameVORepo nhlGameVORepo

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

    @Autowired
    DatapointFinder datapointFinder

    @Autowired
    Environment environment

    @Autowired
    TestData testData

    @Autowired
    SubscriptionVORepo subscriptionVORepo

    @Autowired
    ScrapeAuditVORepo scrapeAuditVORepo

    @Autowired
    ScrapeTypeVORepo scrapeTypeVORepo

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

        testData.insertOfficialData()

        // insert test/initialization data
        testData.insertUserSubscriptionTestData()

        threadPoolTaskScheduler.schedule(new DfoGoalieScrapeImpl(
                scrapeManager:scrapeManager,
                goalieVORepo:goalieVORepo,
                nhlGameVORepo:nhlGameVORepo,
                sysUtil:sysUtil,
                chromeDriverManager:chromeDriverManager,
                executeGoalieScrape:executeGoalieScrape,
                datapointFinder: datapointFinder,
                environment: environment,
                scrapeAuditVORepo: scrapeAuditVORepo,
                scrapeTypeVORepo: scrapeTypeVORepo), periodicTrigger)
//
//
        threadPoolTaskScheduler.schedule(new ScrapeMailerTask(
                goalieVORepo:goalieVORepo,
                emailManager: emailManager,
                sysUtil:sysUtil,
                userVORepo:userVORepo,
                subscriptionVORepo: subscriptionVORepo), periodicTrigger)


    }

    // this is a hack to make spring inject beans early
    @PostConstruct
    void loadBeans(){
        println("LOADING BEANS BABY")
    }
}
