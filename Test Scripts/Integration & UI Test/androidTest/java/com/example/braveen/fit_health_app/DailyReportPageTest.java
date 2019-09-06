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
public class DailyReportPageTest {
    @Rule
    public ActivityTestRule<DailyReportPage> dailyReportPageActivityTestRule = new ActivityTestRule<DailyReportPage>(DailyReportPage.class);
    public DailyReportPage dailyReportPage = null;

    Instrumentation.ActivityMonitor monitorGoTo = getInstrumentation().addMonitor(GoToPage.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        dailyReportPage = dailyReportPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception {
        View view = dailyReportPage.findViewById(R.id.ReportTitle);
        assertNotNull(view);
    }

    @Test
    public void testResetAlertDialogDisplayed() throws Exception{
        onView(withId(R.id.ResetBtn)).perform(click());
        onView(withText("Data reset")).check(matches(isDisplayed()));
    }


    @Test
    public void testClickYesButton() throws Exception{
        onView(withId(R.id.ResetBtn)).perform(click());
        onView(withText("Yes")).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNotNull(goToPage);
        goToPage.finish();
    }

    @Test
    public void testClickNoButton() throws Exception{
        onView(withId(R.id.ResetBtn)).perform(click());
        onView(withText("No")).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNull(goToPage);
    }

    @After
    public void tearDown() throws Exception {
        dailyReportPage = null;
    }
}