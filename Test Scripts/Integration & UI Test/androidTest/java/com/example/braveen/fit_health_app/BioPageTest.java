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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Braveen on 12/03/2018.
 */
@RunWith(AndroidJUnit4.class)
public class BioPageTest {
    @Rule
    public ActivityTestRule<BioPage> bioPageActivityTestRule = new ActivityTestRule<BioPage>(BioPage.class);
    public BioPage bioPage = null;

    Instrumentation.ActivityMonitor monitorGoTo = getInstrumentation().addMonitor(GoToPage.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {
        bioPage = bioPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception {
        View view = bioPage.findViewById(R.id.BioPageTitle);
        assertNotNull(view);
    }

    @Test
    public void testSaveBioCase1() throws Exception {
        onView(withId(R.id.BioPageFirstNameTxt)).perform(replaceText(""));
        onView(withId(R.id.BioPageLastNameTxt)).perform(replaceText("Sritharan"));
        onView(withId(R.id.BioPageAgeTxt)).perform(replaceText("22"));
        onView(withId(R.id.BioPageHeightTxt)).perform(replaceText("180"));
        onView(withId(R.id.BioPageWeightTxt)).perform(replaceText("70"));
        boolean actualOutput = bioPage.saved;
        boolean expectedOutput = false;
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void testSaveBioCase2() throws Exception {
        onView(withId(R.id.BioPageFirstNameTxt)).perform(replaceText("Braveen"));
        onView(withId(R.id.BioPageLastNameTxt)).perform(replaceText(""));
        onView(withId(R.id.BioPageAgeTxt)).perform(replaceText("22"));
        onView(withId(R.id.BioPageHeightTxt)).perform(replaceText("180"));
        onView(withId(R.id.BioPageWeightTxt)).perform(replaceText("70"));
        boolean actualOutput = bioPage.saved;
        boolean expectedOutput = false;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSaveBioCase3() throws Exception {
        onView(withId(R.id.BioPageFirstNameTxt)).perform(replaceText("Braveen"));
        onView(withId(R.id.BioPageLastNameTxt)).perform(replaceText("Sritharan"));
        onView(withId(R.id.BioPageAgeTxt)).perform(replaceText("22"));
        onView(withId(R.id.BioPageHeightTxt)).perform(replaceText("180.0.0"));
        onView(withId(R.id.BioPageWeightTxt)).perform(replaceText("70"));
        boolean actualOutput = bioPage.saved;
        boolean expectedOutput = false;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testSaveBioCase4() throws Exception {
        onView(withId(R.id.BioPageFirstNameTxt)).perform(replaceText("Braveen"));
        onView(withId(R.id.BioPageLastNameTxt)).perform(replaceText("Sritharan"));
        onView(withId(R.id.BioPageAgeTxt)).perform(replaceText("22"));
        onView(withId(R.id.BioPageHeightTxt)).perform(replaceText("180"));
        onView(withId(R.id.BioPageWeightTxt)).perform(replaceText("70.0.0"));
        boolean actualOutput = bioPage.saved;
        boolean expectedOutput = false;
        assertEquals(expectedOutput, actualOutput);
    }

    @Test
    public void testGoToPageVisibilityCase1() throws Exception {
        bioPage.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bioPage.saved = false;
            }
        });
        onView(withId(R.id.BioPageSaveBtn)).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNull(goToPage);
    }

    @Test
    public void testGoToPageVisibilityCase2() throws Exception {
        bioPage.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                bioPage.saved = true;
            }
        });
        onView(withId(R.id.BioPageSaveBtn)).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNotNull(goToPage);
        goToPage.finish();
    }

    @After
    public void tearDown() throws Exception {
        bioPage = null;
    }
}