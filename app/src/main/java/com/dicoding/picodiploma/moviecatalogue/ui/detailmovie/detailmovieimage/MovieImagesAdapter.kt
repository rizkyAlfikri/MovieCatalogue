package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie.detailmovieimage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.movieimageentity.MovieImageEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image_holder.view.*

class MovieImagesAdapter : RecyclerView.Adapter<MovieImagesHolder>() {

    private val listImage = mutableListOf<MovieImageEntity>()

    fun setMovieImages(list: List<MovieImageEntity>) {
        listImage.clear()
        listImage.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieImagesHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_holder, parent, false)

        return MovieImagesHolder(view)
    }

    override fun getItemCount(): Int  = listImage.size

    override fun onBindViewHolder(holder: MovieImagesHolder, position: Int) {
        holder.bindData(listImage[position])
    }
}

class MovieImagesHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView),
    LayoutContainer {

    fun bindData(data: MovieImageEntity) {
        containerView.apply {
            Glide.with(context).load(data.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_image)
        }
    }

}
