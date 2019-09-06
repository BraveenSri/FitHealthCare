package com.example.braveen.fit_health_app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Braveen on 12/03/2018.
 */
@RunWith(AndroidJUnit4.class)
public class WorkoutPageTest {
    @Rule
    public ActivityTestRule<WorkoutPage> workoutPageActivityTestRule = new ActivityTestRule<WorkoutPage>(WorkoutPage.class);
    public WorkoutPage workoutPage = null;

    @Before
    public void setUp() throws Exception {
        workoutPage = workoutPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception {
        View view = workoutPage.findViewById(R.id.WorkoutPageTitle);
        assertNotNull(view);
    }

    @Test
    public void testSaveWorkoutDurationCase1() throws Exception {
        onView(withId(R.id.SaveDuration)).perform(click());
        String actualOutput = workoutPage.runningTxt.getText().toString().trim();
        String expectedOutput = "0";
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void testSaveWorkoutDurationCase2() throws Exception {
        onView(withId(R.id.SaveDuration)).perform(click());
        String actualOutput = workoutPage.cyclingTxt.getText().toString().trim();
        String expectedOutput = "0";
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void testSaveWorkoutDurationCase3() throws Exception {
        onView(withId(R.id.SaveDuration)).perform(click());
        String actualOutput = workoutPage.swimmingTxt.getText().toString().trim();
        String expectedOutput = "0";
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void testSaveWorkoutDurationCase4() throws Exception {
        onView(withId(R.id.SaveDuration)).perform(click());
        String actualOutput = workoutPage.otherTxt.getText().toString().trim();
        String expectedOutput = "0";
        assertEquals(expectedOutput,actualOutput);
    }

    @After
    public void tearDown() throws Exception {
        workoutPage = null;
    }
}