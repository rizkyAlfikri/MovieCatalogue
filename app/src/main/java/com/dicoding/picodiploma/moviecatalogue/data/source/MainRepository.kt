package com.dicoding.picodiploma.moviecatalogue.data.source

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
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
import com.dicoding.picodiploma.moviecatalogue.utils.genreTvDetailBuilder
import com.dicoding.picodiploma.moviecatalogue.utils.movieDetailGenreBuilder
import com.dicoding.picodiploma.moviecatalogue.utils.movieGenreBuilder
import com.dicoding.picodiploma.moviecatalogue.utils.tvGenreBuilder
import com.dicoding.picodiploma.moviecatalogue.vo.Resource

class MainRepository private constructor(
    private val remote: RemoteRepository,
    private val local: LocalRepository,
    private val imageUrl: String
) : MainDataSource {

    companion object {
        @Volatile
        private var INSTANCE: MainRepository? = null

        fun getInstance(
            remoteRepository: RemoteRepository,
            local: LocalRepository,
            imageUrl: String
        ): MainRepository? {
            if (INSTANCE == null) {
                INSTANCE =
                    MainRepository(
                        remoteRepository,
                        local,
                        imageUrl
                    )
            }

            return INSTANCE
        }
    }

    override fun getPopularMovieData(): LiveData<Resource<PagedList<MoviePopularEntity>>> {
        return object :
            NetworkBounceResource<PagedList<MoviePopularEntity>, MovieResultWithGenre>() {
            override fun loadFromDB(): LiveData<PagedList<MoviePopularEntity>> {
                return LivePagedListBuilder(local.getAllMoviePopular(), 10).build()
            }

            override fun shouldFetch(data: PagedList<MoviePopularEntity>?): Boolean {
                return (data == null) || (data.isEmpty())
            }

            override fun createCall(): LiveData<ApiResponse<MovieResultWithGenre>> {
                return remote.getPopularMovieRepo()
            }

            override fun needSaveToDB(): Boolean {
                return true
            }

            override fun fetchDataFromCall(data: MovieResultWithGenre): PagedList<MoviePopularEntity>? {
                return null
            }

            override fun saveCallResult(data: MovieResultWithGenre) {
                val listMoviePopular = mutableListOf<MoviePopularEntity>()
                Log.e(MainRepository::class.java.simpleName, "data = ${data.movieResult.size}")
                data.movieResult.forEach {
                    val movieGenre = movieGenreBuilder(it.genreIds, data.movieGenreResult)

                    listMoviePopular.add(
                        MoviePopularEntity(
                            it.id,
                            "$imageUrl${it.posterPath}",
                            it.title,
                            movieGenre,
                            it.releaseDate,
                            it.voteAverage
                        )
                    )
                }

                local.insertListMoviePopular(listMoviePopular)
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
                    data.releaseDate,
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

    override suspend fun getDetailTvData(idTv: Int): TvDetailEntity {
        val tvDetailResult = remote.getDetailTvRepo(idTv)
        val genre = genreTvDetailBuilder(tvDetailResult.genres)

        return TvDetailEntity(
            genre,
            "$imageUrl${tvDetailResult.backdropPath}",
            tvDetailResult.homepage,
            tvDetailResult.id,
            tvDetailResult.overview,
            "$imageUrl${tvDetailResult.posterPath}",
            tvDetailResult.firstAirDate,
            tvDetailResult.episodeRunTime[0],
            tvDetailResult.status,
            tvDetailResult.name,
            tvDetailResult.voteAverage
        )

    }

    override fun getMovieDetailById(id: Int): LiveData<MovieDetailEntity> {
        return local.getMovieDetailById(id)
    }

    override fun insertMovieDetailFavorite(movieDetailEntity: MovieDetailEntity) {
        local.insertMovieDetailFavorite(movieDetailEntity)
    }

    override fun deleteMovieDetailFavorite(movieDetailEntity: MovieDetailEntity) {
        local.deleteMovieDetailFavorite(movieDetailEntity)
    }
}
