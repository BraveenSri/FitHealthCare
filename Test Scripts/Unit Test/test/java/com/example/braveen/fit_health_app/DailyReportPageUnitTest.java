package com.example.braveen.fit_health_app;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Braveen on 12/05/2018.
 */
public class DailyReportPageUnitTest {
    @Test
    public void testRoundValue() throws Exception {
        DailyReportPage dailyReportPage = new DailyReportPage();
        double value = 2.95684;
        int places = 2;
        double actualOutput = dailyReportPage.round(value,places);
        double expectedOutput = 2.96;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testWalkingCalorieValue() throws Exception {
        DailyReportPage dailyReportPage = new DailyReportPage();
        int stepCount = 100;
        double BMR = 1800;
        double actualOutput = dailyReportPage.walkingCalorie(stepCount,BMR);
        double expectedOutput = 4.06;
        double delta = 0.01;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testRunningCalorieValue() throws Exception {
        DailyReportPage dailyReportPage = new DailyReportPage();
        int runningDuration = 60;
        double BMR = 1800;
        double actualOutput = dailyReportPage.runningCalorie(runningDuration,BMR);
        double expectedOutput = 825.00;
        double delta = 0.01;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testCyclingCalorieValue() throws Exception {
        DailyReportPage dailyReportPage = new DailyReportPage();
        int cyclingDuration = 60;
        double BMR = 1800;
        double actualOutput = dailyReportPage.cyclingCalorie(cyclingDuration,BMR);
        double expectedOutput = 510.00;
        double delta = 0.01;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testSwimmingCalorieValue() throws Exception {
        DailyReportPage dailyReportPage = new DailyReportPage();
        int swimmingDuration = 60;
        double BMR = 1800;
        double actualOutput = dailyReportPage.swimmingCalorie(swimmingDuration,BMR);
        double expectedOutput = 712.50;
        double delta = 0.01;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testOtherCalorieValue() throws Exception {
        DailyReportPage dailyReportPage = new DailyReportPage();
        int otherDuration = 60;
        double BMR = 1800;
        double actualOutput = dailyReportPage.otherCalorie(otherDuration,BMR);
        double expectedOutput = 375.00;
        double delta = 0.01;
        assertEquals(expectedOutput,actualOutput,delta);
    }

    @Test
    public void testTotalCalorieValue() throws Exception {
        DailyReportPage dailyReportPage = new DailyReportPage();
        double walkingCal = 25;
        double runningCal = 100;
        double cyclingCal = 50;
        double swimmingCal = 75;
        double otherCal = 50;
        double actualOutput = dailyReportPage.totalCalorie(walkingCal,runningCal,cyclingCal,swimmingCal,otherCal);
        double expectedOutput = 300.00;
        double delta = 0;
        assertEquals(expectedOutput,actualOutput,delta);
    }
}