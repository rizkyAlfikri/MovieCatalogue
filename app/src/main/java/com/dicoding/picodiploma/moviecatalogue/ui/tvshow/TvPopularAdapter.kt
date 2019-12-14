package com.dicoding.picodiploma.moviecatalogue.ui.tvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_holder.view.*

class TvPopularAdapter(
    private val listTv: List<TvPopularEntity>,
    private val listener: (Int) -> Unit
) : RecyclerView.Adapter<TvPopularViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvPopularViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_holder, parent, false)
        return TvPopularViewHolder(view)
    }

    override fun getItemCount(): Int = listTv.size

    override fun onBindViewHolder(holder: TvPopularViewHolder, position: Int) {
        holder.bindData(listTv[position], listener)
    }
}

class TvPopularViewHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(tvShowEntity: TvPopularEntity, listener: (Int) -> Unit) {
        containerView.apply {
            txt_title.text = tvShowEntity.title
            Glide.with(context).load(tvShowEntity.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(tvShowEntity.id) }

        }
    }
}
