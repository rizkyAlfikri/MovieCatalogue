package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvdetailentity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvutils.SectionsPagerAdapter
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.makeStatusBarTransparent
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import kotlinx.android.synthetic.main.activity_detail_tv_show.btn_trailer
import kotlinx.android.synthetic.main.activity_detail_tv_show.content_container
import kotlinx.android.synthetic.main.activity_detail_tv_show.img_banner
import kotlinx.android.synthetic.main.activity_detail_tv_show.img_poster
import kotlinx.android.synthetic.main.activity_detail_tv_show.progress_bar
import kotlinx.android.synthetic.main.activity_detail_tv_show.rate_bar
import kotlinx.android.synthetic.main.activity_detail_tv_show.tab_layout
import kotlinx.android.synthetic.main.activity_detail_tv_show.txt_release
import kotlinx.android.synthetic.main.activity_detail_tv_show.view_pager
import org.jetbrains.anko.toast

class DetailTvShowActivity : AppCompatActivity() {

    private lateinit var detailTvViewModel: DetailTvViewModel

    companion object {
        const val EXTRA_TV = "extra_tv"

        fun obtainViewModel(activity: AppCompatActivity): DetailTvViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(DetailTvViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_tv_show)

        makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(
            content_container
        ) { _, insets ->
            insets.consumeSystemWindowInsets()

        }

        val idTv = intent.getIntExtra(EXTRA_TV, 0)

        detailTvViewModel = obtainViewModel(this)
        detailTvViewModel.setTvId(idTv)

        showDetailTv()

        val viewPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        view_pager.adapter = viewPagerAdapter
        tab_layout.setupWithViewPager(view_pager)

        collapse_bar.apply {
            setExpandedTitleColor(ContextCompat.getColor(this@DetailTvShowActivity, android.R.color.transparent))
            setCollapsedTitleTextColor(ContextCompat.getColor(this@DetailTvShowActivity, android.R.color.white  ))
        }
    }

    private fun showDetailTv() {
        progress_bar.visible()
        detailTvViewModel.getTvDetail.observe(this, Observer { resource ->

            when(resource.status){

                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()
                    resource.body?.let { tvDetailWithInfoEntity ->
                        tvDetailWithInfoEntity.tvDetailEntity.also {
                            collapse_bar.title = it.title
                            txt_tv_detail_title.text = it.title
                            txt_release.text = it.releaseDate
                            rate_bar.rating = it.voteAverage.toFloat() / 2

                            Glide.with(this).load(it.imagePath).apply(
                                RequestOptions().placeholder(R.drawable.image_loading)
                                    .error(R.drawable.image_error)
                            ).into(img_poster)

                            Glide.with(this@DetailTvShowActivity).load(it.imagePath)
                                .transform(BlurTransformation(22)).into(img_banner)

                            saveToFavorite(it)
                            it.keyVideo?.let { it1 -> showVideoTrailer(it1) }
                        }
                    }
                }

                ERROR -> {
                    progress_bar.invisible()
                    toast(getString(R.string.failed))
                }
            }
        })
    }

    private fun showVideoTrailer(key: String) {
        val appIntentTrailerVideo = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        val webIntentTrailerVideo = Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$key"))

        btn_trailer.setOnClickListener {
            try {
                startActivity(appIntentTrailerVideo)
            } catch (e: ActivityNotFoundException) {
                startActivity(webIntentTrailerVideo)
            }
        }
    }

    private fun saveToFavorite(data: TvDetailEntity) {
        val tvPopularEntity = TvPopularEntity(
            null,
            data.id,
            data.title,
            data.imagePath,
            data.genres,
            data.voteAverage,
            data.releaseDate,
            true
        )

        detailTvViewModel.getTvFavoriteById.observe(this, Observer { tvShow ->
            if (tvShow == null) {
                val btnTextAdd = getString(R.string.add_to_favorite)
                btn_watchlist.text = btnTextAdd
                btn_watchlist.setOnClickListener {
                    detailTvViewModel.insertTvFavorite(tvPopularEntity)
                    toast("${data.title} ${getString(R.string.has_been_added)}")
                }
            } else {
                val btnTextRemove = getString(R.string.remove_from_favorite)
                btn_watchlist.text = btnTextRemove
                btn_watchlist.setOnClickListener {
                    detailTvViewModel.deleteTvFavorite(tvShow)
                    toast("${data.title} ${getString(R.string.has_been_remove)}")
                }
            }
        })
    }
}
