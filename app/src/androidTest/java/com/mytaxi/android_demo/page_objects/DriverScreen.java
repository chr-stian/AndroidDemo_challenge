package com.mytaxi.android_demo.page_objects;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;

import com.mytaxi.android_demo.R;

import static android.support.test.espresso.Espresso.onView;

import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

public class DriverScreen {
    IntentsTestRule mActivityRule;

    public DriverScreen(IntentsTestRule mActivityRule){
        this.mActivityRule = mActivityRule;
    }

    public void displayedDriverNameIs(String driverName){
        onView(ViewMatchers.withId(R.id.textViewDriverName)).check(matches(withText(driverName)));
    }

    public void callDriver(){
        onView(withId(R.id.fab)).perform(click());
    }
}
