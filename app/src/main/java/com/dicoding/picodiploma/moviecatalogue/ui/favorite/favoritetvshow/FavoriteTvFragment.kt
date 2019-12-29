package com.dicoding.picodiploma.moviecatalogue.ui.favorite.favoritetvshow


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
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity.Companion.EXTRA_TV
import com.dicoding.picodiploma.moviecatalogue.ui.favorite.FavoriteViewModel
import com.dicoding.picodiploma.moviecatalogue.utils.MyAdapterClickListener
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.fragment_favorite_tv.*
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class FavoriteTvFragment : Fragment(), MyAdapterClickListener<TvPopularEntity> {

    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var tvPageAdapter: FavoriteTvPageAdapter

    companion object {
        fun newInstance(): FavoriteTvFragment {
            return FavoriteTvFragment()
        }

        private fun obtainViewModel(activity: FragmentActivity): FavoriteViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(FavoriteViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        favoriteViewModel = obtainViewModel(requireActivity())

        return inflater.inflate(R.layout.fragment_favorite_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvPageAdapter = FavoriteTvPageAdapter(this) {
            startActivity<DetailTvShowActivity>(
                EXTRA_TV to it
            )
        }

        rv_tv_favorite.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = tvPageAdapter
        }

        showTvFavorite()
    }

    private fun showTvFavorite() {
        favoriteViewModel.getAllTvFavorite().observe(this, Observer {
            when (it.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()
                    tvPageAdapter.submitList(it.body)
                    tvPageAdapter.notifyDataSetChanged()
                }

                ERROR -> {
                    progress_bar.invisible()
                    toast("Error, please refresh application")
                }
            }
        })
    }

    override fun onItemClicked(data: TvPopularEntity, state: Boolean) {
        favoriteViewModel.deleteTvFavoriteById(data.idTv)
        rv_tv_favorite.snackbar(
            "Are sure want to delete this ?",
            "Undo"
        ) {
            data.isFavorite = true
            favoriteViewModel.insertTvFavorite(data)
            tvPageAdapter.notifyDataSetChanged()
        }
    }
}
