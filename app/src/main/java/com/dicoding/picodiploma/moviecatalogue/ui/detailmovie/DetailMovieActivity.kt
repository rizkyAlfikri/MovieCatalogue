package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.utils.convertMinToHour
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.makeStatusBarTransparent
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail_movie.*
import kotlinx.android.synthetic.main.content_activity_tv_detail.*
import org.jetbrains.anko.toast

class DetailMovieActivity : AppCompatActivity() {

    private lateinit var movieViewModel: DetailMovieViewModel

    companion object {
        const val EXTRA_MOVIE = "extra_movie"

        fun obtainViewModel(activity: AppCompatActivity): DetailMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(DetailMovieViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_movie)

        makeStatusBarTransparent()
        ViewCompat.setOnApplyWindowInsetsListener(
            content_container
        ) { _, insets ->
            insets.consumeSystemWindowInsets()

        }

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayShowHomeEnabled(false)

        val idMovie = intent.getIntExtra(EXTRA_MOVIE, 0)
        Log.e(DetailMovieActivity::class.java.simpleName, "idMovie = $idMovie")

        movieViewModel = obtainViewModel(this)
        movieViewModel.setIdMovie(idMovie)
        showDetailMovie()



        btn_watchlist.setOnClickListener {
            toast("this feature is coming soon")
        }

        btn_trailer.setOnClickListener {
            toast("This feature is coming soon")
        }

    }

    private fun showDetailMovie() {

        movieViewModel.getMovieDetail().observe(this, Observer { resource ->

            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()
                    resource.body?.let {
                        txt_title.text = it.title
                        rate_bar.rating = it.voteAverage.toFloat() / 2
                        txt_release.text = it.releaseDate
                        txt_genre.text = it.genres
                        txt_desc.text = it.overview
                        txt_duration.text = convertMinToHour(it.runtime)
                        txt_status.text = it.status
                        txt_tag.text = it.tagLine ?: " - "
                        txt_web.text = it.homepage ?: " - "
                        Glide.with(this@DetailMovieActivity).load(it.imagePath).apply {
                            RequestOptions().placeholder(R.drawable.image_loading)
                                .error(R.drawable.image_error)
                                .transform(RoundedCornersTransformation(50, 4))
                        }.into(img_poster)

                        Glide.with(this@DetailMovieActivity).load(it.imagePath)
                            .transform(BlurTransformation(22)).into(img_banner)

                        saveToFavorite(it)
                    }
                }

                ERROR -> {
                    progress_bar.invisible()
                    toast("Failed request data to network, please refresh again")
                }
            }
        })
    }

    private fun saveToFavorite(data: MovieDetailEntity) {
        movieViewModel.getMovieDetailById(data.id).observe(this, Observer { movie ->
            var isFavorite = movie != null
            val btnText = "Add To Favorite"
            val btnTextNo = "Remove from favorite list"

            if (movie == null) {
                btn_watchlist.text = btnText
                btn_watchlist.setOnClickListener {
                    movieViewModel.insertMovieDetailFavorite(data)
                    toast("${data.title} has been added to favorite list")
                    isFavorite = !isFavorite
                }
            } else {
                btn_watchlist.text = btnTextNo
                btn_watchlist.setOnClickListener {
                    movieViewModel.deleteMovieDetailFavorite(data)
                    toast("${data.title} has been remove from favorite list")
                    isFavorite = !isFavorite
                }
            }
        })
    }
}



