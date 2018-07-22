package com.mytaxi.android_demo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.action.GeneralClickAction;
import android.support.test.espresso.action.GeneralLocation;
import android.support.test.espresso.action.Press;
import android.support.test.espresso.action.Tap;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.runner.AndroidJUnit4;

import com.mytaxi.android_demo.activities.MainActivity;
import com.mytaxi.android_demo.page_objects.DriverScreen;
import com.mytaxi.android_demo.page_objects.LoginScreen;
import com.mytaxi.android_demo.page_objects.MainScreen;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasAction;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withContentDescription;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.internal.util.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    String username;
    String password;

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(MainActivity.class);

    @Before
    public void startUp(){
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

         onView(withId(R.id.edt_username)).perform(typeText(username));
         onView(withId(R.id.edt_password)).perform(typeText(password), closeSoftKeyboard());
         onView(withId(R.id.btn_login)).perform(click());

         onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
         onView(withId(R.id.textSearch)).perform(typeText("sa"));

         onView(withText("Sarah Scott")).inRoot(RootMatchers.withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).perform(click());
         onView(withId(R.id.textViewDriverName)).check(matches(withText("Sarah Scott")));
         onView(withId(R.id.fab)).perform(click());

        intended(hasAction(Intent.ACTION_DIAL));
    }

    @After
    public void tearDown(){
        SharedPreferences mSharedPref = mActivityRule.getActivity().getSharedPreferences("MytaxiPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPref.edit();
        editor.remove("username");
        editor.remove("salt");
        editor.remove("sha256");
        editor.apply();
    }


}
