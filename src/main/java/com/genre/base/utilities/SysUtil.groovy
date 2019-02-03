package com.genre.base.utilities

import org.springframework.stereotype.Component

@Component
interface SysUtil {

    String getCurrentTimeString()

    int getRandomNumber()

}
