package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmovieinfo


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity.Companion.EXTRA_MOVIE
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieViewModel
import com.dicoding.picodiploma.moviecatalogue.utils.convertMinToHour
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.fragment_detail_movie_info.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class DetailMovieInfoFragment : Fragment() {

    private lateinit var movieSimilarAdapter: MovieSimilarVideoAdapter
    private lateinit var detailViewModel: DetailMovieViewModel

    companion object {
        fun newInstance(): DetailMovieInfoFragment {
            return DetailMovieInfoFragment()
        }

        private fun obtainViewModel(activity: FragmentActivity): DetailMovieViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(DetailMovieViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        detailViewModel = obtainViewModel(requireActivity())
        return inflater.inflate(R.layout.fragment_detail_movie_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieSimilarAdapter = MovieSimilarVideoAdapter {
            startActivity<DetailMovieActivity>(
                EXTRA_MOVIE to it
            )
        }

        val decorative = DividerItemDecoration(rv_similar.context, DividerItemDecoration.HORIZONTAL)

        rv_similar.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = movieSimilarAdapter
            addItemDecoration(decorative)
        }


        showSimilarMovie()
    }

    private fun showSimilarMovie() {

        detailViewModel.getMovieDetail.observe(this, Observer { resource ->

            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()

                    resource.body?.let { movieDetailWithInfoEntity ->
                        movieDetailWithInfoEntity.movieDetailEntity.also {
                            txt_genre.text = it.genres
                            txt_desc.text = it.overview
                            txt_duration.text = convertMinToHour(it.runtime)
                            txt_status.text = it.status
                            txt_tag.text = it.tagLine ?: " - "
                            txt_web.text = it.homepage ?: " - "
                        }

                        movieDetailWithInfoEntity.listMovieSimilar.also {
                            movieSimilarAdapter.setMovieSimilarData(it)
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


}
