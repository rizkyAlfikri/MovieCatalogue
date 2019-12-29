package com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_entity")
data class PeopleEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int?,
    var idPeople: Int,
    var name: String?,
    var profilePath: String?
)
