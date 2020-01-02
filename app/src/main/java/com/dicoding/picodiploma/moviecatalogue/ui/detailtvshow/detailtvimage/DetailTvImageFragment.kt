package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvimage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.DetailTvViewModel
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.fragment_detail_tv_image.*
import org.jetbrains.anko.support.v4.toast


class DetailTvImageFragment : Fragment() {

    private lateinit var tvImageAdapter: DetailTvImageAdapter
    private lateinit var detailTvViewModel: DetailTvViewModel

    companion object {
        fun newInstance(): DetailTvImageFragment {
            return DetailTvImageFragment()
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

        return inflater.inflate(R.layout.fragment_detail_tv_image, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tvImageAdapter = DetailTvImageAdapter()

        rv_tv_images.apply {
            layoutManager = GridLayoutManager(context, 2)
            setHasFixedSize(true)
            adapter = tvImageAdapter
        }

        showTvImage()
    }

    private fun showTvImage() {

        detailTvViewModel.getTvDetail.observe(this, Observer { resource ->

            when(resource.status){
                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()

                    resource.body?.let { tvDetailWithInfoEntity ->
                        tvDetailWithInfoEntity.listTvImage.also {
                            tvImageAdapter.setTvImageData(it)
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
