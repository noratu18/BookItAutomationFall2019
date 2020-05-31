package com.bookit.pages;

import com.bookit.utilities.BrowserUtilities;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage{

    @FindBy(name = "email")
    private WebElement email;

    @FindBy(name = "password")
    private WebElement password;

    public void login(String emailText, String passwordText){
        BrowserUtilities.waitForPageToLoad(10);
        email.sendKeys(emailText);
        password.sendKeys(passwordText, Keys.ENTER);

    }

}
