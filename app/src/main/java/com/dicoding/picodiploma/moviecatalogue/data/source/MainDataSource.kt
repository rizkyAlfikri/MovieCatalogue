package com.dicoding.picodiploma.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.vo.Resource

interface MainDataSource {

    fun getPopularMovieData(): LiveData<Resource<PagedList<MoviePopularEntity>>>

    fun getDetailMovieData(idMovie: Int): LiveData<Resource<MovieDetailEntity>>

    fun getPopularTvData(): LiveData<Resource<List<TvPopularEntity>>>

    suspend fun getDetailTvData(idTv: Int): TvDetailEntity

    fun getMovieDetailById(id: Int): LiveData<MovieDetailEntity>

    fun insertMovieDetailFavorite(movieDetailEntity: MovieDetailEntity)

    fun deleteMovieDetailFavorite(movieDetailEntity: MovieDetailEntity)

}