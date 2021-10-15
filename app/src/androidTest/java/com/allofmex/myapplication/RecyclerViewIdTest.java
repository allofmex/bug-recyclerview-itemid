package com.allofmex.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.atomic.AtomicReference;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class RecyclerViewIdTest {

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void actionOnHolder_findById() {
        RecyclerView recyclerView = getActivity(rule).findViewById(R.id.recycler_view);
        int count = recyclerView.getChildCount();
        // all fine in Ui
        assertThat(count, greaterThan(0));
        for (int i = 0; i < count; i++) {
            // ViewHolder itemIds are bound correctly in Ui
            assertEquals(i, recyclerView.getChildViewHolder(recyclerView.getChildAt(i)).getItemId());
        }
        onView(withText(containsString("Pos and id 4")))
                .check(matches(isDisplayed()));
//        wait(2000);

        /*
        Here the problem:

        Search for ViewHolder with itemId=4

        ViewHolder with id 4 is found for
            implementation 'androidx.recyclerview:recyclerview:1.1.0'
        but not found for >=1.2.0 (tested 1.2.1)

        See Logcat (tag "ID_SEARCH")
        All viewHolder provided to matchesSafely have viewHolder.getItemId()=0
        because no id gets bound when RecyclerViewActions.itemsMatching() calls Recyclerview.bindViewHolder()
         */
        onView(withId(R.id.recycler_view))
                .perform(RecyclerViewActions.actionOnHolderItem(new ViewHolderIdMatcher(4), click()));
    }

    private MainActivity getActivity(ActivityScenarioRule<MainActivity> activityScenarioRule) {
        AtomicReference<MainActivity> activityRef = new AtomicReference<>();
        activityScenarioRule.getScenario().onActivity(activityRef::set);
        return activityRef.get();
    }

    public static void wait(int millis) {
        onView(isRoot()).perform(new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "Wait for " + millis + " milliseconds.";
            }

            @Override
            public void perform(UiController uiController, final View view) {
                uiController.loopMainThreadForAtLeast(millis);
            }
        });
    }
}