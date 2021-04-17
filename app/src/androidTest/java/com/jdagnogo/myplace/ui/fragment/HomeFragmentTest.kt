package com.jdagnogo.myplace.ui.fragment

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.jdagnogo.myplace.R
import com.jdagnogo.myplace.ui.MainActivity
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class HomeFragmentTest {
    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java)

    /**
     * When we open the app for the first time we should see only the search bar
     */
    @Test
    fun test_when_start_for_th_first_time() {
        val searchBar = onView(
            Matchers.allOf(
                withId(R.id.search_bar),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        withId(R.id.container),
                        ViewMatchers.withParent(withId(R.id.fragment_container))
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        searchBar.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun test_searching_milan_should_get_the_venue() {
        search("milan")

        Thread.sleep(3000)

        isTextViewDisplayed(R.id.venue_title, "Piazza del Duomo", R.id.venue_container)
    }

    @Test
    fun test_searching_juve_should_get_the_error_wrong_location() {
        search("juve")

        Thread.sleep(3000)

        isTextViewDisplayed(R.id.error_message, "Wrong Localization", R.id.error_view)
    }

    private fun isTextViewDisplayed(id: Int, withName: String, parentId: Int) {
        val textView = onView(
            Matchers.allOf(
                withId(id), ViewMatchers.withText(withName),
                ViewMatchers.withParent(
                    Matchers.allOf(
                        withId(parentId),
                        ViewMatchers.withParent(withId(R.id.container))
                    )
                ),
                ViewMatchers.isDisplayed()
            )
        )
        textView.check(ViewAssertions.matches(ViewMatchers.withText(withName)))
    }

    private fun search(query: String) {
        val editText = Espresso.onView(
            Matchers.allOf(
                ViewMatchers.withId(R.id.search_repo),
                childAtPosition(
                    childAtPosition(
                        ViewMatchers.withId(R.id.search_bar),
                        0
                    ),
                    0
                ),
                ViewMatchers.isDisplayed()
            )
        )
        editText.perform(ViewActions.replaceText(query), ViewActions.closeSoftKeyboard())
        editText.perform(ViewActions.pressImeActionButton())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }

}