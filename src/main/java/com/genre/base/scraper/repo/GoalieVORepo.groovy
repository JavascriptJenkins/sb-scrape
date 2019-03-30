package com.genre.base.scraper.repo

import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.scraper.repo.objects.nhl.UserVO
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Component
interface GoalieVORepo extends CrudRepository<GoalieVO, Long>{


//    // NOTE: UserVO in this query is the name of the object and not the table
//    @Query("FROM GoalieVO g join NhlGameVO nhlvo on nhlvo.nhl_game_id = g.nhl_game_id WHERE g.name = :name and nhlvo.dateTimeOfGame = :dateTimeOfGame")
//    GoalieVO findByDateTimeOfGameAndName(@Param("dateTimeOfGame") String dateTimeOfGame,
//                                         @Param("name") String name)


//    // NOTE: GoalieVO in this query is the name of the object and not the table
//    @Query("SELECT g FROM GoalieVO g WHERE g.dateTimeOfGame = :dateTimeOfGame and g.name = :name")
//    GoalieVO findByDateTimeOfGameAndName(
//            @Param("dateTimeOfGame") String dateTimeOfGame,
//            @Param("name") String name)


    // pass in a 1 or 0 and get a list of sent or non sent goalies
    @Query("FROM GoalieVO g WHERE g.wasSentToAllEmails = :wasSentToAllEmails")
    Collection <GoalieVO> getAllGoaliesByWasSentToAll(
            @Param("wasSentToAllEmails") int wasSentToAllEmails
    )

    @Transactional
            (
                    propagation = Propagation.REQUIRED,
                    readOnly = false,
                    rollbackFor = Throwable.class
            )
    @Modifying
    @Query("update GoalieVO g set g.wasSentToAllEmails =:wasSentToAllEmails where g.id =:id")
    void updateGoalieVOToggle(@Param("wasSentToAllEmails") int wasSentToAllEmails, @Param("id") int id)

}
