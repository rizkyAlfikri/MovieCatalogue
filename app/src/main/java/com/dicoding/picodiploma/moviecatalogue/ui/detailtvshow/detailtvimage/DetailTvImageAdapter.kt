package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow.detailtvimage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvimageentity.TvImageEntity
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_image_holder.view.*

class DetailTvImageAdapter : RecyclerView.Adapter<DetailTvImageHolder>() {

    private val listTvImage = mutableListOf<TvImageEntity>()

    fun setTvImageData(list: List<TvImageEntity>) {
        listTvImage.clear()
        listTvImage.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTvImageHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image_holder, parent, false)

        return DetailTvImageHolder(view)
    }

    override fun getItemCount(): Int = listTvImage.size

    override fun onBindViewHolder(holder: DetailTvImageHolder, position: Int) {
        holder.bindData(listTvImage[position])
    }
}

class DetailTvImageHolder(override val containerView: View) :
    RecyclerView.ViewHolder(containerView), LayoutContainer {

    fun bindData(tvImageEntity: TvImageEntity) {
        containerView.apply {
            Glide.with(context).load(tvImageEntity.imagePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_image)
        }
    }

}
