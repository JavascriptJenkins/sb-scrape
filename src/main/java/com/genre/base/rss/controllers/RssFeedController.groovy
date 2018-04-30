package com.genre.base.rss.controllers

import com.genre.base.rss.RssFeedManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@RequestMapping("/RssFeed/")
@RestController
class RssFeedController {

    @Autowired
    RssFeedManager rssFeedManager

    @RequestMapping(value = "/MLB/activate", method = RequestMethod.GET)
    @ResponseStatus(value=HttpStatus.OK)
    void activateMLBScraper() {

        rssFeedManager.readMlbRssFeed()

    }


}
