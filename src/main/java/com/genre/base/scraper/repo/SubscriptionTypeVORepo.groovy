package com.genre.base.scraper.repo

import com.genre.base.scraper.repo.objects.nhl.SubscriptionTypeVO
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Component

@Component
interface SubscriptionTypeVORepo extends CrudRepository<SubscriptionTypeVO, Long>{

    // NOTE: GoalieVO in this query is the name of the object and not the table
//    @Query("SELECT g FROM GoalieVO g WHERE g.dateTimeOfGame = :dateTimeOfGame and g.name = :name")
//    SubscriptionVO findByDateTimeOfGameAndName(
//            @Param("dateTimeOfGame") String dateTimeOfGame,
//            @Param("name") String name)

}
