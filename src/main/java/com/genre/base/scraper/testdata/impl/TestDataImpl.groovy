package com.genre.base.scraper.testdata.impl

import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.repo.ScrapeAuditVORepo
import com.genre.base.scraper.repo.ScrapeTypeVORepo
import com.genre.base.scraper.repo.SubscriptionTypeVORepo
import com.genre.base.scraper.repo.SubscriptionVORepo
import com.genre.base.scraper.repo.UserVORepo
import com.genre.base.scraper.repo.objects.nhl.ScrapeAuditVO
import com.genre.base.scraper.repo.objects.nhl.ScrapeTypeVO
import com.genre.base.scraper.repo.objects.nhl.SubscriptionTypeVO
import com.genre.base.scraper.repo.objects.nhl.SubscriptionVO
import com.genre.base.scraper.repo.objects.nhl.UserVO
import com.genre.base.scraper.testdata.TestData
import com.genre.base.utilities.SysUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class TestDataImpl implements TestData {


    @Autowired
    UserVORepo userVORepo

    @Autowired
    SubscriptionVORepo subscriptionVORepo

    @Autowired
    SubscriptionTypeVORepo subscriptionTypeVORepo

    @Autowired
    ScrapeTypeVORepo scrapeTypeVORepo

    @Autowired
    ScrapeAuditVORepo scrapeAuditVORepo

    @Autowired
    SysUtil sysUtil

    @Autowired
    Environment environment

    private final Logger logger = LoggerFactory.getLogger(this.getClass())



    @Override
    void insertUserSubscriptionTestData() {

        if(Boolean.parseBoolean(environment.getProperty('hydrate.init.data'))){

            logger.info('-----> Hydrating that sweet sweet initialization data. ')


            // create goalie sub type
            SubscriptionTypeVO subscriptionTypeVO = new SubscriptionTypeVO(
                    subscriptionName: "Starting Goalie Updates",
                    subscriptionText: "Get Notified of Starting Goalies as they are confirmed!",
                    priceMonthly: "5.00",
                    priceSeason: "20.00",
                    createTimeStamp: new Date()
            )

            SubscriptionTypeVO resultone = subscriptionTypeVORepo.save(subscriptionTypeVO)
            subscriptionTypeVO.setSubscription_type_id(resultone.getSubscription_type_id())


            //create User subscription
            SubscriptionVO subscriptionVO = new SubscriptionVO(
//                    subscriptionTypeVO: subscriptionTypeVO,
                    active: 1,
                    subscriptionStartDate: new Date(sysUtil.get1971epochMs()),
                    subscriptionEndDate: new Date(sysUtil.get2030epochMs()),
                    createTimeStamp: new Date()
            )

            SubscriptionVO subscriptionVOForNonActiveUser = new SubscriptionVO(
//                    subscriptionTypeVO: subscriptionTypeVO,
                    active: 1,
                    subscriptionStartDate: new Date(sysUtil.get1971epochMs()),
                    subscriptionEndDate: new Date(sysUtil.get2030epochMs()),
                    createTimeStamp: new Date()
            )

            SubscriptionVO subscriptionVOForGrady = new SubscriptionVO(
//                    subscriptionTypeVO: subscriptionTypeVO,
                    active: 1,
                    subscriptionStartDate: new Date(sysUtil.get1971epochMs()),
                    subscriptionEndDate: new Date(sysUtil.get2030epochMs()),
                    createTimeStamp: new Date()
            )

            SubscriptionVO resultForGrady = subscriptionVORepo.save(subscriptionVOForGrady)
            subscriptionVOForGrady.setSubscription_id(resultForGrady.getSubscription_id())
            subscriptionVOForGrady.setSubscriptionTypeVO(subscriptionTypeVO)

            SubscriptionVO resultForNonActiveUser = subscriptionVORepo.save(subscriptionVOForNonActiveUser)
            subscriptionVOForNonActiveUser.setSubscription_id(resultForNonActiveUser.getSubscription_id())
            subscriptionVOForNonActiveUser.setSubscriptionTypeVO(subscriptionTypeVO)

            SubscriptionVO result = subscriptionVORepo.save(subscriptionVO)
            subscriptionVO.setSubscription_id(result.getSubscription_id())
            subscriptionVO.setSubscriptionTypeVO(subscriptionTypeVO)

            // create users
            UserVO userVO = new UserVO(
                    username: "testuser1",
                    email: "unwoundcracker@gmail.com",
                    password: "password",
                    active: 1,
                    createTimeStamp: new Date()
            )

            UserVO resultUser = userVORepo.save(userVO)

            ArrayList list = new ArrayList()
            subscriptionVO.setUserVO(resultUser)
            list.add(subscriptionVO)

            resultUser.setSubscriptionVOList(list)
            userVORepo.save(resultUser)
            
            // save user with non active
            UserVO userVO2 = new UserVO(
                    username: "testuser2",
                    email: "bademail@gmail.com",
                    password: "password",
                    active: 0,
                    createTimeStamp: new Date()
            )
            
            UserVO resultUserNonActive = userVORepo.save(userVO2)
            subscriptionVOForNonActiveUser.setUserVO(resultUserNonActive)
            list.clear()
            list.add(subscriptionVOForNonActiveUser)

            resultUserNonActive.setSubscriptionVOList(list)
            userVORepo.save(resultUserNonActive)


            // user for grady
            UserVO userVOGrady = new UserVO(
                    username: "gradytheman",
                    email: "foxxx546@gmail.com",
                    password: "password",
                    active: 1,
                    createTimeStamp: new Date()
            )

            UserVO resultUserGrady = userVORepo.save(userVOGrady)

            subscriptionVOForGrady.setUserVO(resultUserGrady)
            list.clear()
            list.add(subscriptionVOForGrady)

            resultUserGrady.setSubscriptionVOList(list)
            userVORepo.save(resultUserGrady)

        }



    }


    void insertOfficialData(){

        if(Boolean.parseBoolean(environment.getProperty('hydrate.init.data'))) {

            ScrapeTypeVO scrapeTypeVO = new ScrapeTypeVO(
                    name: ScrapeConstants.GOALIE_NOTIFICATION,
                    description: "This will scrape starting nhl goalie site and track when starting goalies change. ",
                    createTimeStamp: new Date()
            )

            ScrapeTypeVO result = scrapeTypeVORepo.save(scrapeTypeVO)
        }
    }
}
