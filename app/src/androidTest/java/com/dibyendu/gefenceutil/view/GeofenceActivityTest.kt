package com.dibyendu.gefenceutil.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.dibyendu.gefenceutil.R
import com.microsoft.appcenter.espresso.Factory
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class GeofenceActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(GeofenceActivity::class.java)

    @Rule
    @JvmField
    var mGrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_BACKGROUND_LOCATION"
        )

    @Rule
    @JvmField
    var reportHelper = Factory.getReportHelper()

    @After
    fun tearDown() {
        reportHelper.label("Test has been finished.")
    }

    @Test
    fun geofenceActivityTest() {
        val floatingActionButton = onView(
            allOf(
                withId(R.id.floatingActionButton),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment),
                        0
                    ),
                    2
                ),
                isDisplayed()
            )
        )
        floatingActionButton.perform(click())

        val textInputEditText = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tvLocationName),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText.perform(replaceText("Meea"), closeSoftKeyboard())

        val textInputEditText2 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tvLatitude),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText2.perform(replaceText("1.0975"), closeSoftKeyboard())

        val textInputEditText3 = onView(
            allOf(
                childAtPosition(
                    childAtPosition(
                        withId(R.id.tvLongitude),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        textInputEditText3.perform(replaceText("1.4567"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(
                withId(R.id.btnSubmit), withText("Submit"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment),
                        0
                    ),
                    5
                ),
                isDisplayed()
            )
        )
        materialButton.perform(click())

        val imageButton = onView(
            allOf(
                withId(R.id.floatingActionButton),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.fragment),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        imageButton.check(matches(isDisplayed()))
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
