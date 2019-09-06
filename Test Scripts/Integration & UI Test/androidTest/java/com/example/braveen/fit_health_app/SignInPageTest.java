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
public class SignInPageTest {
    @Rule
    public ActivityTestRule<SignInPage> signInPageActivityTestRule = new ActivityTestRule<SignInPage>(SignInPage.class);
    public SignInPage signInPage = null;

    Instrumentation.ActivityMonitor monitorSignUp = getInstrumentation().addMonitor(SignUpPage.class.getName(),null,false);
    Instrumentation.ActivityMonitor monitorGoTo = getInstrumentation().addMonitor(GoToPage.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        signInPage = signInPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception {
        View view = signInPage.findViewById(R.id.SignInLogoImg);
        assertNotNull(view);
    }

    @Test
    public void testSignUpPageVisibility() throws Exception {
        onView(withId(R.id.SigninPageSignupBtn)).perform(click());
        Activity signUpPage = getInstrumentation().waitForMonitorWithTimeout(monitorSignUp,5000);
        assertNotNull(signUpPage);
        signUpPage.finish();
    }

    @Test
    public void testSignInPasswordVisibility() throws Exception {
        onView(withId(R.id.SigninPagePasswordEye)).perform(click());
        int actualOutput = signInPage.setPassType;
        int expectedOutput = 1;
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void TestSignInUserCase1() throws Exception {
        onView(withId(R.id.SigninPageEmailTxt)).perform(replaceText("braveen79@gmail.com"));
        onView(withId(R.id.SigninPagePasswordTxt)).perform(replaceText("sritharan79"));
        onView(withId(R.id.SigninPageSigninBtn)).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNotNull(goToPage);
        goToPage.finish();
    }

    @Test
    public void TestSignInUserCase2() throws Exception {
        onView(withId(R.id.SigninPageEmailTxt)).perform(replaceText(""));
        onView(withId(R.id.SigninPagePasswordTxt)).perform(replaceText("sritharan79"));
        onView(withId(R.id.SigninPageSigninBtn)).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNull(goToPage);
    }

    @Test
    public void TestSignInUserCase3() throws Exception {
        onView(withId(R.id.SigninPageEmailTxt)).perform(replaceText("braveen79@gmail.com"));
        onView(withId(R.id.SigninPagePasswordTxt)).perform(replaceText(""));
        onView(withId(R.id.SigninPageSigninBtn)).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNull(goToPage);
    }

    @Test
    public void TestSignInUserCase4() throws Exception {
        onView(withId(R.id.SigninPageEmailTxt)).perform(replaceText("braveen79@gmail.com"));
        onView(withId(R.id.SigninPagePasswordTxt)).perform(replaceText("sritharan78"));
        onView(withId(R.id.SigninPageSigninBtn)).perform(click());
        Activity goToPage = getInstrumentation().waitForMonitorWithTimeout(monitorGoTo,5000);
        assertNull(goToPage);
    }

    @After
    public void tearDown() throws Exception {
        signInPage = null;
    }
}