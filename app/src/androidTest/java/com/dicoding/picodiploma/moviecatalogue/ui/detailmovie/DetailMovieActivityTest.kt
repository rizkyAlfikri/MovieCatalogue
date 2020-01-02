package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
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

class DetailMovieActivityTest {
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
    @OkReplay(tape = "instrumental_test_load_detail_movie_test", mode = TapeMode.READ_ONLY)
    fun load_detail_movie_and_click_watchlist_button() {
        onView(withId(R.id.navigation_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.navigation_movie)).perform(click())
        onView(withId(R.id.rv_movie))
            .check(matches(isDisplayed()))
        onView(withId(R.id.rv_movie))
            .check(RecyclerViewItemCountAssertion(20))
        onView(withId(R.id.rv_movie)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )

        onView(withId(R.id.txt_detail_movie_title)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist_movie)).check(matches(isDisplayed()))

        onView(withText(R.string.add_to_favorite)).check(matches(isDisplayed()))
        onView(withId(R.id.btn_watchlist_movie)).perform(click())
        onView(withText(R.string.remove_from_favorite)).check(matches(isDisplayed()))
    }
}