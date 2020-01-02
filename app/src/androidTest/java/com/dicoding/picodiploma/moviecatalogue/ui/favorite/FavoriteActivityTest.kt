package com.dicoding.picodiploma.moviecatalogue.ui.favorite

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.rule.ActivityTestRule
import com.dicoding.picodiploma.moviecatalogue.MainActivity
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class FavoriteActivityTest {

    @Rule
    @JvmField
    val activityRule = ActivityTestRule(MainActivity::class.java, false, false)


    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)

        activityRule.launchActivity(null)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun favorite_fragment_delete_movie() {

        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())

        onView(withId(R.id.rv_movie_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie_favorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist_movie)).check(matches(isDisplayed())).perform(click())
        onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.rv_movie_favorite)).check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist_movie)).check(matches(isDisplayed())).perform(click())
        onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))

        pressBack()
        pressBack()
    }

    @Test
    fun favorite_fragment_delete_tv() {
        onView(withId(R.id.action_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.action_favorite)).perform(click())

        onView(withText("TV SHOWS")).check(matches(isDisplayed()))
        onView(withText("TV SHOWS")).perform(click())


        onView(withId(R.id.rv_tv_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_tv_favorite)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist)).check(matches(isDisplayed())).perform(click())
        onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))

        pressBack()

        onView(withId(R.id.rv_tv_favorite)).check(matches(isDisplayed()))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    click()
                )
            )

        onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist)).check(matches(isDisplayed())).perform(click())
        onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))

        pressBack()
        pressBack()


    }


}