package com.dicoding.picodiploma.moviecatalogue.ui.home

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_movie_holder.view.*

class HomeTvHolder(private val tvData: TvPopularEntity, private val listener: (Int) -> Unit) :
    Item() {

    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_title.text = tvData.title
            Glide.with(context).load(tvData.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(tvData.idTv) }
        }

    }

    override fun getLayout(): Int = R.layout.item_movie_holder

}