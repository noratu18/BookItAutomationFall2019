package com.bookit.step_definitions;

import com.bookit.pages.LoginPage;
import com.bookit.utilities.Driver;
import com.bookit.utilities.Environment;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginStepDefinitions {
    LoginPage loginPage = new LoginPage();


    @Then("user is on the landing page")
    public void user_is_on_the_landing_page() {
        Driver.getDriver().get(Environment.URL);

    }

    @When("user logs in with {string} email and {string} password")
    public void user_logs_in_with_email_and_password(String email, String password) {
      loginPage.login(email, password);
    }

    @When("user navigates to personal page")
    public void user_navigates_to_personal_page() {
        loginPage.goToSelfPage();

    }



}
