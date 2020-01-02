package com.dicoding.picodiploma.moviecatalogue.ui.home

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie_holder.view.*

class HomeMovieHolder(
    private val movieData: MoviePopularEntity,
    private val listener: (Int) -> Unit
) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_title.text = movieData.title
            Glide.with(context).load(movieData.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(movieData.idMovie) }
        }
    }

    override fun getLayout(): Int = R.layout.item_movie_holder
}