package com.dicoding.picodiploma.moviecatalogue.ui.search

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleEntity
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_search_people_holder.view.*

class SearchPeopleHolder(private val people: PeopleEntity, private val listener: (Int) -> Unit) : Item() {
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.itemView.apply {
            txt_name.text = people.name

            Glide.with(context).load(people.profilePath)
                .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                .into(img_people)

            setOnClickListener { listener(people.idPeople) }
        }
    }

    override fun getLayout(): Int = R.layout.item_search_people_holder
}