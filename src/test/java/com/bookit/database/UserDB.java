package com.bookit.database;

import com.bookit.utilities.DBUtility;

public class UserDB {


    /**
     * this method connects to SQL developer and gets the data from there based to the Query
     * @param email of email to look up
     * @return  true, if user exist
     */
    public boolean checkIfUserExistInDB(String email){

        String query = "SELECT COUNT(*) AS FROM users WHERE email = '"+email+"'";
        System.out.println("QUERY:: " +query);
        long countOfUsers = (Long) DBUtility.getCellValue(query);
        //getCellValue() --> coming from our DBUtility class
        return countOfUsers == 1;

    }

}
