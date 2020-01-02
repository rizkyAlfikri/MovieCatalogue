package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvutils


import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvimage.DetailTvImageFragment
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvinfo.DetailTvInfoFragment
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvreview.DetailTvReviewFragment

private val TAB_TITLES = arrayOf(
    R.string.tab_detail_info,
    R.string.tab_detail_review,
    R.string.tab_detail_image
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when(position){
            0 -> return DetailTvInfoFragment.newInstance()
            1 -> return DetailTvReviewFragment.newInstance()
            2 -> return DetailTvImageFragment.newInstance()
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