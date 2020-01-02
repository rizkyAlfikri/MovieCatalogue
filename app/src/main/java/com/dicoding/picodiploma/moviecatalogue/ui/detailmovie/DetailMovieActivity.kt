package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie

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
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmovieuitls.SectionsPagerAdapter
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.makeStatusBarTransparent
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import kotlinx.android.synthetic.main.activity_detail_movie.*
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

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)

        view_pager.adapter = sectionsPagerAdapter
        tab_layout.setupWithViewPager(view_pager)
        val idMovie = intent.getIntExtra(EXTRA_MOVIE, 0)


        movieViewModel = obtainViewModel(this)
        movieViewModel.setIdMovie(idMovie)
        showDetailMovie()

        collapse_bar.apply {
            setExpandedTitleColor(ContextCompat.getColor(this@DetailMovieActivity, android.R.color.transparent))
            setCollapsedTitleTextColor(ContextCompat.getColor(this@DetailMovieActivity, android.R.color.white  ))
        }

    }

    private fun showDetailMovie() {

        movieViewModel.getMovieDetail.observe(this, Observer { resource ->

            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()
                    resource.body?.let { movieDetailWithInfoEntity ->
                        movieDetailWithInfoEntity.movieDetailEntity.also {
                            collapse_bar.title = it.title
                            txt_detail_movie_title.text = it.title
                            rate_bar.rating = it.voteAverage.toFloat() / 2
                            txt_release.text = it.releaseDate
                            Glide.with(this@DetailMovieActivity).load(it.imagePath).apply {
                                RequestOptions().placeholder(R.drawable.image_loading)
                                    .error(R.drawable.image_error)
                                    .transform(RoundedCornersTransformation(50, 4))
                            }.into(img_poster)

                            Glide.with(this@DetailMovieActivity).load(it.imagePath)
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
        val appTrailerIntent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:$key"))
        val webTrailerIntent =
            Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=$key"))


        btn_trailer.setOnClickListener {
            try {
                startActivity(appTrailerIntent)
            } catch (e: ActivityNotFoundException) {
                startActivity(webTrailerIntent)
            }
        }
    }

    private fun saveToFavorite(data: MovieDetailEntity) {
        val moviePopularEntity = MoviePopularEntity(
            null,
            data.id,
            data.imagePath,
            data.title,
            data.genres,
            data.releaseDate,
            data.voteAverage,
            true
        )

        movieViewModel.getMovieFavoriteById.observe(this, Observer { movie ->
            var isFavorite = movie != null
            val btnText = getString(R.string.add_to_favorite)
            val btnTextNo = getString(R.string.remove_from_favorite)


            if (!isFavorite) {
                btn_watchlist_movie.text = btnText
                btn_watchlist_movie.setOnClickListener {
                    movieViewModel.insertMovieFavorite(moviePopularEntity)
                    toast("${data.title} ${getString(R.string.has_been_added)}")
                    isFavorite = !isFavorite
                }
            } else {
                btn_watchlist_movie.text = btnTextNo
                btn_watchlist_movie.setOnClickListener {
                    movieViewModel.deleteMovieFavorite(movie)
                    toast("${data.title} ${getString(R.string.has_been_remove)}")
                    isFavorite = !isFavorite
                }
            }
        })
    }
}



