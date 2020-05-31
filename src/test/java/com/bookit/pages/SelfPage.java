package com.bookit.pages;

import com.bookit.utilities.BrowserUtilities;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SelfPage extends BasePage{


    /**
     *
     * @param value can be: role, name, team, batch, campus
     * @return it's value
     */
    public String getUserInfo(String value){
        BrowserUtilities.waitForPageToLoad(10);
        String xpath = "//p[text()='" + value + "']/preceding-sibling::p";
        WebElement valueElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpath)));

        return valueElement.getText().trim();
    }
}
