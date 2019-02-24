package com.genre.base.api.rest

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import com.genre.base.api.service.UserService
import com.genre.base.scraper.repo.objects.nhl.UserVO
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.context.request.async.DeferredResult

import java.util.concurrent.ForkJoinPool

@RequestMapping('/UserApi/')
class UserApiController {

    @Autowired
    UserService userService

    private final Logger logger = LoggerFactory.getLogger(this.getClass())

    ObjectMapper mapper = new ObjectMapper()

    @CrossOrigin(origins = [])
    @RequestMapping(value='/createUser',method=RequestMethod.POST)
    DeferredResult<ResponseEntity<String>> createUser(@RequestBody String json){

        DeferredResult<ResponseEntity<String>> output = new DeferredResult<>()

        ForkJoinPool.commonPool().submit({ ->
            logger.debug("Processing in seperate thread")
            try{

                JsonNode root = mapper.readTree(json)
                UserVO userVO = mapper.treeToValue(root, UserVO.class)

                String payload = executeCreateUser(userVO)
                output.setResult(ResponseEntity.ok(payload))

            } catch (InterruptedException e){
                logger.error("Caught interruptedException: "+e)
            }
        })

        logger.debug("servlet thread freed")
        return output
    }

    String executeCreateUser(UserVO userVO){

        Optional<UserVO> optional = userService.createUser(userVO)

        if(optional.isPresent()){
            return mapper.writeValueAsString(optional.get()) // convert return obj to json string
        } else {
            return "Could not create user! "
        }

    }


    // way not using deferred method
//    @CrossOrigin(origins = [])
//    @RequestMapping(value='/createUser',method=RequestMethod.POST)
//    String createUser(@RequestBody String json){
//
//
//
//    }


}
