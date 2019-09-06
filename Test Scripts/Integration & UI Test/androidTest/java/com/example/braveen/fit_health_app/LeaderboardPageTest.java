package com.example.braveen.fit_health_app;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Created by Braveen on 12/03/2018.
 */
@RunWith(AndroidJUnit4.class)
public class LeaderboardPageTest {
    @Rule
    public ActivityTestRule<LeaderboardPage> leaderboardPageActivityTestRule = new ActivityTestRule<LeaderboardPage>(LeaderboardPage.class);
    public LeaderboardPage leaderboardPage = null;

    @Before
    public void setUp() throws Exception {
        leaderboardPage = leaderboardPageActivityTestRule.getActivity();
    }

    @Test
    public void testPageVisibility() throws Exception {
        View view = leaderboardPage.findViewById(R.id.LeaderboardTitle);
        assertNotNull(view);
    }

    @After
    public void tearDown() throws Exception {
        leaderboardPage = null;
    }
}