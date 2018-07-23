package com.mytaxi.android_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.page_objects.LoginScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;
import android.util.Log;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * The test case challangeTestCaseWithPageObjectPattern corresponds to the test case challangeTestCase
 * but using the Page Object Pattern approach
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private static final String TAG = "ExampleInstrumentedTest";

    String username;
    String password;

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void startUp(){
        Log.d(TAG, "Getting valid credentials...");
        List<String> credentials = TestUtils.getValidCredentials();
         username = credentials.get(0);
         password = credentials.get(1);
    }

    @Test
    public void challangeTestCaseWithPageObjectPattern() {

        new LoginScreen(mActivityRule).enterCredentials(username, password)
       .performSearch("sa").selectDriverFromList("Sarah Scott").callDriver();

        intended(hasAction(Intent.ACTION_DIAL));
    }

    @Test
    public void challangeTestCase() {

         Log.d(TAG, "Entering username...");
         onView(withId(R.id.edt_username)).perform(typeText(username));
         Log.d(TAG, "Entering password...");
         onView(withId(R.id.edt_password)).perform(typeText(password), closeSoftKeyboard());
         Log.d(TAG, "Clicking login button...");
         onView(withId(R.id.btn_login)).perform(click());

         Log.d(TAG, "Checking main layout is displayed...");
         onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
         Log.d(TAG, "Entering string 'sa' into search field...");
         onView(withId(R.id.textSearch)).perform(typeText("sa"));

         Log.d(TAG, "Selecting Sarah Scott from the list...");
         onView(withText("Sarah Scott")).inRoot(RootMatchers.withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).perform(click());
         onView(withId(R.id.textViewDriverName)).check(matches(withText("Sarah Scott")));

         Log.d(TAG, "Clicking dial button...");
         onView(withId(R.id.fab)).perform(click());

         Log.d(TAG, "Verifying DIAL intent has been sent...");
         intended(hasAction(Intent.ACTION_DIAL));
    }

    @After
    public void tearDown(){
        // Remove the last login so that next test starts displaying the login screen
        SharedPreferences mSharedPref = mActivityRule.getActivity().getSharedPreferences("MytaxiPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.remove("username");
        editor.remove("salt");
        editor.remove("sha256");
        editor.apply();
    }


}
