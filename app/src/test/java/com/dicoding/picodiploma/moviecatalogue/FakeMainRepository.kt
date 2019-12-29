package com.dicoding.picodiploma.moviecatalogue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.picodiploma.moviecatalogue.data.source.MainDataSource
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.NetworkBounceResource
import com.dicoding.picodiploma.moviecatalogue.data.source.local.LocalRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
import com.dicoding.picodiploma.moviecatalogue.utils.*
import com.dicoding.picodiploma.moviecatalogue.vo.Resource

class FakeMainRepository(
    private val remote: RemoteRepository,
    private val local: LocalRepository,
    private val imageUrl: String
) : MainDataSource {

    companion object {
        @Volatile
        private var INSTANCE: FakeMainRepository? = null

        fun getInstance(remoteRepository: RemoteRepository, local: LocalRepository,imageUrl: String): FakeMainRepository? {
            if (INSTANCE == null) {
                INSTANCE =
                    FakeMainRepository(
                        remoteRepository,
                        local,
                        imageUrl
                    )
            }

            return INSTANCE
        }
    }

    override fun getPopularMovieData(): LiveData<Resource<List<MoviePopularEntity>>> {
        return object :
            NetworkBounceResource<List<MoviePopularEntity>, MovieResultWithGenre>() {
            override fun loadFromDB(): LiveData<List<MoviePopularEntity>> {
                return local.getAllMoviePopular()
            }

            override fun shouldFetch(data: List<MoviePopularEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<MovieResultWithGenre>> {
                return remote.getPopularMovieRepo()
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(data: MovieResultWithGenre): List<MoviePopularEntity>? {
                val listMoviePopular = mutableListOf<MoviePopularEntity>()
                data.movieResult.forEach {
                    val movieGenre = movieGenreBuilder(it.genreIds, data.movieGenreResult)

                    listMoviePopular.add(
                        MoviePopularEntity(
                            null,
                            it.id,
                            "$imageUrl${it.posterPath}",
                            it.title,
                            movieGenre,
                            it.releaseDate,
                            it.voteAverage
                        )
                    )
                }
                return listMoviePopular
            }

            override fun saveCallResult(data: MovieResultWithGenre) {

            }
        }.asLiveData()
    }

    override fun getDetailMovieData(idMovie: Int): LiveData<Resource<MovieDetailEntity>> {
        return object : NetworkBounceResource<MovieDetailEntity, MovieDetailResult>() {
            override fun loadFromDB(): LiveData<MovieDetailEntity> {
                return local.getMovieDetailById(idMovie)
            }

            override fun shouldFetch(data: MovieDetailEntity?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResult>> {
                return remote.getDetailMovieRepo(idMovie)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(data: MovieDetailResult): MovieDetailEntity? {
                Log.e(MainRepository::class.java.simpleName, "data = ${data.title}")
                val movieDetailGenre = movieDetailGenreBuilder(data.genres)

                return MovieDetailEntity(
                    movieDetailGenre,
                    "$imageUrl${data.backdropPath}",
                    data.homepage,
                    data.id,
                    data.overview,
                    "$imageUrl${data.posterPath}",
                    convertDate(data.releaseDate),
                    data.runtime,
                    data.status,
                    data.tagline,
                    data.title,
                    data.voteAverage
                )
            }

            override fun saveCallResult(data: MovieDetailResult) {

            }
        }.asLiveData()
    }

    override fun getPopularTvData(): LiveData<Resource<List<TvPopularEntity>>> {
        return object : NetworkBounceResource<List<TvPopularEntity>, TvResultWithGenre>() {
            override fun loadFromDB(): LiveData<List<TvPopularEntity>> {
                return local.getPopularTv()
            }

            override fun shouldFetch(data: List<TvPopularEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<TvResultWithGenre>>? {
                return remote.getPopularTvRepo()
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(data: TvResultWithGenre): List<TvPopularEntity>? {
                val listTv = mutableListOf<TvPopularEntity>()

                data.listTvShow.forEach {
                    val tvGenre = tvGenreBuilder(it.genreIds, data.listTvGenre)
                    listTv.add(
                        TvPopularEntity(
                            null,
                            it.id,
                            it.name,
                            "$imageUrl${it.posterPath}",
                            tvGenre,
                            it.voteAverage,
                            it.firstAirDate
                        )
                    )
                }

                return listTv
            }

            override fun saveCallResult(data: TvResultWithGenre) {

            }
        }.asLiveData()
    }

    override fun getDetailTvData(idTv: Int): LiveData<Resource<TvDetailEntity>> {
        return object : NetworkBounceResource<TvDetailEntity, TvDetailResult>(){
            override fun loadFromDB(): LiveData<TvDetailEntity> {
                return local.getTvDetailById(idTv)
            }

            override fun shouldFetch(data: TvDetailEntity?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<TvDetailResult>>? {
                return remote.getDetailTvRepo(idTv)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(data: TvDetailResult): TvDetailEntity? {
                val genre = genreTvDetailBuilder(data.genres)

                return TvDetailEntity(
                    genre,
                    "$imageUrl${data.backdropPath}",
                    data.homepage,
                    data.id,
                    data.overview,
                    "$imageUrl${data.posterPath}",
                    data.firstAirDate,
                    data.episodeRunTime[0],
                    data.status,
                    data.name,
                    data.voteAverage)
            }

            override fun saveCallResult(data: TvDetailResult) {

            }
        }.asLiveData()
    }

    override fun getMovieFavoriteById(id: Int): LiveData<MoviePopularEntity> {
        return local.getMovieFavoriteById(id)
    }

    override fun insertMovieFavorite(moviePopularEntity: MoviePopularEntity) {
        local.insertMovieFavorite(moviePopularEntity)
    }

    override fun deleteMovieFavorite(moviePopularEntity: MoviePopularEntity) {
        local.deleteMovieFavorite(moviePopularEntity)
    }

    override fun getTvDetailById(id: Int): LiveData<TvDetailEntity> {
        return local.getTvDetailById(id)
    }

    override fun getTvFavoriteById(id: Int): LiveData<TvPopularEntity> {
        return local.getTvFavoriteById(id)
    }

    override fun insertTvFavorite(tvPopularEntity: TvPopularEntity) {
        local.insertTvDetailFavorite(tvPopularEntity)
    }

    override fun deleteTvFavorite(tvPopularEntity: TvPopularEntity) {
        local.deleteTvFavorite(tvPopularEntity)
    }

    override fun getFavoriteMovieData(): LiveData<Resource<PagedList<MoviePopularEntity>>> {
        return object : NetworkBounceResource<PagedList<MoviePopularEntity>, MovieDetailResult>(){
            override fun loadFromDB(): LiveData<PagedList<MoviePopularEntity>> {
                return LivePagedListBuilder(local.getAllMovieFavorite(), 10).build()
            }

            override fun shouldFetch(data: PagedList<MoviePopularEntity>?): Boolean {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailResult>>? {
                return null
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(data: MovieDetailResult): PagedList<MoviePopularEntity>? {
                return null
            }

            override fun saveCallResult(data: MovieDetailResult) {

            }
        }.asLiveData()
    }

    override fun getFavoriteTvData(): LiveData<Resource<PagedList<TvPopularEntity>>> {
        return object : NetworkBounceResource<PagedList<TvPopularEntity>, TvDetailResult>() {
            override fun loadFromDB(): LiveData<PagedList<TvPopularEntity>> {
                return LivePagedListBuilder(local.getAllTvShowFavorite(), 10).build()
            }

            override fun shouldFetch(data: PagedList<TvPopularEntity>?): Boolean {
                return false
            }

            override fun createCall(): LiveData<ApiResponse<TvDetailResult>>? {
                return null
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(data: TvDetailResult): PagedList<TvPopularEntity>? {
                return null
            }

            override fun saveCallResult(data: TvDetailResult) {

            }
        }.asLiveData()
    }

}