package com.genre.base.email

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

@Configuration
class ThreadPoolConfig {

    @Bean
    ThreadPoolTaskScheduler threadPoolTaskScheduler(){
        ThreadPoolTaskScheduler threadPoolTaskScheduler= new ThreadPoolTaskScheduler()
        threadPoolTaskScheduler.setPoolSize(5)
        threadPoolTaskScheduler.setThreadNamePrefix(
                "ThreadPoolTaskScheduler")
        return threadPoolTaskScheduler
    }

}
