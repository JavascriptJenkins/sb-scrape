package com.genre.base.scraper.objects.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.support.PageFactory

class PageObject {
    protected WebDriver driver

    PageObject(WebDriver driver){
        this.driver = driver
        PageFactory.initElements(driver, this)
    }
}
