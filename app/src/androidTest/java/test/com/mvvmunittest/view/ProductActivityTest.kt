package test.com.mvvmunittest.view


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import androidx.test.runner.AndroidJUnit4
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import test.com.mvvmunittest.R

@LargeTest
@RunWith(AndroidJUnit4::class)
class ProductActivityTest {

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(ProductActivity::class.java)

    @Test
    fun productActivityTest() {
        val appCompatEditText = onView(
            allOf(withId(R.id.productItems),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3),
                    1),
                isDisplayed()))
        appCompatEditText.perform(replaceText("0"), closeSoftKeyboard())

        val materialButton = onView(
            allOf(withId(R.id.buy), withText("buy"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    5),
                isDisplayed()))
        materialButton.perform(click())

        val textView = onView(
            allOf(withId(android.R.id.message), withText("Buy fail"),
                withParent(withParent(withId(R.id.scrollView))),
                isDisplayed()))
        textView.check(matches(withText("Buy fail")))

        Espresso.pressBack()
        Thread.sleep(1000)

        val appCompatEditText2 = onView(
            allOf(withId(R.id.productItems), withText("0"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3),
                    1),
                isDisplayed()))
        appCompatEditText2.perform(replaceText("5"))

        val appCompatEditText3 = onView(
            allOf(withId(R.id.productItems), withText("5"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3),
                    1),
                isDisplayed()))
        appCompatEditText3.perform(closeSoftKeyboard())

        val materialButton2 = onView(
            allOf(withId(R.id.buy), withText("buy"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    5),
                isDisplayed()))
        materialButton2.perform(click())

        val textView3 = onView(
            allOf(withId(android.R.id.message), withText("Buy success"),
                withParent(withParent(withId(R.id.scrollView))),
                isDisplayed()))
        textView3.check(matches(withText("Buy success")))

        Espresso.pressBack()
        Thread.sleep(1000)

        val appCompatEditText4 = onView(
            allOf(withId(R.id.productItems), withText("5"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3),
                    1),
                isDisplayed()))
        appCompatEditText4.perform(replaceText("11"))

        val appCompatEditText5 = onView(
            allOf(withId(R.id.productItems), withText("11"),
                childAtPosition(
                    childAtPosition(
                        withClassName(`is`("android.widget.LinearLayout")),
                        3),
                    1),
                isDisplayed()))
        appCompatEditText5.perform(closeSoftKeyboard())

        val materialButton3 = onView(
            allOf(withId(R.id.buy), withText("buy"),
                childAtPosition(
                    childAtPosition(
                        withId(android.R.id.content),
                        0),
                    5),
                isDisplayed()))
        materialButton3.perform(click())

        val textView5 = onView(
            allOf(withId(android.R.id.message), withText("Buy fail"),
                withParent(withParent(withId(R.id.scrollView))),
                isDisplayed()))
        textView5.check(matches(withText("Buy fail")))
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int): Matcher<View> {

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
