package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmoviereview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviereviewentity.MovieReviewEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_review.*

class MovieReviewAdapter : RecyclerView.Adapter<MovieReviewHolder>() {

    private val listMovieReview = mutableListOf<MovieReviewEntity>()

    fun setMovieReviewData(list: List<MovieReviewEntity>) {
        listMovieReview.clear()
        listMovieReview.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieReviewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_review, parent, false)

        return MovieReviewHolder(view)
    }

    override fun getItemCount(): Int = listMovieReview.size

    override fun onBindViewHolder(holder: MovieReviewHolder, position: Int) {
        holder.apply {
            bindData(listMovieReview[position])
        }
    }
}

class MovieReviewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindData(data: MovieReviewEntity) {
        containerView.apply {
            txt_author.text = data.author
            txt_content.text = data.content

        }
    }

}
