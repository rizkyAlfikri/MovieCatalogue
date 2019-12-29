package com.dicoding.picodiploma.moviecatalogue.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
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

    // ============================ Movie Query ============================

    fun getAllMoviePopular() = movieDao.getAllMoviePopular()


    fun insertListMoviePopular(listMoviePopular: List<MoviePopularEntity>) {
        movieDao.insertListMoviePopular(listMoviePopular)
    }

    fun getAllMovieFavorite(): DataSource.Factory<Int, MoviePopularEntity> {
        return movieDao.getAllMovieFavorite()
    }

    fun getMovieFavoriteById(id: Int): LiveData<MoviePopularEntity> =
        movieDao.getMovieFavoriteById(id)

    fun insertMovieFavorite(moviePopularEntity: MoviePopularEntity) {
        movieDao.insertMovieFavorite(moviePopularEntity)
    }

    fun deleteMovieFavorite(moviePopularEntity: MoviePopularEntity) {
        movieDao.deleteMovieFavorite(moviePopularEntity)
    }

    fun getMovieDetailById(id: Int): LiveData<MovieDetailEntity> {
        return movieDao.getMovieDetailById(id)
    }

    fun deleteMovieFavoriteById(movieId: Int) {
        movieDao.deleteMovieFavoriteById(movieId)
    }

    // ============================ Tv Show Query ===========================

    fun getPopularTv() = tvShowDao.getPopularTv()

    fun insertAllTvPopular(listTv: List<TvPopularEntity>) = tvShowDao.insertAllTvPopular(listTv)

    fun getAllTvShowFavorite() = tvShowDao.getAllTvShowFavorite()

    fun getTvDetailById(id: Int) = tvShowDao.getDetailTvById(id)

    fun insertTvDetailFavorite(tvPopularEntity: TvPopularEntity) =
        tvShowDao.insertTvFavorite(tvPopularEntity)

    fun deleteTvFavorite(tvPopularEntity: TvPopularEntity) =
        tvShowDao.deleteTvFavorite(tvPopularEntity)

    fun getTvFavoriteById(id: Int) = tvShowDao.getTvFavoriteById(id)

    fun deleteTvFavoriteById(tvId: Int) = tvShowDao.deleteTvFavoriteById(tvId)


    // ============================ Other ===========================

    fun getSearchEntity() = movieDao.getSearchEntity()

    fun getPeopleDetailById(idPeople: Int) = movieDao.getPeopleDetailById(idPeople)

}