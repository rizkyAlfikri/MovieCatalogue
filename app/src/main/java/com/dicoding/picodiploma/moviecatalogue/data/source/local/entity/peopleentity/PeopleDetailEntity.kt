package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_detail")
data class PeopleDetailEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int?,
    var biography: String,
    var birthday: String,
    var gender: String,
    var homepage: String,
    var idPeople: Int,
    var name: String,
    var placeOfBirth: String,
    var profilePath: String
)