package com.dicoding.picodiploma.moviecatalogue.ui.detailpeople

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dicoding.picodiploma.moviecatalogue.R
import com.dicoding.picodiploma.moviecatalogue.utils.invisible
import com.dicoding.picodiploma.moviecatalogue.utils.visible
import com.dicoding.picodiploma.moviecatalogue.viewmodels.ViewModelFactory
import com.dicoding.picodiploma.moviecatalogue.vo.Status.*
import kotlinx.android.synthetic.main.activity_detail_people.*
import org.jetbrains.anko.toast

class DetailPeopleActivity : AppCompatActivity() {

    private lateinit var peopleViewModel: PeopleDetailViewModel

    companion object {
        private fun obtainViewModel(activity: AppCompatActivity): PeopleDetailViewModel {
            val factory = ViewModelFactory.getInstance(activity.application)

            return ViewModelProviders.of(activity, factory).get(PeopleDetailViewModel::class.java)
        }

        const val EXTRA_PEOPLE = "extra_people"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_people)

        val peopleId = intent.getIntExtra(EXTRA_PEOPLE, 0)

        peopleViewModel = obtainViewModel(this)
        peopleViewModel.setIdPeople(peopleId)

        showPeopleDetail()
    }

    private fun showPeopleDetail() {

        peopleViewModel.getPeopleDetail.observe(this, Observer { resource ->

            when (resource.status) {

                LOADING -> progress_bar.visible()

                SUCCESS -> {
                    progress_bar.invisible()

                    resource.body?.let {
                        txt_name.text = it.name
                        txt_gender.text = it.gender
                        txt_birth.text = it.birthday
                        txt_birth_place.text = it.placeOfBirth
                        txt_bio.text = it.biography
                        txt_web.text = it.homepage

                        Glide.with(this).load(it.profilePath)
                            .apply(RequestOptions().placeholder(R.drawable.image_loading).error(R.drawable.image_error))
                            .into(img_profile)
                    }
                }

                ERROR -> {
                    progress_bar.invisible()
                    toast("Failed request data to network, please try again")
                }

            }

        })
    }
}
