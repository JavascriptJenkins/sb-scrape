package com.genre.base.scraper.restapi.controllers

import com.genre.base.scraper.ScrapeManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus

@RequestMapping("/Scraper/")
@Controller
class ScraperController {

    @Autowired
    ScrapeManager scrapeManager

    /* Sample requests on the 2 lines below baby */
    //  http://localhost:8080/CraigslistScraper/activate/?json={"activate":"yes"}
    //  http://localhost:8080/CraigslistScraper/activate/?json={"activate":"no"}
    // curl --data "json={"activate":"yes"}" http://localhost:8080/CraigslistScraper/activate
    @RequestMapping(value = "/CraigslistScraper/activate", method = RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.OK)
    void activateCraigslistScraper(@RequestParam("json") String json) {

        if(json.contains("yes")){
            scrapeManager.toggleCraigslistScraperProcess(true)
        }

        if(json.contains("no")){
            scrapeManager.toggleCraigslistScraperProcess(false)
        }
    }


    @RequestMapping(value = "/MLB/activate", method = RequestMethod.POST)
    @ResponseStatus(value=HttpStatus.OK)
    void activateMLBScraper(@RequestParam("json") String json) {

        if(json.contains("yes")){
            scrapeManager.toggleMLBScrape(true)
        }

        if(json.contains("no")){
            scrapeManager.toggleMLBScrape(false)
        }
    }


}
