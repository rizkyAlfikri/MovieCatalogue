package com.dicoding.picodiploma.moviecatalogue.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult
import java.text.SimpleDateFormat

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.GONE
}

fun Activity.makeStatusBarTransparent() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        window.apply {
            clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                decorView.systemUiVisibility =
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            } else {
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            }
            statusBarColor = Color.TRANSPARENT
        }
    }
}

fun View.setMarginTop(marginTop: Int) {
    val menuLayoutParams = this.layoutParams as ViewGroup.MarginLayoutParams
    menuLayoutParams.setMargins(0, marginTop, 0, 0)
    this.layoutParams = menuLayoutParams
}

fun movieGenreBuilder(genreId: List<Int>, movieGenre: List<MovieGenreResult>): String {
    val genreBuilder = StringBuilder()

    for (i in genreId.indices) {
        if (i < genreId.size - 1) {
            movieGenre.forEach {
                if (genreId[i] == it.id) {
                    genreBuilder.append(it.name)
                    genreBuilder.append(", ")
                }
            }
        } else {
            movieGenre.forEach {
                if (genreId[i] == it.id) {
                    genreBuilder.append(it.name)
                }
            }
        }
    }

    return genreBuilder.toString()
}

fun movieDetailGenreBuilder(listGenre: List<MovieGenreResult>): String {
    val genreBuilder = StringBuilder()
    for (i in listGenre.indices) {
        if (i < listGenre.size - 1) {
            genreBuilder.append(listGenre[i].name)
            genreBuilder.append(", ")
        } else {
            genreBuilder.append(listGenre[i].name)
        }
    }

    return genreBuilder.toString()

}

fun tvGenreBuilder(genreId: List<Int>, tvGenre: List<TvGenreResult>): String {
    val genreBuilder = StringBuilder()
    for (i in genreId.indices) {
        if (i < genreId.size - 1) {
            tvGenre.forEach {
                if (genreId[i] == it.id) {
                    genreBuilder.append(it.name)
                    genreBuilder.append(", ")
                }
            }
        } else {
            tvGenre.forEach {
                if (genreId[i] == it.id) {
                    genreBuilder.append(it.name)
                }
            }
        }
    }

    return genreBuilder.toString()
}

fun genreTvDetailBuilder(listGenre: List<TvGenreResult>): String {
    val genreBuilder = StringBuilder()

    for (i in listGenre.indices) {
        if (i < listGenre.size - 1) {
            genreBuilder.append(listGenre[i].name)
            genreBuilder.append(", ")
        } else {
            genreBuilder.append(listGenre[i].name)
        }
    }

    return genreBuilder.toString()
}


fun <T> LiveData<T>.observerForTesting(block: () -> Unit) {
    val observer = Observer<T>{ }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}

fun convertMinToHour(minute: Int): String {
    return if (minute > 0) {
        val hour = minute / 60
        val minutes = minute % 60
        "$hour h $minutes min"
    } else {
        "-"
    }
}

@SuppressLint("SimpleDateFormat")
fun convertDate(date: String): String {
    if (date.isEmpty() || date == "") return ""
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val formattedDate = sdf.parse(date)
    val newSdf = SimpleDateFormat("dd-MMM-yyyy")
    return formattedDate?.let { newSdf.format(it) }.toString()
}

fun convertPeopleGender(gender: Int): String {
    return if (gender == 1) {
        "Female"
    } else if (gender == 2) {
        "Male"
    } else {
        " - "
    }
}

@SuppressLint("SimpleDateFormat")
fun convertBirthDay(date: String): String {
    if (date.isEmpty() || date == "") return ""
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val formattedDate = sdf.parse(date)
    val newSdf = SimpleDateFormat("EEE, dd-MMM-yyyy")
    return formattedDate?.let { newSdf.format(it) }.toString()
}

interface MyAdapterClickListener <T> {
    fun onItemClicked(data: T, state: Boolean)
}

