package com.example.braveen.fit_health_app;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.junit.Assert.*;

/**
 * Created by Braveen on 12/03/2018.
 */

@RunWith(AndroidJUnit4.class)
public class GoToPageTest {
    @Rule
    public ActivityTestRule<GoToPage> goToPageActivityTestRule = new ActivityTestRule<GoToPage>(GoToPage.class);
    public GoToPage goToPage = null;

    Instrumentation.ActivityMonitor monitorLeaderboard = getInstrumentation().addMonitor(LeaderboardPage.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorBio = getInstrumentation().addMonitor(BioPage.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorExit = getInstrumentation().addMonitor(SignInPage.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorWorkout = getInstrumentation().addMonitor(WorkoutPage.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorDailyReport = getInstrumentation().addMonitor(DailyReportPage.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorSetTarget = getInstrumentation().addMonitor(SetTargetPage.class.getName(),null,false);

    @Before
    public void setUp() throws Exception{
        goToPage = goToPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception{
        View view = goToPage.findViewById(R.id.WalkImg);
        assertNotNull(view);
    }

    @Test
    public void testBioPageVisibility() throws Exception{
        onView(withId(R.id.PersonImg)).perform(click());
        Activity bioPage = getInstrumentation().waitForMonitorWithTimeout(monitorBio,5000);
        assertNotNull(bioPage);
        bioPage.finish();
    }

    @Test
    public void testLeaderboardPageVisibility() throws Exception{
        onView(withId(R.id.GroupImg)).perform(click());
        Activity leaderboardPage = getInstrumentation().waitForMonitorWithTimeout(monitorLeaderboard,5000);
        assertNotNull(leaderboardPage);
        leaderboardPage.finish();
    }

    @Test
    public void testExitAlertDialogDisplayed() throws Exception{
        onView(withId(R.id.ExitImg)).perform(click());
        onView(withText("Sign Out")).check(matches(isDisplayed()));
    }

    @Test
    public void testClickYesButton() throws Exception{
        onView(withId(R.id.ExitImg)).perform(click());
        onView(withText("Yes")).perform(click());
        Activity signInPage = getInstrumentation().waitForMonitorWithTimeout(monitorExit,5000);
        assertNotNull(signInPage);
        signInPage.finish();
    }

    @Test
    public void testClickNoButton() throws Exception{
        onView(withId(R.id.ExitImg)).perform(click());
        onView(withText("No")).perform(click());
        Activity signInPage = getInstrumentation().waitForMonitorWithTimeout(monitorExit,5000);
        assertNull(signInPage);
    }

    @Test
    public void testDailyReportPageVisibility() throws Exception{
        onView(withId(R.id.ReportBtn)).perform(click());
        Activity dailyReportPage = getInstrumentation().waitForMonitorWithTimeout(monitorDailyReport,5000);
        assertNotNull(dailyReportPage);
        dailyReportPage.finish();
    }

    @Test
    public void testSetTargetPageVisibility() throws Exception{
        onView(withId(R.id.TargetBtn)).perform(click());
        Activity setTargetPage = getInstrumentation().waitForMonitorWithTimeout(monitorSetTarget,5000);
        assertNotNull(setTargetPage);
        setTargetPage.finish();
    }

    @Test
    public void testWorkoutPageVisibility() throws Exception{
        onView(withId(R.id.WorkoutBtn)).perform(click());
        Activity workoutPage = getInstrumentation().waitForMonitorWithTimeout(monitorWorkout,5000);
        assertNotNull(workoutPage);
        workoutPage.finish();
    }

    @After
    public void tearDown() throws Exception{
        goToPage = null;
    }
}
