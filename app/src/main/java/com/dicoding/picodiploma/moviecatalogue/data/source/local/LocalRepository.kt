package com.dicoding.picodiploma.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.room.MovieDao
import com.dicoding.picodiploma.moviecatalogue.data.source.local.room.TvShowDao

class LocalRepository private constructor(
    private val movieDao: MovieDao,
    private val tvShowDao: TvShowDao
) {

    companion object {
        private var INSTANCE: LocalRepository? = null
        private lateinit var instance: LocalRepository

        fun getInstance(movieDao: MovieDao, tvShowDao: TvShowDao): LocalRepository {
            if (INSTANCE == null) {
                instance =
                    LocalRepository(
                        movieDao, tvShowDao
                    )
                INSTANCE = instance
            }

            return INSTANCE ?: instance
        }
    }

    fun getAllMoviePopular(): DataSource.Factory<Int, MoviePopularEntity> {
        return movieDao.getAllMoviePopular()
    }

    fun insertListMoviePopular(listMoviePopular: List<MoviePopularEntity>) {
        movieDao.insertListMoviePopular(listMoviePopular)
    }

    fun getMovieDetailById(id: Int): LiveData<MovieDetailEntity> {
        return movieDao.getMovieDetailById(id)

    }

    fun insertMovieDetailFavorite(movieDetailEntity: MovieDetailEntity) {
        movieDao.insertMovieDetailFavorite(movieDetailEntity)
    }

    fun deleteMovieDetailFavorite(movieDetailEntity: MovieDetailEntity) {
        movieDao.deleteMovieDetailFavorite(movieDetailEntity)
    }

    fun getPopularTv() = tvShowDao.getPopularTv()

    fun insertAllTvPopular(listTv: List<TvPopularEntity>) = tvShowDao.insertAllTvPopular(listTv)

    fun getTvDetailById(id: Int) = tvShowDao.getDetailTvById(id)

    fun insertTvDetailFavorite(tvDetailEntity: TvDetailEntity) =
        tvShowDao.insertTvDetailFavorite(tvDetailEntity)

    fun deleteTvDetailFavorite(tvDetailEntity: TvDetailEntity) =
        tvShowDao.deleteTvDetailFavorite(tvDetailEntity)

}