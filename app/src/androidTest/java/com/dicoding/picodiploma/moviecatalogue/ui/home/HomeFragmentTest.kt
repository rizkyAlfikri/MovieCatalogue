package com.dicoding.picodiploma.moviecatalogue.ui.home

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
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

class HomeFragmentTest {
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
    @OkReplay(tape = "instrumental_test_load_home_fragment_test", mode = TapeMode.READ_WRITE)
    fun load_home_fragment_data() {
        onView(withId(R.id.rv_home)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_home)).check(RecyclerViewItemCountAssertion(40))
    }
}