package com.dicoding.picodiploma.moviecatalogue.ui.movie

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.dicoding.picodiploma.moviecatalogue.MainActivity
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.RecyclerViewItemCountAssertion
import com.dicoding.picodiploma.moviecatalogue.network.RetrofitService
import com.dicoding.picodiploma.moviecatalogue.utils.EspressoIdlingResource
import okreplay.*
import org.junit.*

class MovieFragmentTest {

    private val okReplayConfig = OkReplayConfig.Builder()
        .tapeRoot(
            AndroidTapeRoot(
                InstrumentationRegistry.getInstrumentation().context, javaClass)
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

        activityRule.launchActivity(null    )
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }


    @Test
    @OkReplay(tape = "instrumental_test_load_popular_movie_test", mode = TapeMode.READ_ONLY)
    fun load_movie_and_click_spinner_for_sorting_movie() {
        onView(withId(R.id.navigation_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.navigation_movie)).perform(click())
        onView(withId(R.id.rv_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie)).check(RecyclerViewItemCountAssertion(20))

        onView(withId(R.id.movie_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_spinner)).perform(click())
        onView(withText("Top Rated Movies")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_movie)).check(RecyclerViewItemCountAssertion(20))

        onView(withId(R.id.movie_spinner)).check(matches(isDisplayed()))
        onView(withId(R.id.movie_spinner)).perform(click())
        onView(withText("Upcoming Movies")).check(matches(isDisplayed())).perform(click())
        onView(withId(R.id.rv_movie)).check(RecyclerViewItemCountAssertion(20))

    }
}