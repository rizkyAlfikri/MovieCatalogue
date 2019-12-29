package com.dicoding.picodiploma.moviecatalogue.ui.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.MyAdapterClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_holder.view.*

class MovieAdapter(
    private val adapterClickListener: MyAdapterClickListener<MoviePopularEntity>,
    private val listener: (Int) -> Unit
) :
    RecyclerView.Adapter<MovieViewHolder>() {

    private val listMovie = mutableListOf<MoviePopularEntity>()

    fun setDataMovie(list: List<MoviePopularEntity>) {
        listMovie.clear()
        listMovie.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_holder, parent, false)
        return MovieViewHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bindData(listMovie[position], adapterClickListener, listener)
    }
}

class MovieViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindData(
        movie: MoviePopularEntity,
        adapterClickListener: MyAdapterClickListener<MoviePopularEntity>,
        listener: (Int) -> Unit
    ) {
        containerView.apply {

            txt_title.text = movie.title
            Glide.with(context).load(movie.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            if (movie.isFavorite) {
                Glide.with(context).load(R.drawable.favorite_red_rounded).into(img_favorite)
            } else {
                Glide.with(context).load(R.drawable.favorite_gray_rounded).into(img_favorite)
            }


            img_favorite.setOnClickListener {
                if (!movie.isFavorite) {
                    Glide.with(context).load(R.drawable.favorite_red_rounded).into(img_favorite)
                } else {
                    Glide.with(context).load(R.drawable.favorite_gray_rounded).into(img_favorite)
                }

                movie.isFavorite = !movie.isFavorite
                adapterClickListener.onItemClicked(movie, movie.isFavorite)

            }


            setOnClickListener { listener(movie.idMovie) }
        }
    }
}
