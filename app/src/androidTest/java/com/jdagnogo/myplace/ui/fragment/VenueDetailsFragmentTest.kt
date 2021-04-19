package com.jdagnogo.myplace.ui.fragment

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jdagnogo.myplace.R
import com.jdagnogo.myplace.ui.MainActivity
import com.jdagnogo.myplace.ui.fragment.HomeFragmentTest.Companion.search
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class VenueDetailsFragmentTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun test_venueDetailsFragment_shoul_display_the_details_information() {
        search("milan")

        Thread.sleep(3000)

        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.venue_title), ViewMatchers.withText("Cracco Cafè"))).perform(click())
        onView(CoreMatchers.allOf(ViewMatchers.withId(R.id.venue_title), ViewMatchers.withText("Cracco Cafè"))).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.venue_rating)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.venue_description)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.venue_photo)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        onView(ViewMatchers.withId(R.id.venue_facebook)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
                // error text should not be displayed
        onView(ViewMatchers.withId(R.id.error_message)).check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }

}