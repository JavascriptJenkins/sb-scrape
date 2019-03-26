package com.genre.base.scraper.task

import com.genre.base.email.EmailManager
import com.genre.base.scraper.constants.ScrapeConstants
import com.genre.base.scraper.repo.GoalieVORepo
import com.genre.base.scraper.repo.UserVORepo
import com.genre.base.scraper.repo.objects.nhl.GoalieVO
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

        ArrayList<String> emailList = null


        ArrayList<String> userEmails = new ArrayList<>()

        Collection <GoalieVO> goaliesToMarkAsSent = new ArrayList<>()

        // get a collection of all non-sent goalies
        Collection <GoalieVO> goaliesToSend = goalieVORepo.getAllGoaliesByWasSentToAll(0)

        // for some reason hibernate returns an object array with both UserVO AND UserSubscriptionVO
        Object[] goalieUsers =  userVORepo.getAllActiveUsersBySubscriptionTypeID(ScrapeConstants.DFO_GOALIE_SCRAPE_TYPE_INT)

        // Strip out only the UserVO objects from the HQL query that returned us an object array...
        goalieUsers.eachWithIndex { Object[] entry ->
            userEmails.add(entry[0][0].email) // this is a UserVO type, getting the email is dirty but ok
        }

        StringBuilder goalieInfoSb = new StringBuilder()
        goalieInfoSb.append("GOALIES WHO WERE CONFIRMED: ")
        goalieInfoSb.append("||")

        // this loop will produce a list of goalies to mark as sent, and also a string we can send in an email to users
        for(GoalieVO goalieVO : goaliesToSend){

            // if updateDate is after createDate and isConfirmed == 1, then send an email
            if(goalieVO.updateTimeStamp.after(goalieVO.createTimeStamp)
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

        // if it contains this text we know there is meaningful data to send
        if(goalieInfoSb.contains("DATE TIME OF GAME")){
            // for each user, send an email with the goalie object data
            sendGoalieDataToUsers(goalieInfoSb.toString(), userEmails, goaliesToMarkAsSent)
        }
    }

    static void appendStringData(StringBuilder goalieInfoSb, GoalieVO goalieVO){
        goalieInfoSb.append("--------------------------------------------")
        goalieInfoSb.append("NAME: ")
        goalieInfoSb.append(System.getProperty("line.separator"))
        goalieInfoSb.append(goalieVO.name)
        goalieInfoSb.append("DATE TIME OF GAME: ")
        goalieInfoSb.append(System.getProperty("line.separator"))
        goalieInfoSb.append(goalieVO.dateTimeOfGame)
        goalieInfoSb.append("--------------------------------------------")
        goalieInfoSb.append(System.getProperty("line.separator"))

    }

    void sendGoalieDataToUsers(String goalieEmailData, ArrayList userEmails, ArrayList<GoalieVO> goaliesToMarkAsSent){

        try {

            emailManager.generateAndSendEmail(goalieEmailData, userEmails)

            for(GoalieVO obj : goaliesToMarkAsSent){
                // update each object so it doesn't get set again
                goalieVORepo.updateGoalieVOToggle(1, obj.id)
            }

        } catch (Exception ex){
            logger.error('Caught exception in goalie mail sending loop: ',ex)
        }

    }

}
