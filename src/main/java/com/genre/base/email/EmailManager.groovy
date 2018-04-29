package com.genre.base.email

import org.springframework.stereotype.Component

import javax.mail.MessagingException
import javax.mail.internet.AddressException


@Component
interface EmailManager {

    void generateAndSendEmail(String dataToSend, ArrayList<String> emailList) throws AddressException, MessagingException

}