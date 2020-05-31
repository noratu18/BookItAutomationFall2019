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
        System.out.println("token = " + token);
        return token;



    }


}
