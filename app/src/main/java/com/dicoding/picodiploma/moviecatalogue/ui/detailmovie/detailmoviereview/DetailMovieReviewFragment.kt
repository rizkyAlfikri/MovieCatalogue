package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmoviereview


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
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieViewModel
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.fragment_detail_movie_review.*
import org.jetbrains.anko.support.v4.toast


class DetailMovieReviewFragment : Fragment() {

    private lateinit var detailMovieViewModel: DetailMovieViewModel
    private lateinit var movieReviewAdapter: MovieReviewAdapter

    companion object {
        fun newInstance(): DetailMovieReviewFragment {
            return DetailMovieReviewFragment()
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
        detailMovieViewModel = obtainViewModel(requireActivity())
        return inflater.inflate(R.layout.fragment_detail_movie_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieReviewAdapter = MovieReviewAdapter()

        val decorative =
            DividerItemDecoration(rv_movie_review.context, DividerItemDecoration.HORIZONTAL)

        rv_movie_review.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieReviewAdapter
            addItemDecoration(decorative)
        }

        showMovieReview()
    }

    private fun showMovieReview() {

        detailMovieViewModel.getMovieDetail.observe(this, Observer { resource ->
            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()
                    resource.body?.let { movieDetailWithInfoEntity ->
                        movieDetailWithInfoEntity.listMovieReview.also {
                            movieReviewAdapter.setMovieReviewData(it)
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
