package com.dicoding.picodiploma.moviecatalogue.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_holder.view.*

class MoviePageAdapter(private val listener: (Int) -> Unit) : PagedListAdapter<MoviePopularEntity, MoviePageHolder>(DIFF_CALLBACK) {


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MoviePopularEntity> =
            object : DiffUtil.ItemCallback<MoviePopularEntity>() {
                override fun areItemsTheSame(
                    oldItem: MoviePopularEntity,
                    newItem: MoviePopularEntity
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: MoviePopularEntity,
                    newItem: MoviePopularEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_holder, parent, false)
        return MoviePageHolder(view)
    }

    override fun onBindViewHolder(holder: MoviePageHolder, position: Int) {
        val movie = getItem(position)
        movie?.let { holder.bindData(it, listener) }
    }
}

class MoviePageHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindData(movie: MoviePopularEntity, listener: (Int) -> Unit) {
        containerView.apply {
            txt_title.text = movie.title
            Glide.with(context).load(movie.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(movie.id) }
        }
    }
}

