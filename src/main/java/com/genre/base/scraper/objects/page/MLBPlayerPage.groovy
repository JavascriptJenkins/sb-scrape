package com.genre.base.scraper.objects.page

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.FindBy

class MLBPlayerPage extends PageObject {

    @FindBy(className = "responsive-datatable__pinned")
    public List<WebElement> responsiveDataTableList


    // driver.findElements(By.tagName("tr")).text
    @FindBy(tagName = "tr")
    public List<WebElement> allTrElements

    MLBPlayerPage(WebDriver driver) {
            super(driver)
        }


    // driver.findElements(By.className("responsive-datatable__pinned")).getAt(0).findElements(By.xpath(".//*")).each({it -> println it.text})
// driver.findElements(By.className("responsive-datatable__pinned")).getAt(0).findElements(By.xpath(".//*")).each({it -> if("Last 7 Games".equals(it.text)) println it.text})


}
