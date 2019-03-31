package com.genre.base.scraper.task

import com.genre.base.email.EmailManager
import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.repo.GoalieVORepo
import com.genre.base.scraper.repo.SubscriptionVORepo
import com.genre.base.scraper.repo.UserVORepo
import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.scraper.repo.objects.nhl.SubscriptionVO
import com.genre.base.utilities.SysUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import java.lang.reflect.Array

@Component
class ScrapeMailerTask implements Runnable {

    @Autowired
    EmailManager emailManager

    @Autowired
    GoalieVORepo goalieVORepo

    @Autowired
    UserVORepo userVORepo

    @Autowired
    SysUtil sysUtil

    @Autowired
    SubscriptionVORepo subscriptionVORepo

    private final Logger logger = LoggerFactory.getLogger(this.getClass())


    @Override
    void run() {

        // send the emails to notify people goalies are confirmed starting
        try {
            checkGoalieScrapeSendEmail()
        } catch (Exception ex){
            logger.error('Caught exception in checkGoalieScrapeSendEmail(): ',ex)
        }

    }

    // check to see if any goalie records have been confirmed and send out emails.
    // after each email send, update the record to wasSentToAll

    // send email if (createDate == updateDate && isConfirmed == 1 && isSentToAll == 0), // then flip sent to 1
    // send email else if (createDate != updateDate && isConfirmed = 1) // then flip sent to 1


    // else (do nothing)

    void checkGoalieScrapeSendEmail(){


        logger.info(ScrapeConstants.DFO_GOALIE_EMAIL_NOTIF+" ----> INIT. ");
        Thread.sleep(sysUtil.getRandomNumber()); // sleep random between 5-15 seconds
        logger.info(ScrapeConstants.DFO_GOALIE_EMAIL_NOTIF+" ----> STARTING. ");



        // get a list of all the goalie update subscriptions
        List<SubscriptionVO> sublist =
                subscriptionVORepo.getSubscriptionsByIdOnlyWithActiveUser(ScrapeConstants.DFO_GOALIE_SCRAPE_TYPE_LONG)

        // get a list of all non-sent goalies
        Collection <GoalieVO> nonSentGoalies = goalieVORepo.getAllGoaliesByWasSentToAll(0)

        // create a list to pass into the email interface
        ArrayList<String> userEmails = new ArrayList<>()

        // add all subscribed users to the emailList
        sublist.each { SubscriptionVO item ->
            userEmails.add(item.userVO.email)
        }

        // create a list of goalies to mark as already sent
        Collection <GoalieVO> goaliesToMarkAsSent = new ArrayList<>()


        StringBuilder goalieInfoSb = new StringBuilder()
        goalieInfoSb.append("GOALIES WHO WERE CONFIRMED: ")

        // this loop will produce a list of goalies to mark as sent, and also a string we can send in an email to users
        for(GoalieVO goalieVO : nonSentGoalies){

            // if updateDate is after createDate and isConfirmed == 1, then send an email
            if(goalieVO.updateTimeStamp != null &&
            goalieVO.updateTimeStamp.after(goalieVO.createTimeStamp)
                && goalieVO.isConfirmed == 1
                    && goalieVO.wasSentToAllEmails == 0){
                appendStringData(goalieInfoSb, goalieVO)
                goaliesToMarkAsSent.add(goalieVO)
            } else if(goalieVO.isConfirmed == 1
                    && goalieVO.wasSentToAllEmails == 0) {
                // this means we found a goalie that was already confirmed but hasn't been set
                appendStringData(goalieInfoSb, goalieVO)
                goaliesToMarkAsSent.add(goalieVO)
            }
        }

        // if there is anything to be sent, send it
        if(goaliesToMarkAsSent.size() > 0){
            // for each user, send an email with the goalie object data
            sendGoalieDataToUsers(goalieInfoSb.toString(), userEmails, goaliesToMarkAsSent)
        }
    }

    static void appendStringData(StringBuilder goalieInfoSb, GoalieVO goalieVO){


        goalieInfoSb.append('<div>')
        goalieInfoSb.append('<br>')
        goalieInfoSb.append('GAME INFO: ')
        goalieInfoSb.append(goalieVO.nhlGameVO.gameDesc)
        goalieInfoSb.append('<br>')
        goalieInfoSb.append('NAME: ')
        goalieInfoSb.append(goalieVO.name)
        goalieInfoSb.append('<br>')
        goalieInfoSb.append('TEAM NAME: ')
        goalieInfoSb.append(goalieVO.teamName)
        if(goalieVO.goalieDesc != null){
            goalieInfoSb.append('<br>')
            goalieInfoSb.append('GOALIE DESC: ')
            goalieInfoSb.append(goalieVO.goalieDesc)
        }
        goalieInfoSb.append('<br>')
        goalieInfoSb.append('SOURCE: ')
        goalieInfoSb.append(goalieVO.source)
        goalieInfoSb.append('</div>')




//        goalieInfoSb.append("--------------------------------------------")
//        goalieInfoSb.append("NAME: ")
//        goalieInfoSb.append(System.getProperty("line.separator"))
//        goalieInfoSb.append(goalieVO.name)
//        goalieInfoSb.append("DATE TIME OF GAME: ")
//        goalieInfoSb.append(System.getProperty("line.separator"))
//        goalieInfoSb.append(goalieVO.nhlGameVO.getDateTimeOfGame())
//        goalieInfoSb.append("--------------------------------------------")
//        goalieInfoSb.append(System.getProperty("line.separator"))

    }

    void sendGoalieDataToUsers(String goalieEmailData, ArrayList userEmails, ArrayList<GoalieVO> goaliesToMarkAsSent){

        try {

            emailManager.generateAndSendEmail(goalieEmailData, userEmails, "GOALIES CONFIRMED!")

            for(GoalieVO obj : goaliesToMarkAsSent){
                // update each object so it doesn't get set again
                goalieVORepo.updateGoalieVOToggle(1, obj.getGoalie_id())
            }

        } catch (Exception ex){
            logger.error('Caught exception in goalie mail sending loop: ',ex)
        }

    }

}
