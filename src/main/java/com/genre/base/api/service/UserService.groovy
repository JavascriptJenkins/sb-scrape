package com.genre.base.api.service

import com.genre.base.scraper.repo.UserVORepo
import com.genre.base.scraper.repo.objects.nhl.UserVO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

// This service layer is used by the api controllers to communicate as a passthru
// layer between the api controller and the UserRepositories.
// This seperation will make sure the api controllers don't get crowded with business logic
// code.
@Component
class UserService {

    @Autowired
    UserVORepo userVORepo

    private final Logger logger = LoggerFactory.getLogger(this.getClass())


    // returning an optional here because the user creation could fail.
    Optional<UserVO> createUser(UserVO userVO){

        try {
            UserVO returnObj = userVORepo.save(userVO)
            return  Optional.of(returnObj)
        } catch (Exception ex){
            logger.error('Caught exception creating user: ',ex)
            return  Optional.of(null) // return an empty optional
        }

    }



}
