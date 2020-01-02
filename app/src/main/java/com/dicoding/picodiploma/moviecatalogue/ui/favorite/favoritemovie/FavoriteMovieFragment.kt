package com.dicoding.picodiploma.moviecatalogue.ui.favorite.favoritemovie


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity.Companion.EXTRA_MOVIE
import com.dicoding.picodiploma.moviecatalogue.ui.favorite.FavoriteViewModel
import com.dicoding.picodiploma.moviecatalogue.utils.MyAdapterClickListener
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_favorite_movie.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class FavoriteMovieFragment : Fragment(), MyAdapterClickListener<MoviePopularEntity> {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var movieAdapter: FavoriteMoviePageAdapter

    companion object {
        private fun obtainViewModel(activity: FragmentActivity): FavoriteViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
        }

        fun newInstance(): FavoriteMovieFragment {
            return FavoriteMovieFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        favoriteViewModel = obtainViewModel(requireActivity())

        return inflater.inflate(R.layout.fragment_favorite_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        movieAdapter = FavoriteMoviePageAdapter(this) {
            startActivity<DetailMovieActivity>(
                EXTRA_MOVIE to it
            )
        }

        rv_movie_favorite.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        showMovieFavorite()
    }


    private fun showMovieFavorite() {
        favoriteViewModel.getAllMovieFavorite().observe(this, Observer {
            when (it.status) {
                Status.LOADING -> progress_bar.visible()

                Status.SUCCESS -> {
                    progress_bar.invisible()
                    movieAdapter.submitList(it.body)
                    movieAdapter.notifyDataSetChanged()

                }

                Status.ERROR -> {
                    progress_bar.invisible()
                    toast(getString(R.string.failed))
                }

            }
        })
    }

    override fun onItemClicked(data: MoviePopularEntity, state: Boolean) {
        favoriteViewModel.deleteMovieFavoriteById(data.idMovie)
        rv_movie_favorite.snackbar(
            getString(R.string.are_sure_delete),
            getString(R.string.undo)
        ) {
            data.isFavorite = true
            favoriteViewModel.insertMovieFavorite(data)
            movieAdapter.notifyDataSetChanged()
        }
    }
}
