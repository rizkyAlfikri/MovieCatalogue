package com.dicoding.picodiploma.moviecatalogue.testing

import android.os.Bundle
import android.view.Gravity
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.dicoding.picodiploma.moviecatalogue.R

class SingleFragmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val content = FrameLayout(this)
        val params = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.MATCH_PARENT,
            FrameLayout.LayoutParams.MATCH_PARENT,
            Gravity.CENTER
        )

        content.layoutParams = params
        content.id = R.id.nav_host_fragment
    }

    fun setFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().add(R.id.nav_host_fragment, fragment, "TEST")
            .commit()
    }
}