package com.dicoding.picodiploma.moviecatalogue.ui.favorite.favoritemovie

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
import com.dicoding.picodiploma.moviecatalogue.utils.MyAdapterClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_favorite_movie_holder.view.*

class FavoriteMoviePageAdapter(
    private val adapterClickListener: MyAdapterClickListener<MoviePopularEntity>,
    private val listener: (Int) -> Unit
) :
    PagedListAdapter<MoviePopularEntity, MovieFavoriteHolder>(
        DIFF_CALLBACK
    ) {


    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<MoviePopularEntity> =
            object : DiffUtil.ItemCallback<MoviePopularEntity>() {
                override fun areItemsTheSame(
                    oldItem: MoviePopularEntity,
                    newItem: MoviePopularEntity
                ): Boolean {
                    return oldItem.idMovie == newItem.idMovie
                }

                override fun areContentsTheSame(
                    oldItem: MoviePopularEntity,
                    newItem: MoviePopularEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieFavoriteHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_movie_holder, parent, false)
        return MovieFavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: MovieFavoriteHolder, position: Int) {
        val movieDetailEntity = getItem(position)
        movieDetailEntity?.let { holder.bindData(it, adapterClickListener, listener) }
    }
}

class MovieFavoriteHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(
        data: MoviePopularEntity,
        adapterClickListener: MyAdapterClickListener<MoviePopularEntity>,
        listener: (Int) -> Unit
    ) {
        containerView.apply {
            txt_title.text = data.title
            txt_genre.text = data.genre
            txt_release.text = data.releaseDate
            txt_rate.text = "${data.voteAverage}"
            Glide.with(context).load(data.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            if (data.isFavorite) {
                Glide.with(context).load(R.drawable.favorite_red_rounded).into(img_favorite)
            } else {
                Glide.with(context).load(R.drawable.favorite_gray_rounded).into(img_favorite)
            }

            img_favorite.setOnClickListener {
                if (!data.isFavorite) {
                    Glide.with(context).load(R.drawable.favorite_red_rounded).into(img_favorite)
                } else {
                    Glide.with(context).load(R.drawable.favorite_gray_rounded).into(img_favorite)
                }

                adapterClickListener.onItemClicked(data, data.isFavorite)
                data.isFavorite = !data.isFavorite

            }


            setOnClickListener { listener(data.idMovie) }
        }
    }

}
