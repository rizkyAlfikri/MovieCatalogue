package com.dicoding.picodiploma.moviecatalogue.data.source.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity

@Database(
    entities = [
        MoviePopularEntity::class
        , MovieDetailEntity::class
        , TvPopularEntity::class
        , TvDetailEntity::class
        , SearchEntity::class
        , PeopleEntity::class
        , PeopleDetailEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class MovieCatalogDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
    abstract fun tvShowDao(): TvShowDao

    companion object {
        private var INSTANCE: MovieCatalogDatabase? = null

        fun getInstance(context: Context): MovieCatalogDatabase {
            return INSTANCE
                ?: synchronized(this) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        MovieCatalogDatabase::class.java,
                        "movie_catalog.db"
                    ).build()

                    INSTANCE = instance
                    return instance
                }
        }
    }
}