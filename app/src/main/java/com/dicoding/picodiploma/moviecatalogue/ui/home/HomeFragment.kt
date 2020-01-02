package com.dicoding.picodiploma.moviecatalogue.ui.home

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
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity.Companion.EXTRA_TV
import com.dicoding.picodiploma.moviecatalogue.ui.search.SearchActivity
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class HomeFragment : Fragment() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var homeAdapter: GroupAdapter<ViewHolder>

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }

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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        homeAdapter = GroupAdapter()

        rv_home.apply {
            layoutManager = GridLayoutManager(context, 2)
            adapter = homeAdapter
        }

        mainViewModel.movieSpinnerPosition = 0
        mainViewModel.tvSpinnerPosition = 0
        mainViewModel.setMovieSpinner()
        mainViewModel.setTvSpinner()

        populateMovieData()
        populateTvData()

        search_view.setOnClickListener { startActivity<SearchActivity>() }

    }

    private fun populateMovieData() {
        mainViewModel.getMoviePopularData.observe(this, Observer { resource ->
            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()

                    resource.body?.let { list ->
                        list.map { moviePopularEntity ->
                            homeAdapter.add(HomeMovieHolder(moviePopularEntity) {
                                startActivity<DetailMovieActivity>(
                                    EXTRA_MOVIE to it
                                )
                            })
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

    private fun populateTvData() {
        mainViewModel.getTvPopularData.observe(this, Observer { resource ->
            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()

                    resource.body?.let { list ->
                        list.map { tvPopularEntity ->
                            homeAdapter.add(HomeTvHolder(tvPopularEntity) {
                                startActivity<DetailTvShowActivity>(
                                    EXTRA_TV to it
                                )
                            })
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