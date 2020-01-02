package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvinfo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvsimilarentity.TvSimilarEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_movie_similar.view.*

class DetailTvSimilarAdapter(private val listener: (Int) -> Unit) :
    RecyclerView.Adapter<DetailTvSimilarHolder>() {

    private val listTvSimilar = mutableListOf<TvSimilarEntity>()

    fun setTvSimilarData(list: List<TvSimilarEntity>) {
        listTvSimilar.clear()
        listTvSimilar.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTvSimilarHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_movie_similar, parent, false)

        return DetailTvSimilarHolder(view)
    }

    override fun getItemCount(): Int = listTvSimilar.size

    override fun onBindViewHolder(holder: DetailTvSimilarHolder, position: Int) {
        holder.bindData(listTvSimilar[position], listener)
    }
}

class DetailTvSimilarHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(tvSimilarEntity: TvSimilarEntity, listener: (Int) -> Unit) {
        containerView.apply {
            txt_title.text = tvSimilarEntity.title

            Glide.with(context).load(tvSimilarEntity.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_poster)

            setOnClickListener { listener(tvSimilarEntity.idTv) }

        }
    }


}
