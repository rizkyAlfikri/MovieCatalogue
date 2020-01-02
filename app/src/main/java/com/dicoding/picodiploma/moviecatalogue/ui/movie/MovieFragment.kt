package com.dicoding.picodiploma.moviecatalogue.ui.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.picodiploma.moviecatalogue.MainViewModel
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity.Companion.EXTRA_MOVIE
import com.dicoding.picodiploma.moviecatalogue.ui.search.SearchActivity
import com.dicoding.picodiploma.moviecatalogue.utils.MyAdapterClickListener
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status
import kotlinx.android.synthetic.main.fragment_movie.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class MovieFragment : Fragment(), MyAdapterClickListener<MoviePopularEntity> {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var movieAdapter: MovieAdapter
    private lateinit var sortBy: String

    companion object {
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

        movieAdapter = MovieAdapter(this) {
            startActivity<DetailMovieActivity>(EXTRA_MOVIE to it)
        }


        search_view.setOnClickListener {
            startActivity<SearchActivity>()
        }
        rv_movie.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = movieAdapter
        }

        val movieSpinner = resources.getStringArray(R.array.spinner_movie)
        val spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            movieSpinner
        )
        movie_spinner.adapter = spinnerAdapter
        movie_spinner.setSelection(mainViewModel.movieSpinnerPosition)
        movie_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                sortBy = movie_spinner.selectedItem.toString()
                mainViewModel.movieSpinnerPosition = movie_spinner.selectedItemPosition
                mainViewModel.setMovieSpinner()
            }
        }

        showMovie()

    }

    private fun showMovie() {
        mainViewModel.getMoviePopularData.observe(this@MovieFragment, Observer { resource ->
            when (resource.status) {
                Status.LOADING -> progress_bar.visible()

                Status.SUCCESS -> {
                    progress_bar.invisible()
                    resource.body?.let { data ->
                        movieAdapter.setDataMovie(data)

                    }
                }

                Status.ERROR -> {
                    progress_bar.invisible()
                    toast(getString(R.string.failed))
                }
            }
        })
    }

    override fun onItemClicked(data: MoviePopularEntity, state: Boolean) {
        if (state) {
            mainViewModel.insertMovieFavorite(data)
            rv_movie.adapter?.notifyDataSetChanged()
            toast("${data.title} ${getString(R.string.has_been_added)}")
        } else {
            mainViewModel.deleteMovieFavoriteById(data.idMovie)
            rv_movie.adapter?.notifyDataSetChanged()
            toast("${data.title} ${getString(R.string.has_been_remove)}")
        }
    }


}