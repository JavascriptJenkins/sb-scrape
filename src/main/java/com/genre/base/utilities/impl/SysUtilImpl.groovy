package com.genre.base.utilities.impl

import com.genre.base.utilities.SysUtil
import org.springframework.stereotype.Component

@Component
class SysUtilImpl implements SysUtil {

    int low = 5000
    int high = 15000


    String getCurrentTimeString(){

        java.util.Date dt = new java.util.Date();

        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String currentTime = sdf.format(dt);
        return currentTime
    }

    int getRandomNumber(){
        Random r = new Random()
        int result = r.nextInt(high-low) + low
        return result
    }

    boolean isAlpha(String name) {
        name = name.replaceAll("\\s+","")
        char[] chars = name.trim().toCharArray();

        for (char c : chars) {
            if(!Character.isLetter(c)) {
                return false;
            }
        }

        return true;
    }

    String removeWhiteSpace(String inputString){
        return inputString.replaceAll("\\s+","")
    }

    long get1971epochMs(){
        return 31557600000
    }

    long get2030epochMs(){
        return 1893477600000
    }


}
