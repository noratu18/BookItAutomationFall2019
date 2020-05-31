package com.bookit.utilities;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

public class APIUtilities {

    //static block will execute before everything in the class only once
    // baseURI --> is coming from RestAssured
    // static block used to initialize static variables
    // 1. static block
    // 2. constructor
    // 3. method
    static {
        baseURI = Environment.BASE_URI;
    }

    /**
     * This method is used to retrieve token from the server
     * Token must be attached to the header of every API call
     * since the tokens expires that's why we need this method
     * @return
     */
    public static String getToken() {

        Response response = given()
                .queryParam("email", Environment.LEADER_USERNAME)
                .queryParam("password", Environment.LEADER_PASSWORD)
                .when()
                .get("/sign"); //endpoint for authentication

        response.then().log().ifError(); // if request failed, print response information
        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token :: " + token);
        return token;



    }

    /**
     * This method is used to retrieve authorization token from the server for specific role
     * Token must be attached to the header of every API call
     * since the tokens expires that's why we need this method
     * @return
     */
    public static String getToken(String role) {

        String email = null;
        String password = null;
        if(role.toLowerCase().contains("teacher")){
            email = Environment.TEACHER_USERNAME;
            password = Environment.TEACHER_PASSWORD;
        }else if(role.toLowerCase().contains("lead")){
            email = Environment.LEADER_USERNAME;
            password = Environment.LEADER_PASSWORD;
        }else{
            email = Environment.MEMBER_USERNAME;
            password = Environment.MEMBER_PASSWORD;
        }

        Response response = given()
                .queryParam("email",email )
                .queryParam("password", password)
                .when()
                .get("/sign");

        response.then().log().ifError(); // if request failed, print response information
        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token = " + token);
        return token;
    }

       /**
     *      * This method is used to retrieve token from the server
     *      * Token must be attached to the header of every API call
     *      * since the tokens expires that's why we need this method
     * @param email
     * @param password
     * @return
     */
    public static String getToken(String email, String password) {

        Response response = given()
                .queryParam("email", email)
                .queryParam("password", password)
                .when()
                .get("/sign"); //endpoint for authentication

        response.then().log().ifError(); // if request failed, print response information
        String token = response.jsonPath().getString("accessToken");
        System.out.println("Token :: " + token);
        return token;

    }

    /**
     * This method returns id of logged in user
     * {
     *         "id": integer,
     *         "firstName": "string",
     *         "lastName": "string",
     *         "role": "string"
     *     }
     *     User credentaials
     *
     * @param email
     * @param password
     * @return user id, return -1 if user doesn't exist
     */

    public static int getUserID(String email, String password){

        try {
            String token = getToken(email, password);

            Response response = given().auth().oauth2(token)
                    .when().get(EndPoints.GET_ME);
            response.then().log().ifError();//print response in case of error
            response.then().statusCode(200);//ensure that it returns 200 status code
            return response.jsonPath().getInt("id");
        }catch (Exception e ){
            System.out.println("User doesn't exist!");
            System.out.println(e.getMessage());;
        }
        return -1; // if user doesn't exist
    }

    /**
     * This method deletes user based on id
     * @param id of the user to delete
     * @return response object
     */
    public static Response deleteUserBy(int id){
        String token = getToken("teacher");
        Response response = given().auth().oauth2(token)
                .when().delete(EndPoints.DELETE_STUDENT, id);
        response.then().log().ifError();
        System.out.printf("User with id %s was deleted", id);
        return response;

    }


    /**
     * use this method to add new batch to the system
     * @param batchNumber to add
     * @return response object
     */
    public static Response addBatch(int batchNumber){

        String token = getToken("techer");

        Response response = given()
                .auth().oauth2(token)
                .queryParam("batch-number", batchNumber)
                .post(EndPoints.ADD_BATCH);
        response.then().log().ifError();
        return response;

    }


    /**
     * Use this method to add new team Name
     * @param teamName must be unique within specific batch number
     * @param location  VA ot IL
     * @param batchNumber any number that already exist
     * @return response object
     */
    public static Response addTeam( String teamName, String location, int batchNumber){
        String token = getToken("teacher");
        Response response = given()
                .auth().oauth2(token)
                .queryParam("team-name", teamName)
                .queryParam("campus-location", location)
                .queryParam("batch-number", batchNumber)
                .when()
                .post(EndPoints.ADD_TEAM);
        response.then().log().ifError();
        return response;

    }

    /**
     * Use this method before creating new user.
     * If user exists --> this method will that user
     * @param email
     * @param password
     */
    public static void ensureUserDoesntExist(String email, String password){
        int userID = getUserID(email, password);
        //condition is true if userID is a positive value
        // there is no users with 0 or negative id
        if(userID > 0){
            deleteUserBy(userID);
        }

    }

}
