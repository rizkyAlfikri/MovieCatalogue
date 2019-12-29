package com.dicoding.picodiploma.moviecatalogue

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.dicoding.picodiploma.moviecatalogue.ui.favorite.FavoriteActivity
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    companion object{
        private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        toolbar.title = getString(R.string.app_name)
        toolbar.setTitleTextColor(ContextCompat.getColor(this, android.R.color.white))

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        nav_view.setupWithNavController(navController)

        mainViewModel = obtainViewModel(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> startActivity<FavoriteActivity>()
        }

        return super.onOptionsItemSelected(item)
    }
}
