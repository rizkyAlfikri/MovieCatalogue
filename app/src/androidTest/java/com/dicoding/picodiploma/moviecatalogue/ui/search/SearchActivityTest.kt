package com.dicoding.picodiploma.moviecatalogue.ui.search

import android.widget.AutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.dicoding.picodiploma.moviecatalogue.MainActivity
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.network.RetrofitService
import com.dicoding.picodiploma.moviecatalogue.utils.EspressoIdlingResource
import okreplay.*
import org.junit.*

class SearchActivityTest {

    private val okReplayConfig = OkReplayConfig.Builder()
        .tapeRoot(
            AndroidTapeRoot(
                InstrumentationRegistry.getInstrumentation().context, javaClass
            )
        )
        .sslEnabled(true)
        .interceptor(RetrofitService.okReplayInterceptor)
        .build()

    companion object {
        @ClassRule
        @JvmField
        val grantExternalStoragePermissionRule: GrantPermissionRule =
            GrantPermissionRule.grant(
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
    }

    @Rule
    @JvmField
    var activityRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val testRule = OkReplayRuleChain(okReplayConfig, activityRule).get()

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
    @OkReplay(tape = "instrumental_test_load_search_feature_test", mode = TapeMode.READ_ONLY)
    fun load_search_movie_and_add_to_favorite() {

        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).perform(click())

        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).check(matches(isDisplayed())).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            typeText("John Wick"),
            pressImeActionButton()
        )

        onView(withId(R.id.rv_search)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_search)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withText("John Wick")).check(matches(isDisplayed()))
        onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist_movie)).check(matches(isDisplayed())).perform(click())
        onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))
        pressBack()

    }

    @Test
    @OkReplay(tape = "instrumental_search_tv_and_add_to_favorite", mode = TapeMode.READ_ONLY)
    fun search_tv_and_add_to_favorite() {

        onView(withId(R.id.search_view)).check(matches(isDisplayed()))
        onView(withId(R.id.search_view)).perform(click())

        onView(withId(R.id.spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.spinner)).perform(click())
        onView(withText("Tv Shows")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.search_view)).perform(click())
        onView(isAssignableFrom(AutoCompleteTextView::class.java)).perform(
            typeText("Black Clover"),
            pressImeActionButton()
        )

        onView(withId(R.id.rv_search)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_search)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withText("Black Clover")).check(matches(isDisplayed()))
        onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist)).perform(click())
        onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))

        pressBack()

    }
}