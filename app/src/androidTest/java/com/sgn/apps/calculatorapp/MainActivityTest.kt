package com.sgn.apps.calculatorapp

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.sgn.apps.calculatorapp.activity.MainActivity
import org.hamcrest.core.AllOf.allOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    /*@get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)*/
    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @Test
    fun appLaunchesSuccessfully() {
        // activityRule.launchActivity(null)
        ActivityScenario.launch(MainActivity::class.java)
    }


    @Test
    fun onLaunchCheckAllViewsDisplayed() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.addition_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.subtraction_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.multiplication_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.division_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.equal_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.undo_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.redo_btn)).check(matches(isDisplayed()))
        onView(withId(R.id.result_text_view)).check(matches(isDisplayed()))
        onView(withId(R.id.result_value_text_view)).check(matches(isDisplayed()))
    }

    @Test
    fun whenOkayButtonIsPressedAndAmountIsEmptyTipIsEmpty() {
        ActivityScenario.launch(MainActivity::class.java)

        // 1
        onView(withId(R.id.addition_btn))
            .perform(click())

        // 2
        onView(allOf(withId(R.id.second_operand_edit_text), withText("")))
            .check(matches(isDisplayed()))
    }

    @Test
    fun whenEqualButtonIsPressedAndSecondOperandIsFilledWithInitialZero() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.addition_btn)).perform(click())
        onView(withId(R.id.second_operand_edit_text)).perform(typeText("11"))
        onView(withId(R.id.equal_btn)).perform(click())

        onView(withId(R.id.result_value_text_view))
            .check(matches(withText("11")))
    }
    @Test
    fun whenEqualButtonIsPressedAndSecondOperandIsFilled() {
        ActivityScenario.launch(MainActivity::class.java)
        onView(withId(R.id.addition_btn)).perform(click())
        onView(withId(R.id.second_operand_edit_text)).perform(typeText("11"))
        onView(withId(R.id.equal_btn)).perform(click())

        onView(withId(R.id.addition_btn)).perform(click())
        onView(withId(R.id.second_operand_edit_text)).perform(typeText("11"))
        onView(withId(R.id.equal_btn)).perform(click())

        onView(withId(R.id.result_value_text_view))
            .check(matches(withText("22")))
    }
}