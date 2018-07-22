package com.mytaxi.android_demo.page_objects;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;

import com.mytaxi.android_demo.R;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.doubleClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class LoginScreen {

    IntentsTestRule mActivityRule;

    public LoginScreen(IntentsTestRule mActivityRule){
        this.mActivityRule = mActivityRule;
    }

    public MainScreen enterCredentials(String username, String password){
        onView(ViewMatchers.withId(R.id.edt_username)).perform(typeText(username));
        onView(withId(R.id.edt_password)).perform(typeText(password), closeSoftKeyboard());
        onView(withId(R.id.btn_login)).perform(click());
        return new MainScreen(mActivityRule);
    }
}
