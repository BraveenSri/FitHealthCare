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
public class SignUpPageTest {
    @Rule
    public ActivityTestRule<SignUpPage> signUpPageActivityTestRule = new ActivityTestRule<SignUpPage>(SignUpPage.class);
    public SignUpPage signUpPage = null;

    Instrumentation.ActivityMonitor monitorSignIn = getInstrumentation().addMonitor(SignInPage.class.getName(),null,false);

    @Before
    public void setUp() throws Exception {
        signUpPage = signUpPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception {
        View view = signUpPage.findViewById(R.id.SignUpLogoImg);
        assertNotNull(view);
    }

    @Test
    public void TestSignInPageVisibility() throws Exception {
        onView(withId(R.id.SignupPageSigninBtn)).perform(click());
        Activity signInPage = getInstrumentation().waitForMonitorWithTimeout(monitorSignIn,5000);
        assertNotNull(signInPage);
        signInPage.finish();
    }

    @Test
    public void testSignUpPassword1Visibility() throws Exception {
        onView(withId(R.id.SignupPagePassword1Eye)).perform(click());
        int actualOutput = signUpPage.setPass1Type;
        int expectedOutput = 1;
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void testSignUpPassword2Visibility() throws Exception {
        onView(withId(R.id.SignupPagePassword2Eye)).perform(click());
        int actualOutput = signUpPage.setPass2Type;
        int expectedOutput = 1;
        assertEquals(expectedOutput,actualOutput);
    }

    @Test
    public void testSignUpUserCase1() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText(""));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testSignUpUserCase2() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText("sribraveen96@gmail.com"));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText(""));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testSignUpUserCase3() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText("sribraveen96@gmail.com"));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText(""));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testSignUpUserCase4() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText("sribraveen96@gmail.com"));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText("br@ve"));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText("br@ve"));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testSignUpUserCase5() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText("sribraveen96@gmail.com"));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText("br@ve"));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testSignUpUserCase6() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText("sribraveen96@gmail.com"));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText("br@ve"));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testSignUpUserCase7() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText("sribraveen96@gmail.com"));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText("sritharan79"));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.INVISIBLE);
    }

    @Test
    public void testSignUpUserCase8() throws Exception {
        onView(withId(R.id.SignupPageEmailTxt)).perform(replaceText("sribraveen96@gmail.com"));
        onView(withId(R.id.SignupPagePassword1Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPagePassword2Txt)).perform(replaceText("Sr1br@ve"));
        onView(withId(R.id.SignupPageSignupBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageVerifyBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.VISIBLE);
    }

    @Test
    public void testVerifyUser() throws Exception{
        signUpPage.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                signUpPage.findViewById(R.id.SignupPageVerifyBtn).setVisibility(View.VISIBLE);
                signUpPage.findViewById(R.id.SignupPageSignupBtn).setVisibility(View.INVISIBLE);
            }
        });
        onView(withId(R.id.SignupPageVerifyBtn)).perform(click());
        View view = signUpPage.findViewById(R.id.SignupPageSignupBtn);
        Thread.sleep(2000);
        assertTrue(view.getVisibility() == View.VISIBLE);
    }

    @After
    public void tearDown() throws Exception {
        signUpPage = null;
    }
}