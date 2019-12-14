package com.dicoding.picodiploma.moviecatalogue.utils

import androidx.test.espresso.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

object EspressoIdlingResource{
    private const val RESOURCE = "GLOBAL"
    private val countingIdlingResource = CountingIdlingResource(RESOURCE)

    val idlingResource: IdlingResource
    get() = countingIdlingResource

    fun idlingIncrement() {
        countingIdlingResource.increment()
    }

    fun idlingDecrement() {
        if (!idlingResource.isIdleNow) {
            countingIdlingResource.decrement()
        }
    }
}