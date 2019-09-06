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
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Created by Braveen on 12/03/2018.
 */
@RunWith(AndroidJUnit4.class)
public class SetTargetPageTest {
    @Rule
    public ActivityTestRule<SetTargetPage> setTargetPageActivityTestRule = new ActivityTestRule<SetTargetPage>(SetTargetPage.class);
    public SetTargetPage setTargetPage = null;

    @Before
    public void setUp() throws Exception {
        setTargetPage = setTargetPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception {
        View view = setTargetPage.findViewById(R.id.SetTargetPageTitle);
        assertNotNull(view);
    }

    @Test
    public void testDefaultPeriodValue() throws Exception {
        onView(withId(R.id.GoalWeight)).perform(replaceText(""));
        String actualOutput = setTargetPage.expectedPeriodTxt.getText().toString().trim();
        String expectedOutput = "0";
        assertEquals(expectedOutput,actualOutput);
    }
    @After
    public void tearDown() throws Exception {
        setTargetPage = null;
    }
}