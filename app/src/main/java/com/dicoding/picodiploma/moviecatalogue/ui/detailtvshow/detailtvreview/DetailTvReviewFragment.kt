package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvreview


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
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvViewModel
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.fragment_detail_tv_review.*
import org.jetbrains.anko.support.v4.toast


class DetailTvReviewFragment : Fragment() {

    private lateinit var detailTvViewModel: DetailTvViewModel
    private lateinit var detailTvReviewAdapter: DetailTvReviewAdapter

    companion object {
        fun newInstance(): DetailTvReviewFragment {
            return DetailTvReviewFragment()
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
        return inflater.inflate(R.layout.fragment_detail_tv_review, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        detailTvReviewAdapter = DetailTvReviewAdapter()

        rv_tv_review.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = detailTvReviewAdapter
        }

        showReviewTv()

    }

    private fun showReviewTv() {

        detailTvViewModel.getTvDetail.observe(this, Observer { resource ->

            when (resource.status) {
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()

                    resource.body?.let { tvDetailWithInfoEntity ->
                        tvDetailWithInfoEntity.listTvReview.also {
                            detailTvReviewAdapter.setTvReviewData(it)
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
