package com.genre.base.scraper.repo

import com.genre.base.scraper.repo.objects.nhl.SubscriptionVO
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component

@Component
interface SubscriptionVORepo extends CrudRepository<SubscriptionVO, Long>{

    // NOTE: GoalieVO in this query is the name of the object and not the table
//    @Query("SELECT g FROM GoalieVO g WHERE g.dateTimeOfGame = :dateTimeOfGame and g.name = :name")
//    SubscriptionVO findByDateTimeOfGameAndName(
//            @Param("dateTimeOfGame") String dateTimeOfGame,
//            @Param("name") String name)

    @Query("FROM SubscriptionVO s WHERE s.subscriptionTypeVO.subscription_type_id = :subTypeID and s.userVO.active = 1")
    Collection<SubscriptionVO> getSubscriptionsByIdOnlyWithActiveUser(@Param("subTypeID") long subTypeID)



}
