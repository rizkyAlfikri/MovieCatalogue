package com.dicoding.picodiploma.moviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.picodiploma.moviecatalogue.MainViewModel
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity.Companion.EXTRA_MOVIE
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MovieFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var moviePageAdapter: MoviePageAdapter

    companion object{
        private fun obtainViewModel(activity: FragmentActivity): MainViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mainViewModel = obtainViewModel(requireActivity())
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        moviePageAdapter = MoviePageAdapter {
            startActivity<DetailMovieActivity>(EXTRA_MOVIE to it)
        }

        rv_movie.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = moviePageAdapter
        }

        showMovie()
    }

    private fun showMovie() {
        progress_bar.visible()

            mainViewModel.getMoviePopularData().observe(this@MovieFragment, Observer { resource ->
                when(resource.status){
                    Status.LOADING -> progress_bar.visible()

                    Status.SUCCESS -> {
                        progress_bar.invisible()
                        resource.body?.let { pagedList ->
                            moviePageAdapter.submitList(pagedList)
                        }
                    }

                    Status.ERROR ->{
                        progress_bar.invisible()
                        toast("Terjadi kesalahan")
                    }
                }
            })
        }
}