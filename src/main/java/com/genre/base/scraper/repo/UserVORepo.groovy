package com.genre.base.scraper.repo

import com.genre.base.scraper.repo.objects.nhl.GoalieVO
import com.genre.base.scraper.repo.objects.nhl.UserVO
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Component

@Component
interface UserVORepo extends CrudRepository<UserVO, Long>{

    // NOTE: UserVO in this query is the name of the object and not the table
//    @Query("FROM UserVO u join UserSubscriptionVO us on u.user_id = us.user_id WHERE us.subscription_type_id = :subTypeID and u.active = 1")
//    Collection<UserVO> getAllActiveUsersBySubscriptionTypeID(@Param("subTypeID") int subTypeID)

//    @Query("FROM UserVO u join SubscriptionVO s on u.user_id = s.user_id WHERE s.subscription_type_id = :subTypeID and u.active = 1")
//    Collection<UserVO> getAllActiveUsersBySubscriptionTypeID(@Param("subTypeID") int subTypeID)

}
