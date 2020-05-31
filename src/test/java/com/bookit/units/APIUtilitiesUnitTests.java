package com.bookit.units;

import com.bookit.utilities.APIUtilities;
import org.junit.Assert;
import org.junit.Test;

/**
 * Class with unit tests for APUtilities class
 * Here we ensure that utilities work fine before usin them in action
 */

public class APIUtilitiesUnitTests {

    /**
     * unit test
     */
    @Test
    public void getTokenTest(){

        String token = APIUtilities.getToken();
        String tokenForStudent = APIUtilities.getToken("student");
        String tokenForTeacher = APIUtilities.getToken("teacher");

        Assert.assertNotNull(token);
        Assert.assertNotNull(tokenForStudent);
        Assert.assertNotNull(tokenForTeacher);

    }
}
