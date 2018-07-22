package com.mytaxi.android_demo.page_objects;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.RootMatchers;
import android.support.test.espresso.matcher.ViewMatchers;

import com.mytaxi.android_demo.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.CoreMatchers.not;

public class MainScreen {


    IntentsTestRule mActivityRule;

    public MainScreen(IntentsTestRule mActivityRule){
        this.mActivityRule = mActivityRule;
        onView(ViewMatchers.withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

    public MainScreen performSearch(String textToSearch){
        onView(withId(R.id.textSearch)).perform(typeText(textToSearch));
        return this;
    }

    public DriverScreen selectDriverFromList(String driver){
        onView(withText(driver)).inRoot(RootMatchers.withDecorView(not(mActivityRule.getActivity().getWindow().getDecorView()))).perform(click());
        return new DriverScreen(mActivityRule);
    }

}
