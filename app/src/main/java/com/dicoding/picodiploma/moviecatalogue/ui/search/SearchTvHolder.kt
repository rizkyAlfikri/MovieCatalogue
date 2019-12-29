package com.dicoding.picodiploma.moviecatalogue.ui.search

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_search_tv_holder.view.*

class SearchTvHolder(private val tv: TvPopularEntity, private val listener: (Int) -> Unit) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_title.text = tv.title
            txt_genre.text = tv.genreIds
            txt_release.text = tv.release
            txt_rate.text = tv.voteAverage.toString()

            Glide.with(context).load(tv.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(tv.idTv) }
        }
    }

    override fun getLayout(): Int = R.layout.item_search_tv_holder
}

