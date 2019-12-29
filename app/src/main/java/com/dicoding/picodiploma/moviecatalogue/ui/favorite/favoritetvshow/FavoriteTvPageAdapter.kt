package com.dicoding.picodiploma.moviecatalogue.ui.favorite.favoritetvshow

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.MyAdapterClickListener
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_favorite_tv_holder.view.*

class FavoriteTvPageAdapter(
    private val adapterClickListener: MyAdapterClickListener<TvPopularEntity>,
    private val listener: (Int) -> Unit
) :
    PagedListAdapter<TvPopularEntity, TvFavoritePagedHolder>(
        DIFF_UTIL_CALLBACK
    ) {

    companion object {
        val DIFF_UTIL_CALLBACK: DiffUtil.ItemCallback<TvPopularEntity> =
            object : DiffUtil.ItemCallback<TvPopularEntity>() {
                override fun areItemsTheSame(
                    oldItem: TvPopularEntity,
                    newItem: TvPopularEntity
                ): Boolean {
                    return oldItem.idTv == newItem.idTv
                }

                override fun areContentsTheSame(
                    oldItem: TvPopularEntity,
                    newItem: TvPopularEntity
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvFavoritePagedHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite_tv_holder, parent, false)

        return TvFavoritePagedHolder(view)
    }

    override fun onBindViewHolder(holder: TvFavoritePagedHolder, position: Int) {
        val tvPopularEntity = getItem(position)
        tvPopularEntity?.let { holder.bindData(it, adapterClickListener, listener) }
    }
}

class TvFavoritePagedHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(
        data: TvPopularEntity,
        adapterClickListener: MyAdapterClickListener<TvPopularEntity>,
        listener: (Int) -> Unit
    ) {
        containerView.apply {
            txt_title.text = data.title
            txt_genre.text = data.genreIds
            txt_release.text = data.release
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

                data.isFavorite = !data.isFavorite
                adapterClickListener.onItemClicked(data, data.isFavorite)

            }

            setOnClickListener { listener(data.idTv) }
        }
    }
}
