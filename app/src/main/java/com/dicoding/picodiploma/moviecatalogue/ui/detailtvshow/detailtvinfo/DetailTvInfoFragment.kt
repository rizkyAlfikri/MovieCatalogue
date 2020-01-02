package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvinfo


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
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvShowActivity.Companion.EXTRA_TV
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvViewModel
import com.dicoding.picodiploma.moviecatalogue.utils.convertMinToHour
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.fragment_detail_tv_info.*
import org.jetbrains.anko.support.v4.startActivity
import org.jetbrains.anko.support.v4.toast


class DetailTvInfoFragment : Fragment() {

    private lateinit var detailTvSimilar: DetailTvSimilarAdapter
    private lateinit var detailTvViewModel: DetailTvViewModel

    companion object {
        fun newInstance(): DetailTvInfoFragment {
            return DetailTvInfoFragment()
        }

        private fun obtainViewModel(activity: FragmentActivity): DetailTvViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(DetailTvViewModel::class.java)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        detailTvViewModel = obtainViewModel(requireActivity())
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_tv_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailTvSimilar = DetailTvSimilarAdapter {
            startActivity<DetailTvShowActivity>(
                EXTRA_TV to it
            )
        }

        rv_tv_similar.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            adapter = detailTvSimilar
        }


        showTvSimilar()
    }

    private fun showTvSimilar() {

        detailTvViewModel.getTvDetail.observe(this, Observer { resource ->

            when(resource.status){
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()

                    resource.body?.let { tvDetailWithInfoEntity ->
                        tvDetailWithInfoEntity.tvDetailEntity.also {
                            txt_genre.text = it.genres
                            txt_desc.text = it.overview
                            txt_duration.text = convertMinToHour(it.runtime)
                            txt_status.text = it.status
                            txt_web.text = it.homepage
                        }

                        detailTvSimilar.setTvSimilarData(tvDetailWithInfoEntity.listTvSimilar)
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
