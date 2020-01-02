package com.dicoding.picodiploma.moviecatalogue.ui.search

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_search_movie_holder.view.*

class SearchMovieHolder(
    private val movie: MoviePopularEntity,
    private val listener: (Int) -> Unit
) : Item() {


    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_title.text = movie.title
            txt_genre.text = movie.genre
            txt_release.text = movie.releaseDate
            txt_rate.text = movie.voteAverage.toString()

            Glide.with(context).load(movie.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(movie.idMovie) }
        }
    }

    override fun getLayout(): Int = R.layout.item_search_movie_holder


}