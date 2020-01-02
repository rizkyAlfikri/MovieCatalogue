package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmovieinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviesimilarentity.MovieSimilarEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_holder.view.*

class MovieSimilarVideoAdapter(
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<MovieSimilarHolder>() {

    private val listMovieSimilar =  mutableListOf<MovieSimilarEntity>()

    fun setMovieSimilarData(list: List<MovieSimilarEntity>) {
        listMovieSimilar.clear()
        listMovieSimilar.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieSimilarHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_similar, parent, false)

        return MovieSimilarHolder(view)
    }

    override fun getItemCount(): Int = listMovieSimilar.size

    override fun onBindViewHolder(holder: MovieSimilarHolder, position: Int) {
        holder.bindData(listMovieSimilar[position], listener)
    }
}

class MovieSimilarHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindData(data: MovieSimilarEntity, listener: (Int) -> Unit) {
        containerView.apply {
            txt_title.text = data.title

            Glide.with(context).load(data.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(data.idMovie) }
        }
    }
}
