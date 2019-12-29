package com.dicoding.picodiploma.moviecatalogue.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchWithMovieTvPeopleEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.vo.Resource

interface MainDataSource {


    // ============================== Movie ==============================

    fun getPopularMovieData(sortBy: String): LiveData<Resource<List<MoviePopularEntity>>>

    fun getDetailMovieData(idMovie: Int): LiveData<Resource<MovieDetailEntity>>

    fun getFavoriteMovieData(): LiveData<Resource<PagedList<MoviePopularEntity>>>

    fun getMovieFavoriteById(id: Int): LiveData<MoviePopularEntity>

    fun insertMovieFavorite(moviePopularEntity: MoviePopularEntity)

    fun deleteMovieFavorite(moviePopularEntity: MoviePopularEntity)

    fun deleteMovieFavoriteById(movieId: Int)

    // ============================== Tv Show =============================

    fun getPopularTvData(sortBy: String): LiveData<Resource<List<TvPopularEntity>>>

    fun getDetailTvData(idTv: Int): LiveData<Resource<TvDetailEntity>>

    fun getFavoriteTvData(): LiveData<Resource<PagedList<TvPopularEntity>>>

    fun getTvDetailById(id: Int): LiveData<TvDetailEntity>

    fun getTvFavoriteById(id: Int): LiveData<TvPopularEntity>

    fun insertTvFavorite(tvPopularEntity: TvPopularEntity)

    fun deleteTvFavorite(tvPopularEntity: TvPopularEntity)

    fun deleteTvFavoriteById(tvId: Int)


    // ============================== Other =============================

    fun getSearchData(query: String): LiveData<Resource<SearchWithMovieTvPeopleEntity>>

    fun getPeopleDetailData(idPeople: Int): LiveData<Resource<PeopleDetailEntity>>
}