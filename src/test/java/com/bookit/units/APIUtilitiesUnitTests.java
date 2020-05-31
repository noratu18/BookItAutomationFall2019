package com.bookit.units;

import com.bookit.utilities.APIUtilities;
import com.bookit.utilities.Environment;
import org.codehaus.groovy.classgen.EnumVisitor;
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

    @Test
    public void testIfUserExists(){

        //negative test
        int actual = APIUtilities.getUserID("thereisnoemail@email.com", "88888");
        Assert.assertEquals(-1, actual);

        // positive test
        int actual2 = APIUtilities.getUserID(Environment.MEMBER_USERNAME, Environment.MEMBER_PASSWORD);
        Assert.assertTrue(actual2 > 0); // if ID is positive - user exists indeed, otherwise it return -1
    }



}
