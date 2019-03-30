package com.genre.base.scraper.repo

import com.genre.base.scraper.repo.objects.nhl.NhlGameVO
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param

interface NhlGameVORepo extends CrudRepository<NhlGameVO, Long> {

    // NOTE: UserVO in this query is the name of the object and not the table
    @Query("FROM NhlGameVO nhlvo WHERE nhlvo.dateTimeOfGame = :dateTimeOfGame")
    NhlGameVO findByDateTimeOfGame(@Param("dateTimeOfGame") String dateTimeOfGame)

}
