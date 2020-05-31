package com.bookit.step_definitions;

import com.bookit.pojos.Room;
import com.bookit.utilities.APIUtilities;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
import static org.hamcrest.Matchers.*;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class APIStepDefinitions {

    private RequestSpecification requestSpecification; // this is what we put in given
    private Response response; // this is were we store response data
    private String token;// this is what we use for authentication
    private JsonPath jsonPath; // this is were we store JSON body
    private ContentType contentType; // this is what we use to setup content type


    //Given authorization token is provided for "teacher"
    @Given("authorization token is provided for {string}")
    public void authorization_token_is_provided_for(String role) {
        token = APIUtilities.getToken(role);

    }

    @Given("user accepts content type as {string}")
    public void user_accepts_content_type_as(String acceptType) {
        if(acceptType.toLowerCase().contains("json")){
            contentType = ContentType.JSON;
        }else if(acceptType.toLowerCase().contains("xml")){
            contentType = ContentType.XML;
        }else if(acceptType.toLowerCase().contains("html")){
            contentType = ContentType.HTML;
        }

    }

    @When("user sends GET request to {string}")
    public void user_sends_GET_request_to(String path) {
         response = given().accept(contentType).auth()
                 .oauth2(token)
                 .when()
                 .get(path).prettyPeek();

    }

    //Then user should be able to see 18 rooms
    @Then("user should be able to see {int} rooms")
    public void user_should_be_able_to_see_rooms(int expectedNumberOfRooms) {
      //  List<?> rooms = response.as(List.class); // will work too
        List<Object> rooms = response.jsonPath().get();
        Assert.assertEquals(expectedNumberOfRooms, rooms.size());
     //   response.then().body("", hasSize(expectedNumberOfRooms));// we can use hamcrest too

    }

    @When("user verifies that response status code is {int}")
    public void user_verifies_that_response_status_code_is(int expectedStatusCode) {
        Assert.assertEquals(expectedStatusCode, response.getStatusCode());

    }


    @Then("user should be able to see all room names")
    public void user_should_be_able_to_see_all_room_names() {
        //how we can represent a json --> as a map
        // but here we use our own custom pojo class Room
        List<Room> rooms = response.jsonPath().getList("", Room.class);
        // path "" --> is empty because there  is no name for one payload as a one map
        // with lambda expressions
     //   rooms.forEach(room -> System.out.println(room.getName()));
        for( Room each : rooms){
            System.out.println(each.getName());
        }

    }




    @Then("user payload contains following room names:")
    public void user_payload_contains_following_room_names(List<String> dataTable) {
        // we are collecting here only the name of rooms
        // we can use only List because we have only on table
        List<String> roomNames = response.jsonPath().getList("name");
        Assert.assertTrue(roomNames.containsAll(dataTable));//regular assertions
        MatcherAssert.assertThat(roomNames, hasItem(in(dataTable))); //coming from hamcrest matchers

    }

    //When user sends POST request to "/api/students/student" with following information:
    @When("user sends POST request to {string} with following information:")
    public void user_sends_POST_request_to_with_following_information(String path, List<Map<String, String>> dataTable) {
        // we need to use Lis<Map<...,....>> if we have multiple tables
        for(Map<String, String> user : dataTable) {
            System.out.println("User to add :: " + user);
            APIUtilities.ensureUserDoesntExist(user.get("email"), user.get("password"));
            response = given()
                    .queryParams(user)
                    .contentType(contentType).auth().oauth2(token).when().post(path).prettyPeek();
        }

    }

    /**
     * Then user deletes previously added students
     *       | first-name | last-name | email               | password | role                | campus-location | batch-number | team-name      |
     *       | Lesly      | SDET      | lesly2020@email.com | 1111     | student-team-member | VA              | 15           | Online_Hackers |
     * @param dataTable
     */
    @Then("user deletes previously added students")
    public void user_deletes_previously_added_students(List<Map<String, String>> dataTable) {
        for(Map<String, String> row : dataTable){
            int userID = APIUtilities.getUserID(row.get("email"), row.get("password"));
            response = APIUtilities.deleteUserBy(userID);
            response.then().statusCode(204);
        }

    }

}
