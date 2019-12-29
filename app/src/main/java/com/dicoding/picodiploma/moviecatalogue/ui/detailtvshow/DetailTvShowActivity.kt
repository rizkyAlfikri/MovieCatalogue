package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.convertMinToHour
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.makeStatusBarTransparent
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_detail_tv_show.*
import kotlinx.android.synthetic.main.content_activity_tv_detail.*
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

        btn_trailer.setOnClickListener {
            toast("This feature is coming soon")
        }

        btn_watchlist.setOnClickListener {
            toast("this feature is comming soon")
        }
    }

    private fun showDetailTv() {
        progress_bar.visible()
        detailTvViewModel.getTvDetail.observe(this, Observer { resource ->

            when(resource.status){

                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()
                    resource.body?.let {
                        txt_title.text = it.title
                        txt_genre.text = it.genres
                        txt_release.text = it.releaseDate
                        txt_desc.text = it.overview
                        txt_duration.text = convertMinToHour(it.runtime)
                        txt_status.text = it.status
                        txt_web.text = it.homepage
                        rate_bar.rating = it.voteAverage.toFloat() / 2

                        Glide.with(this).load(it.imagePath).apply(
                            RequestOptions().placeholder(R.drawable.image_loading)
                                .error(R.drawable.image_error)
                        ).into(img_poster)

                        Glide.with(this@DetailTvShowActivity).load(it.imagePath)
                            .transform(BlurTransformation(22)).into(img_banner)

                        saveToFavorite(it)
                    }
                }

                ERROR -> {
                    progress_bar.invisible()
                    toast("An error occurred, please refresh application")
                }
            }



        })
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
                val btnTextAdd = "Add To Favorite"
                btn_watchlist.text = btnTextAdd
                btn_watchlist.setOnClickListener {
                    detailTvViewModel.insertTvFavorite(tvPopularEntity)
                    toast("${data.title} has been add to favortite")
                }
            } else {
                val btnTextRemove = "Remove From Favorite"
                btn_watchlist.text = btnTextRemove
                btn_watchlist.setOnClickListener {
                    detailTvViewModel.deleteTvFavorite(tvShow)
                    toast("${data.title} has been remove from favorite")
                }
            }
        })
    }
}
