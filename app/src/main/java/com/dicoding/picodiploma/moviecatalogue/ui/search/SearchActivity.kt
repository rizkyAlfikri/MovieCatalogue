package com.dicoding.picodiploma.moviecatalogue.ui.search

import android.app.SearchManager
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.getSystemService
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.DetailMovieActivity.Companion.EXTRA_MOVIE
import com.dicoding.picodiploma.moviecatalogue.ui.detailpeople.DetailPeopleActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailpeople.DetailPeopleActivity.Companion.EXTRA_PEOPLE
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity.Companion.EXTRA_TV
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_search.*
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class SearchActivity : AppCompatActivity() {

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var searchAdapter: GroupAdapter<ViewHolder>
    private lateinit var keyWord: String

    companion object {
        fun obtainViewModel(activity: AppCompatActivity): SearchViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(SearchViewModel::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN)

        searchAdapter = GroupAdapter()

        searchViewModel = obtainViewModel(this)

        rv_search.apply {
            layoutManager = LinearLayoutManager(this@SearchActivity)
            adapter = searchAdapter
        }

        search()

        val spinnerItem = resources.getStringArray(R.array.spinner)
        val spinnerAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, spinnerItem)
        spinner.adapter = spinnerAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                keyWord = spinner.selectedItem.toString()
                populateData(keyWord)
            }
        }

        Glide.with(this).load(Config.IMG_NOT_FOUND).into(img_notFound)
    }

    private fun search() {
        val searchManager = getSystemService<SearchManager>()

        search_view.apply {
            setSearchableInfo(searchManager?.getSearchableInfo(componentName))
            search_view.onActionViewExpanded()
        }

        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { searchViewModel.setQuery(it) }
                search_view.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun populateData(key: String) {
        searchViewModel.getSearchData.observe(this, Observer { resource ->
            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {

                    resource.body?.let { data ->
                        progress_bar.invisible()

                        when (key) {
                            "Movies" -> {
                                searchAdapter.clear()

                                if (data.listMovie.isEmpty()) {
                                    img_notFound.visible()
                                } else {
                                    img_notFound.invisible()
                                }

                                data.listMovie.map { moviePopularEntity ->
                                    searchAdapter.add(SearchMovieHolder(moviePopularEntity) {
                                        startActivity<DetailMovieActivity>(
                                            EXTRA_MOVIE to it
                                        )
                                    })
                                }
                            }

                            "Tv Shows" -> {

                                if (data.listMovie.isEmpty()) {
                                    img_notFound.visible()
                                } else {
                                    img_notFound.invisible()
                                }

                                searchAdapter.clear()
                                data.listTv.map { tvPopularEntity ->
                                    searchAdapter.add(SearchTvHolder(tvPopularEntity) {
                                        startActivity<DetailTvShowActivity>(
                                            EXTRA_TV to it
                                        )
                                    })
                                }
                            }

                            else -> {

                                if (data.listPeople.isEmpty()) {
                                    img_notFound.visible()
                                } else {
                                    img_notFound.invisible()
                                }

                                searchAdapter.clear()
                                data.listPeople.map { people ->
                                    searchAdapter.add(SearchPeopleHolder(people) {
                                        startActivity<DetailPeopleActivity>(
                                            EXTRA_PEOPLE to it
                                        )
                                    })
                                }
                            }
                        }
                    }
                }

                ERROR -> {
                    progress_bar.invisible()
                    toast("Error, please refresh application")
                }
            }
        })
    }
}
