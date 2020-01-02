package com.dicoding.picodiploma.moviecatalogue

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dicoding.picodiploma.moviecatalogue.data.source.MainDataSource
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.NetworkBounceResource
import com.dicoding.picodiploma.moviecatalogue.data.source.local.LocalRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.MovieDetailWithInfoEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.movieimageentity.MovieImageEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviereviewentity.MovieReviewEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviesimilarentity.MovieSimilarEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchWithMovieTvPeopleEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.TvDetailWithInfoEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvdetailentity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvimageentity.TvImageEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvreviewentity.TvReviewEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvsimilarentity.TvSimilarEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieDetailWithInfoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.peopleresponse.PeopleDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchWithGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvDetailWithInfoResult
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

    override fun getPopularMovieData(sortBy: String): LiveData<Resource<List<MoviePopularEntity>>> {
        return object :
            NetworkBounceResource<List<MoviePopularEntity>, MovieResultWithGenre>() {
            override fun loadFromDB(): LiveData<List<MoviePopularEntity>> {
                return local.getAllMoviePopular()
            }

            override fun shouldFetch(data: List<MoviePopularEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<MovieResultWithGenre>> {
                return remote.getPopularMovieRepo(sortBy)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: MovieResultWithGenre,
                dbData: List<MoviePopularEntity>?
            ): List<MoviePopularEntity>? {

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
                            it.voteAverage,
                            false
                        )
                    )
                }

                listMoviePopular.forEach { oldData ->
                    dbData?.forEach { newData ->
                        if (oldData.idMovie == newData.idMovie) {
                            oldData.isFavorite = true
                        }
                    }
                }


                return listMoviePopular
            }

            override fun saveCallResult(data: MovieResultWithGenre) {

            }

        }.asLiveData()
    }

    override fun getDetailMovieData(idMovie: Int): LiveData<Resource<MovieDetailWithInfoEntity>> {
        return object :
            NetworkBounceResource<MovieDetailWithInfoEntity, MovieDetailWithInfoResult>() {
            override fun loadFromDB(): LiveData<MovieDetailWithInfoEntity> {
                return local.getMovieDetailById(idMovie)
            }

            override fun shouldFetch(data: MovieDetailWithInfoEntity?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<MovieDetailWithInfoResult>> {
                return remote.getDetailMovieRepo(idMovie)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: MovieDetailWithInfoResult,
                dbData: MovieDetailWithInfoEntity?
            ): MovieDetailWithInfoEntity? {

                val movieDetailGenre = movieDetailGenreBuilder(data.movieDetailResult.genres)
                val listMovieReview = mutableListOf<MovieReviewEntity>()
                val listMovieImage = mutableListOf<MovieImageEntity>()
                val listMovieSimilar = mutableListOf<MovieSimilarEntity>()

                data.listMovieReviewResult.results.forEach {
                    listMovieReview.add(
                        MovieReviewEntity(
                            null,
                            data.listMovieReviewResult.id,
                            it.author,
                            it.content,
                            it.id,
                            it.url
                        )
                    )
                }

                data.listMovieImageResult.posters.forEach {
                    listMovieImage.add(
                        MovieImageEntity(
                            null,
                            data.listMovieImageResult.id,
                            "$imageUrl${it.filePath}"
                        )
                    )
                }

                data.listMovieSimilarResult.forEach {
                    listMovieSimilar.add(
                        MovieSimilarEntity(
                            null,
                            it.id,
                            "$imageUrl${it.posterPath}",
                            it.title
                        )
                    )
                }

                return MovieDetailWithInfoEntity(
                    MovieDetailEntity(
                        movieDetailGenre,
                        "$imageUrl${data.movieDetailResult.backdropPath}",
                        data.movieDetailResult.homepage,
                        data.movieDetailResult.id,
                        data.movieDetailResult.overview,
                        "$imageUrl${data.movieDetailResult.posterPath}",
                        convertDate(data.movieDetailResult.releaseDate),
                        data.movieDetailResult.runtime,
                        data.movieDetailResult.status,
                        data.movieDetailResult.tagline,
                        data.movieDetailResult.title,
                        data.movieDetailResult.voteAverage,
                        data.movieDetailResult.keyVideo
                    ),
                    listMovieReview,
                    listMovieImage,
                    listMovieSimilar
                )
            }

            override fun saveCallResult(data: MovieDetailWithInfoResult) {

            }
        }.asLiveData()
    }

    override fun getFavoriteMovieData(): LiveData<Resource<PagedList<MoviePopularEntity>>> {
        return object : NetworkBounceResource<PagedList<MoviePopularEntity>, MovieDetailResult>() {
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

            override fun fetchDataFromCall(
                data: MovieDetailResult,
                dbData: PagedList<MoviePopularEntity>?
            ): PagedList<MoviePopularEntity>? {
                return null
            }

            override fun saveCallResult(data: MovieDetailResult) {

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

    override fun deleteMovieFavoriteById(movieId: Int) {
        local.deleteMovieFavoriteById(movieId)
    }

    override fun getPopularTvData(sortBy: String): LiveData<Resource<List<TvPopularEntity>>> {
        return object : NetworkBounceResource<List<TvPopularEntity>, TvResultWithGenre>() {
            override fun loadFromDB(): LiveData<List<TvPopularEntity>> {
                return local.getPopularTv()
            }

            override fun shouldFetch(data: List<TvPopularEntity>?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<TvResultWithGenre>>? {
                return remote.getPopularTvRepo(sortBy)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: TvResultWithGenre,
                dbData: List<TvPopularEntity>?
            ): List<TvPopularEntity>? {

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
                            it.firstAirDate,
                            false
                        )
                    )
                }

                listTv.forEach { oldData ->
                    dbData?.forEach { newData ->
                        if (oldData.idTv == newData.idTv) {
                            oldData.isFavorite = true
                        }
                    }
                }

                return listTv

            }

            override fun saveCallResult(data: TvResultWithGenre) {

            }
        }.asLiveData()
    }

    override fun getDetailTvData(idTv: Int): LiveData<Resource<TvDetailWithInfoEntity>> {
        return object : NetworkBounceResource<TvDetailWithInfoEntity, TvDetailWithInfoResult>() {
            override fun loadFromDB(): LiveData<TvDetailWithInfoEntity> {
                return local.getTvDetailById(idTv)
            }

            override fun shouldFetch(data: TvDetailWithInfoEntity?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<TvDetailWithInfoResult>>? {
                return remote.getDetailTvRepo(idTv)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: TvDetailWithInfoResult,
                dbData: TvDetailWithInfoEntity?
            ): TvDetailWithInfoEntity? {

                val listTvReview = mutableListOf<TvReviewEntity>()
                val listTvImage = mutableListOf<TvImageEntity>()
                val listTvSimilar = mutableListOf<TvSimilarEntity>()

                data.listTvReview.results.forEach {
                    listTvReview.add(
                        TvReviewEntity(
                            null,
                            data.listTvReview.id,
                            it.author,
                            it.content,
                            it.id,
                            it.url
                        )
                    )
                }

                data.listTvImage.backdrops.forEach {
                    listTvImage.add(
                        TvImageEntity(
                            null,
                            data.listTvImage.id,
                            "$imageUrl${it.filePath}"
                        )
                    )
                }

                data.listTvSimilar.forEach {
                    listTvSimilar.add(
                        TvSimilarEntity(
                            null,
                            it.id,
                            it.name,
                            "$imageUrl${it.posterPath}"
                        )
                    )
                }


                val genre = genreTvDetailBuilder(data.tvShowDetail.genres)
                val tvDetailEntity = TvDetailEntity(
                    genre,
                    "$imageUrl${data.tvShowDetail.backdropPath}",
                    data.tvShowDetail.homepage,
                    data.tvShowDetail.id,
                    data.tvShowDetail.overview,
                    "$imageUrl${data.tvShowDetail.posterPath}",
                    data.tvShowDetail.firstAirDate,
                    data.tvShowDetail.episodeRunTime[0],
                    data.tvShowDetail.status,
                    data.tvShowDetail.name,
                    data.tvShowDetail.voteAverage,
                    data.tvShowDetail.keyVideo
                )




                return TvDetailWithInfoEntity(
                    tvDetailEntity,
                    listTvImage,
                    listTvReview,
                    listTvSimilar
                )
            }

            override fun saveCallResult(data: TvDetailWithInfoResult) {

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

            override fun fetchDataFromCall(
                data: TvDetailResult,
                dbData: PagedList<TvPopularEntity>?
            ): PagedList<TvPopularEntity>? {
                return null
            }

            override fun saveCallResult(data: TvDetailResult) {

            }
        }.asLiveData()
    }

    override fun getTvFavoriteById(id: Int): LiveData<TvPopularEntity> {
        return local.getTvFavoriteById(id)
    }

    override fun insertTvFavorite(tvPopularEntity: TvPopularEntity) {
        local.insertTvFavorite(tvPopularEntity)
    }

    override fun deleteTvFavorite(tvPopularEntity: TvPopularEntity) {
        local.deleteTvFavorite(tvPopularEntity)
    }

    override fun deleteTvFavoriteById(tvId: Int) {
        local.deleteTvFavoriteById(tvId)
    }


    override fun getSearchData(query: String): LiveData<Resource<SearchWithMovieTvPeopleEntity>> {
        return object :
            NetworkBounceResource<SearchWithMovieTvPeopleEntity, SearchWithGenreResult>() {
            override fun loadFromDB(): LiveData<SearchWithMovieTvPeopleEntity> {
                return local.getSearchEntity()
            }

            override fun shouldFetch(data: SearchWithMovieTvPeopleEntity?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<SearchWithGenreResult>>? {
                return remote.getSearchRepo(query)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: SearchWithGenreResult,
                dbData: SearchWithMovieTvPeopleEntity?
            ): SearchWithMovieTvPeopleEntity? {
                val movieResult = mutableListOf<MoviePopularEntity>()
                val tvResult = mutableListOf<TvPopularEntity>()
                val peopleResult = mutableListOf<PeopleEntity>()
                val searchResult = mutableListOf<SearchEntity>()

                Log.e(MainRepository::class.java.simpleName, "${data.listSearchResult.size}")

                data.listSearchResult.forEach {

                    searchResult.add(SearchEntity(null, it.id, it.mediaType))

                    when {
                        it.mediaType == "movie" -> {
                            val movieGenre = movieGenreBuilder(it.genreIds, data.listMovieGenre)
                            movieResult.add(
                                MoviePopularEntity(
                                    null,
                                    it.id,
                                    "$imageUrl${it.posterPath}",
                                    it.title,
                                    movieGenre,
                                    convertDate(it.releaseDate),
                                    it.voteAverage,
                                    false
                                )
                            )
                        }
                        it.mediaType == "tv" -> {
                            val tvGenre = tvGenreBuilder(it.genreIds, data.listTvGenre)
                            tvResult.add(
                                TvPopularEntity(
                                    null,
                                    it.id,
                                    it.name,
                                    "$imageUrl${it.posterPath}",
                                    tvGenre,
                                    it.voteAverage,
                                    convertDate(it.firstAirDate),
                                    false
                                )
                            )
                        }
                        else -> peopleResult.add(
                            PeopleEntity(
                                null,
                                it.id,
                                it.name,
                                "$imageUrl${it.profilePath}"
                            )
                        )
                    }
                }

                Log.e(
                    MainRepository::class.java.simpleName,
                    "Movie DB = ${dbData?.listMovie?.size}"
                )

                movieResult.forEach { newData ->
                    dbData?.listMovie?.forEach { oldData ->
                        Log.e(MainRepository::class.java.simpleName, "Movie DB = $oldData")
                        if (newData.idMovie == oldData.idMovie) {
                            newData.isFavorite = true
                        }
                    }
                }

                dbData?.listTv?.forEach { oldData ->
                    tvResult.forEach { newData ->
                        if (newData.idTv == oldData.idTv) {
                            newData.isFavorite = true
                        }

                    }
                }

                return SearchWithMovieTvPeopleEntity(
                    null,
                    movieResult,
                    tvResult,
                    peopleResult
                )
            }

            override fun saveCallResult(data: SearchWithGenreResult) {

            }
        }.asLiveData()
    }

    override fun getPeopleDetailData(idPeople: Int): LiveData<Resource<PeopleDetailEntity>> {
        return object : NetworkBounceResource<PeopleDetailEntity, PeopleDetailResult>() {
            override fun loadFromDB(): LiveData<PeopleDetailEntity> {
                return local.getPeopleDetailById(idPeople)
            }

            override fun shouldFetch(data: PeopleDetailEntity?): Boolean {
                return true
            }

            override fun createCall(): LiveData<ApiResponse<PeopleDetailResult>>? {
                return remote.getDetailPersonRepo(idPeople)
            }

            override fun needSaveToDB(): Boolean {
                return false
            }

            override fun fetchDataFromCall(
                data: PeopleDetailResult,
                dbData: PeopleDetailEntity?
            ): PeopleDetailEntity? {

                return PeopleDetailEntity(
                    null,
                    data.biography,
                    convertBirthDay(data.birthday),
                    convertPeopleGender(data.gender),
                    "${data.homepage}",
                    data.id,
                    data.name,
                    data.placeOfBirth,
                    "$imageUrl${data.profilePath}"
                )
            }

            override fun saveCallResult(data: PeopleDetailResult) {

            }
        }.asLiveData()
    }
}