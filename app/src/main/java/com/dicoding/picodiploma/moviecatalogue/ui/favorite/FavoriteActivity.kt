package com.dicoding.picodiploma.moviecatalogue.ui.favorite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.viewpager.widget.ViewPager
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.ui.favorite.favoriteutils.SectionsPagerAdapter
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.google.android.material.tabs.TabLayout

class FavoriteActivity : AppCompatActivity() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    companion object{
        private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        favoriteViewModel = obtainViewModel(this)

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                this,
                supportFragmentManager
            )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

    }
}