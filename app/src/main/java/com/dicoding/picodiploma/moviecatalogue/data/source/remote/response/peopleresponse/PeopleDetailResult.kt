package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.peopleresponse


import com.google.gson.annotations.SerializedName

data class PeopleDetailResult(
    @SerializedName("biography")
    var biography: String,
    @SerializedName("birthday")
    var birthday: String,
    @SerializedName("deathday")
    var deathday: Any?,
    @SerializedName("gender")
    var gender: Int,
    @SerializedName("homepage")
    var homepage: Any?,
    @SerializedName("id")
    var id: Int,
    @SerializedName("name")
    var name: String,
    @SerializedName("place_of_birth")
    var placeOfBirth: String,
    @SerializedName("profile_path")
    var profilePath: String
)