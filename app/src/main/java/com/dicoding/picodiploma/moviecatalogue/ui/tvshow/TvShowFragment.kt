package com.dicoding.picodiploma.moviecatalogue.ui.tvshow

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
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity.Companion.EXTRA_TV
import com.dicoding.picodiploma.moviecatalogue.ui.search.SearchActivity
import com.dicoding.picodiploma.moviecatalogue.utils.MyAdapterClickListener
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.fragment_tvshow.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast

class TvShowFragment : Fragment(), MyAdapterClickListener<TvPopularEntity> {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var tvPopularAdapter: TvPopularAdapter

    companion object {

        fun obtainViewModel(activity: FragmentActivity): MainViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)
            return ViewModelProviders.of(activity, factory).get(MainViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tvshow, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mainViewModel = obtainViewModel(this@TvShowFragment.requireActivity())

        tvPopularAdapter = TvPopularAdapter(this) {
            startActivity<DetailTvShowActivity>(EXTRA_TV to it)
        }

        rv_tvshow.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = tvPopularAdapter
        }


        val tvSpinner = resources.getStringArray(R.array.spinner_tv)
        val spinnerAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, tvSpinner)
        spinner.adapter = spinnerAdapter
        spinner.setSelection(mainViewModel.tvSpinnerPosition)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                mainViewModel.tvSpinnerPosition = spinner.selectedItemPosition
                mainViewModel.setTvSpinner()
            }
        }

        showTvPopular()

        search_view.setOnClickListener { startActivity<SearchActivity>() }

    }

    private fun showTvPopular() {

        mainViewModel.getTvPopularData.observe(this, Observer { resource ->

            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()
                    resource.body?.let { list ->
                        tvPopularAdapter.setDataTv(list)
                    }
                }

                ERROR -> {
                    progress_bar.invisible()
                    toast("Error, please refresh application")
                }
            }
        })
    }

    override fun onItemClicked(data: TvPopularEntity, state: Boolean) {
        if (state) {
            mainViewModel.insertTvPopular(data)
            toast("${data.title} has been added to favorite list")
        } else {
            mainViewModel.deleteTvFavoriteById(data.idTv)
            toast("${data.title} has been remove from favorite list")

        }
    }
}