package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvreview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvreviewentity.TvReviewEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_review.*

class DetailTvReviewAdapter : RecyclerView.Adapter<DetailTvReviewHolder>() {

    private val listTvReview = mutableListOf<TvReviewEntity>()

    fun setTvReviewData(list: List<TvReviewEntity>) {
        listTvReview.clear()
        listTvReview.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTvReviewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_review, parent, false)

        return DetailTvReviewHolder(view)
    }

    override fun getItemCount(): Int = listTvReview.size

    override fun onBindViewHolder(holder: DetailTvReviewHolder, position: Int) {
        holder.bindData(listTvReview[position])
    }
}

class DetailTvReviewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(tvReview: TvReviewEntity) {
        containerView.apply {
            txt_author.text = tvReview.author
            txt_content.text = tvReview.content
        }
    }
}
