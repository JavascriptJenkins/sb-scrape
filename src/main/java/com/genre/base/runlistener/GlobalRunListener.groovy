package com.genre.base.runlistener

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Component

import javax.annotation.PostConstruct


@Component
class GlobalRunListener implements ApplicationListener<ApplicationReadyEvent>{
    @Override
    void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        println("THE APP STARTED OMG")
    }


    // this is a hack to make spring inject beans
    @PostConstruct
    void loadBeans(){
        println("LOADING BEANS BABY")
    }
}
