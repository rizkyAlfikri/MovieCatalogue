package com.dicoding.picodiploma.moviecatalogue.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_home_holder.view.*

class HomeAdapter : RecyclerView.Adapter<HomeRecyclerHolder>() {

    private val listMovie = mutableListOf<MoviePopularEntity>()
    private val listTv = mutableListOf<TvPopularEntity>()
    private var movieListener: (Int) -> Unit = { }
    private var tvListener: (Int) -> Unit = { }

    fun setMovieData(list: List<MoviePopularEntity>, listener: (Int) -> Unit) {
        listMovie.clear()
        listMovie.addAll(list)
        movieListener = listener
        notifyDataSetChanged()
    }

    fun setTvData(list: List<TvPopularEntity>, listener: (Int) -> Unit) {
        listTv.clear()
        listTv.addAll(list)
        tvListener = listener
        notifyDataSetChanged()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecyclerHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_home_holder, parent, false)

        return HomeRecyclerHolder(view)
    }

    override fun getItemCount(): Int = listMovie.size

    override fun onBindViewHolder(holder: HomeRecyclerHolder, position: Int) {
        holder.apply {
            bindMovieData(listMovie[position], movieListener)
            bindTvData(listTv[position], tvListener)
        }
    }
}

class HomeRecyclerHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindMovieData(movieData: MoviePopularEntity, listener: (Int) -> Unit) {
        containerView.apply {
            txt_title_movie.text = movieData.title
            Glide.with(context).load(movieData.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster_movie)

            if (movieData.isFavorite) {
                Glide.with(context).load(R.drawable.favorite_red_rounded).into(img_favorite_movie)
            } else {
                Glide.with(context).load(R.drawable.favorite_gray_rounded).into(img_favorite_movie)
            }

            img_favorite_movie.setOnClickListener {
                if (!movieData.isFavorite) {
                    Glide.with(context).load(R.drawable.favorite_red_rounded)
                        .into(img_favorite_movie)
                } else {
                    Glide.with(context).load(R.drawable.favorite_gray_rounded)
                        .into(img_favorite_movie)
                }

                movieData.isFavorite = !movieData.isFavorite
            }

            setOnClickListener { listener(movieData.idMovie) }
        }
    }

    fun bindTvData(tvData: TvPopularEntity, listener: (Int) -> Unit) {
        containerView.apply {
            txt_title_tv.text = tvData.title
            Glide.with(context).load(tvData.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster_tv)

            if (tvData.isFavorite) {
                Glide.with(context).load(R.drawable.favorite_red_rounded)
                    .into(img_favorite_tv)
            } else {
                Glide.with(context).load(R.drawable.favorite_gray_rounded)
                    .into(img_favorite_tv)
            }

            img_favorite_tv.setOnClickListener {
                if (!tvData.isFavorite) {
                    Glide.with(context).load(R.drawable.favorite_red_rounded)
                        .into(img_favorite_tv)
                } else {
                    Glide.with(context).load(R.drawable.favorite_gray_rounded)
                        .into(img_favorite_tv)
                }

                tvData.isFavorite = !tvData.isFavorite
            }

            setOnClickListener { listener(tvData.idTv) }
        }
    }

}
