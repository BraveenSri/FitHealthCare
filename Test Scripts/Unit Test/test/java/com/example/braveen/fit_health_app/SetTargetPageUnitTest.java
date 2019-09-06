package com.example.braveen.fit_health_app;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Braveen on 12/05/2018.
 */

public class SetTargetPageUnitTest {
    @Test
    public void testPeriodForGreatDiet() throws Exception {
        SetTargetPage setTargetPage = new SetTargetPage();
        String diet = "Great";
        double BMR = 1800;
        double calories = 1500;
        double currentWeight = 70;
        double goalWeight = 60;
        int actualOutput = setTargetPage.calPeriod(diet,BMR,calories,currentWeight,goalWeight);
        int expectedOutput = 42;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testPeriodForGoodDiet() throws Exception {
        SetTargetPage setTargetPage = new SetTargetPage();
        String diet = "Good";
        double BMR = 1800;
        double calories = 1500;
        double currentWeight = 70;
        double goalWeight = 60;
        int actualOutput = setTargetPage.calPeriod(diet,BMR,calories,currentWeight,goalWeight);
        int expectedOutput = 53;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testPeriodForOkDiet() throws Exception {
        SetTargetPage setTargetPage = new SetTargetPage();
        String diet = "Ok";
        double BMR = 1800;
        double calories = 1500;
        double currentWeight = 70;
        double goalWeight = 60;
        int actualOutput = setTargetPage.calPeriod(diet,BMR,calories,currentWeight,goalWeight);
        int expectedOutput = 73;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testPeriodForBadDiet() throws Exception {
        SetTargetPage setTargetPage = new SetTargetPage();
        String diet = "Bad";
        double BMR = 1800;
        double calories = 1500;
        double currentWeight = 70;
        double goalWeight = 60;
        int actualOutput = setTargetPage.calPeriod(diet,BMR,calories,currentWeight,goalWeight);
        int expectedOutput = 114;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testPeriodForAwfulDiet() throws Exception {
        SetTargetPage setTargetPage = new SetTargetPage();
        String diet = "Awful";
        double BMR = 1800;
        double calories = 1500;
        double currentWeight = 70;
        double goalWeight = 60;
        int actualOutput = setTargetPage.calPeriod(diet,BMR,calories,currentWeight,goalWeight);
        int expectedOutput = 263;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testRoundValue() throws Exception {
        SetTargetPage setTargetPage = new SetTargetPage();
        double value = 2.95684;
        int places = 2;
        double actualOutput = setTargetPage.round(value,places);
        double expectedOutput = 2.96;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }
}
