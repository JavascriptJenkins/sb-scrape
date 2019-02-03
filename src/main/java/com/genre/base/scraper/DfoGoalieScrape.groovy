package com.genre.base.scraper

import org.springframework.stereotype.Component

@Component
interface DfoGoalieScrape {


    // 1) Check starting goalies and put them in a database. (goalie name, teamName, isConfirmed, dateTimeOfGame)
    //      a) Is the goalie already in database for that teamName and dateTimeOfGame?
    //         If yes, then check for isConfirmed.  If isConfirmed == true, then send alert.  If false, do nothing.

    // This method will return false if there is technical difficulty scraping.
    void checkStartingGoalies()

}
