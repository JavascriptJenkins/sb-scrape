package com.genre.base.springMVC.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
@RequestMapping("/SampleMVC")
class SampleMVCController {


    @RequestMapping("/serveView")
    String serveView() {
        return "SampleMVC/hello";
    }








}
