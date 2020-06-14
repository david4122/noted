package org.noted

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.doesNotExist
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.Intents.intending
import androidx.test.espresso.intent.matcher.IntentMatchers.*
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.runner.RunWith
import androidx.test.rule.ActivityTestRule
import org.hamcrest.Matchers.not
import org.hamcrest.core.AllOf.allOf
import org.junit.Test
import java.util.*


@RunWith(AndroidJUnit4::class)
@LargeTest
class NoteTest {

    @get:Rule
    var activityRule: ActivityTestRule<MainActivity> = ActivityTestRule(MainActivity::class.java)

    @get:Rule
    var intentsRule = IntentsTestRule<MainActivity>(MainActivity::class.java)

    @Test
    fun testCreateNote() {

        val testContent = UUID.randomUUID().toString()

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.noteContent)).perform(typeText(testContent))
        closeSoftKeyboard()
        onView(withId(R.id.save)).perform(click())

        onView(withText(testContent)).check(matches(isDisplayed()))
    }

    @Test
    fun testNoteCancel() {
        val testContent = UUID.randomUUID().toString()

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.noteContent)).perform(typeText(testContent))
        closeSoftKeyboard()
        onView(withId(R.id.cancel)).perform(click())

        onView(withText(testContent)).check(doesNotExist())
    }

    @Test
    fun testNoteShare() {
        intending(not(isInternal())).respondWith(
            Instrumentation.ActivityResult(
                Activity.RESULT_OK,
                null
            )
        )


        val testContent = UUID.randomUUID().toString()

        onView(withId(R.id.fab)).perform(click())
        onView(withId(R.id.noteContent)).perform(typeText(testContent))
        closeSoftKeyboard()
        onView(withId(R.id.share)).perform(click())

        intended(allOf(hasAction(Intent.ACTION_SEND), hasData(testContent)))
    }
}