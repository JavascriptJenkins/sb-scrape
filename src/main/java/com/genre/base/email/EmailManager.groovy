package com.genre.base.email

import com.genre.base.email.objects.CraigslistObject
import com.genre.base.objects.ScraperObject
import org.springframework.stereotype.Component

import javax.mail.MessagingException
import javax.mail.internet.AddressException


@Component
interface EmailManager {

    void generateAndSendEmail(String dataToSend, ArrayList<String> emailList) throws AddressException, MessagingException

    String formatCraigslistObjectsToEmailHTML(ArrayList<ScraperObject> craigslistObjects)

}