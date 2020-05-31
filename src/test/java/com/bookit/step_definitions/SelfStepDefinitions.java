package com.bookit.step_definitions;

import com.bookit.database.UserDB;
import com.bookit.pages.SelfPage;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

public class SelfStepDefinitions {

    SelfPage selfPage = new SelfPage();
    UserDB userDB = new UserDB();

    /*
     Then user verifies that information displays correctly:
      | first-name | last-name | email                    | password | role                | campus-location | batch-number | team-name      |
 Row 0| Nora      | SDET      | nora@email.com | 1111     | student-team-member | VA              | 15           | Online_Hackers |
     */

    @Then("user verifies that information displays correctly:")
    public void user_verifies_that_information_displays_correctly(List<Map<String, String>> dataTable) {

        System.out.println("==========the below code is for UI validation Only+==============");

        // get(0) --> means fet the first row
        String fullName = dataTable.get(0).get("first-name")+" "+dataTable.get(0).get("last-name");
        Assert.assertEquals(fullName, selfPage.getUserInfo("name"));
        Assert.assertEquals(dataTable.get(0).get("role"), selfPage.getUserInfo("role"));
        Assert.assertEquals(dataTable.get(0).get("campus-location"), selfPage.getUserInfo("campus"));
        Assert.assertEquals(dataTable.get(0).get("batch-number"), selfPage.getUserInfo("batch").replace("#",""));
        Assert.assertEquals(dataTable.get(0).get("team-name"), selfPage.getUserInfo("team"));;


        System.out.println("===========The below code is for database validations=============");

        Assert.assertTrue(userDB.checkIfUserExistInDB(dataTable.get(0).get("email")));

    }


}
