package com.example.braveen.fit_health_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Braveen on 12/05/2018.
 */

public class GoToPageUnitTest {
    @Test
    public void testBMIValue() throws Exception{
        GoToPage goToPage = new GoToPage();
        double weight = 70;
        double height = 180;
        double actualOutput = goToPage.measureBMI(weight,height);
        double expectedOutput = 21.60;
        double delta = 0.01;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testBMRValue() throws Exception{
        GoToPage goToPage = new GoToPage();
        double weight = 70;
        double height = 180;
        String gender = "Male";
        int age = 22;
        double actualOutput = goToPage.measureBMR(weight,height,gender,age);
        double expectedOutput = 1720.00;
        double delta = 0.01;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testRoundValue() throws Exception{
        GoToPage goToPage = new GoToPage();
        double value = 2.95684;
        int places = 2;
        double actualOutput = goToPage.round(value,places);
        double expectedOutput = 2.96;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }
}
