package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmovieuitls

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmovieimage.DetailMovieImageFragment
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmovieinfo.DetailMovieInfoFragment
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmoviereview.DetailMovieReviewFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_detail_info,
    R.string.tab_detail_review,
    R.string.tab_detail_image
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return DetailMovieInfoFragment.newInstance()
            1 -> return DetailMovieReviewFragment.newInstance()
            2 -> return DetailMovieImageFragment.newInstance()
        }
        return PlaceholderFragment.newInstance(
            position + 1
        )
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        // Show 3 total pages.
        return 3
    }
}