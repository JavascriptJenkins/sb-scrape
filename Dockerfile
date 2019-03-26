FROM centos:latest

EXPOSE 8080 8443

# GConf2 libnss3 libgconf-2-4 -> these are for chromedriver
RUN yum upgrade -y&& yum install -y GConf2 libnss3 libgconf-2-4 java-1.8.0-openjdk-devel curl iputils wget net-tools; yum clean headers; yum clean packages; yum clean metadata; yum clean all

RUN wget https://dl.google.com/linux/direct/google-chrome-stable_current_x86_64.rpm && yum localinstall google-chrome-stable_current_x86_64.rpm -y; yum clean headers; yum clean packages; yum clean metadata; yum clean all

#CMD LATEST_VERSION=$(curl -s https://chromedriver.storage.googleapis.com/LATEST_RELEASE) && wget -O /tmp/chromedriver.zip https://chromedriver.storage.googleapis.com/$LATEST_VERSION/chromedriver_linux64.zip && sudo unzip /tmp/chromedriver.zip chromedriver -d /usr/local/bin/;

COPY /target/app.jar /app/
COPY /ubuntu/chromedriver /usr/bin/chromedriver

RUN mkdir /.pki/ && chmod -R ag+w+x /.pki/
RUN chmod -R ag+w+x /app/
RUN chmod -R ag+w+x /usr/bin/chromedriver

EXPOSE 8080
USER 1001

CMD java -jar /app/app.jar

# [1.089][WARNING]: PAC support disabled because there is no system implementation

## [0.713][WARNING]: PAC support disabled because there is no system implementation
##   [0.713][SEVERE]: Failed to create /.pki/nssdb directory.



 ##/usr/bin/chromedriver: error while loading shared libraries: libgconf-2.so.4: cannot open shared object file: No such file or directory

#FROM openjdk:8-jdk-alpine
#
#COPY /target/app.jar /app/
#COPY /ubuntu/chromedriver /usr/bin/chromedriver
#
#RUN chmod -R ag+w+x /app/
#RUN chmod -R ag+w+x /usr/bin/chromedriver
#
#EXPOSE 8080
#USER 1001
#
#CMD java -jar /app/app.jar



#
#2019-03-26 02:53:20.108 ERROR 1 --- [lTaskScheduler2] c.g.base.scraper.task.ScrapeMailerTask   : Caught exception in goalie mail sending loop:
#
#javax.mail.AuthenticationFailedException: 534-5.7.14 <https://accounts.google.com/signin/continue?sarp=1&scc=1&plt=AKgnsbtP
#534-5.7.14 UXUJqxDDJgQ1fbaeAVXXjMiIIB9Wj_troKHiFFVzhbyGZMsdrmZq6eoque02bX9Yct2Uq4
#534-5.7.14 H5XyFLAiPzqPMnWRQm0kny6TOJki5KNmxyApCwRhitxB2Zp_WuA-aMAe4HD95NhqcjdjOP
#534-5.7.14 BRsKCMzngAbWF9UY6WHpBIvZARkOc7ei4s6XwrNx4T4G2qgov4Vyhiqu> Please log
#534-5.7.14 in via your web browser and then try again.
#534-5.7.14  Learn more at
#534 5.7.14  https://support.google.com/mail/answer/78754 f189sm10463455qkb.79 - gsmtp
#
#	at com.sun.mail.smtp.SMTPTransport$Authenticator.authenticate(SMTPTransport.java:826) ~[mail-1.4.7.jar!/:1.4.7]
#	at com.sun.mail.smtp.SMTPTransport.authenticate(SMTPTransport.java:761) ~[mail-1.4.7.jar!/:1.4.7]
#	at com.sun.mail.smtp.SMTPTransport.protocolConnect(SMTPTransport.java:685) ~[mail-1.4.7.jar!/:1.4.7]
#	at javax.mail.Service.connect(Service.java:295) ~[mail-1.4.7.jar!/:1.4.7]
#	at javax.mail.Service.connect(Service.java:176) ~[mail-1.4.7.jar!/:1.4.7]
#	at javax.mail.Service$connect.call(Unknown Source) ~[na:na]
#	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47) [groovy-all-2.4.15.jar!/:2.4.15]
#	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116) [groovy-all-2.4.15.jar!/:2.4.15]
#	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:144) [groovy-all-2.4.15.jar!/:2.4.15]
#	at com.genre.base.email.impl.EmailManagerImpl.generateAndSendEmail(EmailManagerImpl.groovy:55) ~[classes!/:2.0.0.RELEASE]
#	at com.genre.base.email.EmailManager$generateAndSendEmail.call(Unknown Source) ~[na:na]
#	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCall(CallSiteArray.java:47) [groovy-all-2.4.15.jar!/:2.4.15]
#	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:116) [groovy-all-2.4.15.jar!/:2.4.15]
#	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.call(AbstractCallSite.java:136) [groovy-all-2.4.15.jar!/:2.4.15]
#	at com.genre.base.scraper.task.ScrapeMailerTask.sendGoalieDataToUsers(ScrapeMailerTask.groovy:125) [classes!/:2.0.0.RELEASE]
#	at com.genre.base.scraper.task.ScrapeMailerTask$sendGoalieDataToUsers$0.callCurrent(Unknown Source) [classes!/:2.0.0.RELEASE]
#	at org.codehaus.groovy.runtime.callsite.CallSiteArray.defaultCallCurrent(CallSiteArray.java:51) [groovy-all-2.4.15.jar!/:2.4.15]
#	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:157) [groovy-all-2.4.15.jar!/:2.4.15]
#	at org.codehaus.groovy.runtime.callsite.AbstractCallSite.callCurrent(AbstractCallSite.java:185) [groovy-all-2.4.15.jar!/:2.4.15]
#	at com.genre.base.scraper.task.ScrapeMailerTask.checkGoalieScrapeSendEmail(ScrapeMailerTask.groovy:104) [classes!/:2.0.0.RELEASE]
#	at com.genre.base.scraper.task.ScrapeMailerTask.run(ScrapeMailerTask.groovy:39) [classes!/:2.0.0.RELEASE]
#	at org.springframework.scheduling.support.DelegatingErrorHandlingRunnable.run(DelegatingErrorHandlingRunnable.java:54) [spring-context-5.0.4.RELEASE.jar!/:5.0.4.RELEASE]
#	at org.springframework.scheduling.concurrent.ReschedulingRunnable.run(ReschedulingRunnable.java:93) [spring-context-5.0.4.RELEASE.jar!/:5.0.4.RELEASE]
#	at java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:511) [na:1.8.0_201]
#	at java.util.concurrent.FutureTask.run(FutureTask.java:266) [na:1.8.0_201]
#	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.access$201(ScheduledThreadPoolExecutor.java:180) [na:1.8.0_201]
#	at java.util.concurrent.ScheduledThreadPoolExecutor$ScheduledFutureTask.run(ScheduledThreadPoolExecutor.java:293) [na:1.8.0_201]
#	at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149) [na:1.8.0_201]
#	at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624) [na:1.8.0_201]
#	at java.lang.Thread.run(Thread.java:748) [na:1.8.0_201]

