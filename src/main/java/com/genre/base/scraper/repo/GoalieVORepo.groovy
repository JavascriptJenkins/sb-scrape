package com.genre.base.scraper.repo

import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component

@Component
interface GoalieVORepo extends CrudRepository<GoalieVO, Long>{

    // NOTE: GoalieVO in this query is the name of the object and not the table
    @Query("SELECT g FROM GoalieVO g WHERE g.dateTimeOfGame = :dateTimeOfGame and g.name = :name")
    GoalieVO findByDateTimeOfGameAndName(
            @Param("dateTimeOfGame") String dateTimeOfGame,
            @Param("name") String name)

}
