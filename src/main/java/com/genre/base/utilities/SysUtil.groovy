package com.genre.base.utilities

import org.springframework.stereotype.Component

@Component
interface SysUtil {

    String getCurrentTimeString()

    int getRandomNumber()

    boolean isAlpha(String name)

    String removeWhiteSpace(String inputString)

    long get1971epochMs()
    long get2030epochMs()

}
