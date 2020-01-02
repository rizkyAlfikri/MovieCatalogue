package com.dicoding.picodiploma.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.picodiploma.moviecatalogue.FakeMainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.LocalRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.MovieDetailWithInfoEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchWithMovieTvPeopleEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.TvDetailWithInfoEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieDetailWithInfoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.peopleresponse.PeopleDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchWithGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvDetailWithInfoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.utils.LiveDataUtilsTest
import com.dicoding.picodiploma.moviecatalogue.utils.PagedListUtils
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remote: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var dataSourceMovie: DataSource.Factory<Int, MoviePopularEntity>

    @Mock
    private lateinit var dataSourceTv: DataSource.Factory<Int, TvPopularEntity>

    private lateinit var mainRepository: FakeMainRepository

    private val imageUrl = Config.IMAGE_URL_BASE_PATH

    private val idTv = 60625

    private val idMovie = 475557

    private val idPeople = 119143

    private val querySearch = "Kana Hanazawa"

    private val sortBy = Config.POPULAR

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainRepository = FakeMainRepository(remote, localRepository, imageUrl)
    }

    @Test
    fun return_movie_live_data_when_get_popular_movie_data_success() {

        val listDataMovieEntity = FakeDataDummy.getMovieDummyEntity()
        val moviePopularLiveEntity = MutableLiveData<List<MoviePopularEntity>>()
        moviePopularLiveEntity.value = listDataMovieEntity

        val movieResponseDummy = FakeDataDummy.getMovieDummyResult()
        val movieGenreDummy = FakeDataDummy.getMovieGenreDummyResult()
        val movieResponseLiveResult = MutableLiveData<ApiResponse<MovieResultWithGenre>>()
        movieResponseLiveResult.value =
            ApiResponse.success(MovieResultWithGenre(movieResponseDummy.results, movieGenreDummy))


        `when`(remote.getPopularMovieRepo(sortBy)).thenReturn(movieResponseLiveResult)
        `when`(localRepository.getAllMoviePopular()).thenReturn(moviePopularLiveEntity)

        val result = LiveDataUtilsTest.getValue(mainRepository.getPopularMovieData(sortBy))


        verify(remote).getPopularMovieRepo(sortBy)
        verify(localRepository, times(3)).getAllMoviePopular()

        assertNotNull(result.body)
        assertEquals(listDataMovieEntity, result.body)
    }

    @Test
    fun return_tv_show_live_data_when_get_popular_tv_data_success() {
        val listTvResult = FakeDataDummy.getTvDummyResult()
        val listGenreTv = FakeDataDummy.getTvGenreDummyResult()
        val liveDataTvResult = MutableLiveData<ApiResponse<TvResultWithGenre>>()
        liveDataTvResult.value =
            ApiResponse.success(TvResultWithGenre(listTvResult.results, listGenreTv))

        val listTvEntity = FakeDataDummy.getTvDummyEntity()
        val liveDataTvEntity = MutableLiveData<List<TvPopularEntity>>()
        liveDataTvEntity.value = listTvEntity

        `when`(remote.getPopularTvRepo(sortBy)).thenReturn(liveDataTvResult)
        `when`(localRepository.getPopularTv()).thenReturn(liveDataTvEntity)

        val result = LiveDataUtilsTest.getValue(mainRepository.getPopularTvData(sortBy))

        verify(remote).getPopularTvRepo(sortBy)
        verify(localRepository, times(3)).getPopularTv()

        assertNotNull(result.body)
        assertEquals(listTvEntity, result.body)
    }

    @Test
    fun return_movie_detail_with_info_entity_live_data_when_get_detail_movie_data_success() {
        val movieDetailWithInfoResult = MovieDetailWithInfoResult(
            FakeDataDummy.getMovieDetailResultDummy(),
            FakeDataDummy.getMovieReviewResultDummy(),
            FakeDataDummy.getMovieImageResultDummy(),
            FakeDataDummy.getMovieDummyResult().results
        )

        val movieDetailResultLive = MutableLiveData<ApiResponse<MovieDetailWithInfoResult>>()
        movieDetailResultLive.value = ApiResponse.success(
            movieDetailWithInfoResult
        )

        val movieDetailWithInfoEntity = MovieDetailWithInfoEntity(
            FakeDataDummy.getMovieDetailEntityDummy(),
            listOf(FakeDataDummy.getMovieReviewEntity()),
            listOf(FakeDataDummy.getMovieImageEntity()),
            listOf(FakeDataDummy.getMovieSimilarEntity())
        )

        val movieDetailEntityLive = MutableLiveData<MovieDetailWithInfoEntity>()
        movieDetailEntityLive.value = movieDetailWithInfoEntity

        `when`(remote.getDetailMovieRepo(idMovie)).thenReturn(movieDetailResultLive)
        `when`(localRepository.getMovieDetailById(idMovie)).thenReturn(movieDetailEntityLive)

        val result = LiveDataUtilsTest.getValue(mainRepository.getDetailMovieData(idMovie))

        verify(remote).getDetailMovieRepo(idMovie)
        verify(localRepository, times(3)).getMovieDetailById(idMovie)

        assertNotNull(result.body)
        assertNotNull(result.body?.movieDetailEntity)
        assertNotNull(result.body?.listMovieReview)
        assertNotNull(result.body?.listMovieImage)
        assertNotNull(result.body?.listMovieSimilar)

        assertEquals(movieDetailWithInfoEntity, result.body)
    }

    @Test
    fun return_tv_detail_with_info_entity_live_data_when_get_tv_detail_by_id_success() {
        val tvDetailWithInfoResult = TvDetailWithInfoResult(
            FakeDataDummy.getTvDetailResultDummy(),
            FakeDataDummy.getTvReviewResult(),
            FakeDataDummy.getTvImageResult(),
            FakeDataDummy.getTvDummyResult().results
        )

        val tvDetailWithInfoResultLive = MutableLiveData<ApiResponse<TvDetailWithInfoResult>>()
        tvDetailWithInfoResultLive.value = ApiResponse.success(tvDetailWithInfoResult)

        val tvDetailWithInfoEntity = TvDetailWithInfoEntity(
            FakeDataDummy.getTvDetailEntity(),
            listOf(FakeDataDummy.getTvImageEntity()),
            listOf(FakeDataDummy.getTvReviewEntity()),
            listOf(FakeDataDummy.getTvSimilarEntity())
        )

        val tvDetailWithInfoEntityLive = MutableLiveData<TvDetailWithInfoEntity>()
        tvDetailWithInfoEntityLive.value = tvDetailWithInfoEntity

        `when`(remote.getDetailTvRepo(idTv)).thenReturn(tvDetailWithInfoResultLive)
        `when`(localRepository.getTvDetailById(idTv)).thenReturn(tvDetailWithInfoEntityLive)

        val result = LiveDataUtilsTest.getValue(mainRepository.getDetailTvData(idTv))

        verify(remote).getDetailTvRepo(idTv)
        verify(localRepository, times(3)).getTvDetailById(idTv)

        assertNotNull(result.body)
        assertNotNull(result.body?.tvDetailEntity)
        assertNotNull(result.body?.listTvImage)
        assertNotNull(result.body?.listTvReview)
        assertNotNull(result.body?.listTvSimilar)
        assertEquals(tvDetailWithInfoEntity, result.body)

    }

    @Test
    fun return_movie_favorite_when_get_movie_favorite_data_success() {
        val movieFavoriteDummy = FakeDataDummy.getMovieDummyEntity()

        `when`(localRepository.getAllMovieFavorite()).thenReturn(dataSourceMovie)

        mainRepository.getFavoriteMovieData()

        val result = Resource.success(PagedListUtils.mockePagedList(movieFavoriteDummy))

        verify(localRepository).getAllMovieFavorite()
        assertNotNull(result.body)
        assertEquals(movieFavoriteDummy.size, result.body?.size)
    }

    @Test
    fun return_tv_favorite_when_get_favorite_tv_data_success() {
        val tvFavoriteDummy = FakeDataDummy.getTvDummyEntity()

        `when`(localRepository.getAllTvShowFavorite()).thenReturn(dataSourceTv)

        mainRepository.getFavoriteTvData()

        val result = Resource.success(PagedListUtils.mockePagedList(tvFavoriteDummy))

        verify(localRepository).getAllTvShowFavorite()
        assertNotNull(result.body)
        assertEquals(tvFavoriteDummy.size, result.body?.size)
    }

    @Test
    fun return_movie_favorite_by_id_when_get_movie_favorite_by_id_success() {
        val movieFavoriteByIdDummy = FakeDataDummy.getMovieDummyEntity().first()
        val movieFavoriteByIdLive = MutableLiveData<MoviePopularEntity>()
        movieFavoriteByIdLive.value = movieFavoriteByIdDummy

        `when`(localRepository.getMovieFavoriteById(idMovie)).thenReturn(movieFavoriteByIdLive)

        val result = LiveDataUtilsTest.getValue(mainRepository.getMovieFavoriteById(idMovie))

        verify(localRepository).getMovieFavoriteById(idMovie)
        assertNotNull(result)
        assertEquals(movieFavoriteByIdDummy, result)
    }

    @Test
    fun return_tv_favorite_by_id_when_get_tv_favorite_by_id_success() {
        val tvFavoriteDummy = FakeDataDummy.getTvDummyEntity().first()
        val tvFavoriteLive = MutableLiveData<TvPopularEntity>()
        tvFavoriteLive.value = tvFavoriteDummy

        `when`(localRepository.getTvFavoriteById(idTv)).thenReturn(tvFavoriteLive)

        val result = LiveDataUtilsTest.getValue(mainRepository.getTvFavoriteById(idTv))

        verify(localRepository).getTvFavoriteById(idTv)
        assertNotNull(result)
        assertEquals(tvFavoriteDummy, result)
    }

    @Test
    fun return_search_with_movie_tv_people_entity_when_get_search_data_success() {
        val searchWithGenreResult = SearchWithGenreResult(
            FakeDataDummy.getSearchMovieTv().results,
            FakeDataDummy.getMovieGenreDummyResult(),
            FakeDataDummy.getTvGenreDummyResult()
        )

        val searchWithGenreLive = MutableLiveData<ApiResponse<SearchWithGenreResult>>()
        searchWithGenreLive.value = ApiResponse.success(searchWithGenreResult)

        val searchWithMovieTvPeopleEntity = SearchWithMovieTvPeopleEntity(
            null,
            emptyList(),
            emptyList(),
            listOf(FakeDataDummy.getPeopleEntity())
        )

        val searchWithMovieTvPeopleLive = MutableLiveData<SearchWithMovieTvPeopleEntity>()
        searchWithMovieTvPeopleLive.value = searchWithMovieTvPeopleEntity

        `when`(remote.getSearchRepo(querySearch)).thenReturn(searchWithGenreLive)
        `when`(localRepository.getSearchEntity()).thenReturn(searchWithMovieTvPeopleLive)

        val result = LiveDataUtilsTest.getValue(mainRepository.getSearchData(querySearch))

        verify(remote).getSearchRepo(querySearch)
        verify(localRepository, times(3)).getSearchEntity()

        assertNotNull(result.body)
        assertEquals(searchWithMovieTvPeopleEntity, result.body)
    }

    @Test
    fun return_people_detail_entity_when_get_people_data_success() {
        val peopleDetailResultDummy = FakeDataDummy.getPeopleDetailResult()
        val peopleDetailResultLive = MutableLiveData<ApiResponse<PeopleDetailResult>>()
        peopleDetailResultLive.value = ApiResponse.success(peopleDetailResultDummy)

        val peopleDetailEntityDummy = FakeDataDummy.getPeopleDetailEntity()
        val peopleDetailEntityLive = MutableLiveData<PeopleDetailEntity>()
        peopleDetailEntityLive.value = peopleDetailEntityDummy

        `when`(remote.getDetailPersonRepo(idPeople)).thenReturn(peopleDetailResultLive)
        `when`(localRepository.getPeopleDetailById(idPeople)).thenReturn(peopleDetailEntityLive)

        val result = LiveDataUtilsTest.getValue(mainRepository.getPeopleDetailData(idPeople))

        verify(remote).getDetailPersonRepo(idPeople)
        verify(localRepository, times(3)).getPeopleDetailById(idPeople)

        assertNotNull(result.body)
        assertEquals(peopleDetailEntityDummy, result.body)
    }

    @Test
    fun should_insert_movie_favorite_to_database_when_insert_movie_favorite_success() {
        val movieFavoriteDummy = FakeDataDummy.getMovieDummyEntity().first()

        doNothing().`when`(localRepository).insertMovieFavorite(movieFavoriteDummy)

        mainRepository.insertMovieFavorite(movieFavoriteDummy)

        verify(localRepository).insertMovieFavorite(movieFavoriteDummy)
    }

    @Test
    fun should_delete_movie_favorite_from_database_when_delete_movie_favorite_success() {
        val movieFavoriteDummy = FakeDataDummy.getMovieDummyEntity().first()

        doNothing().`when`(localRepository).deleteMovieFavorite(movieFavoriteDummy)

        mainRepository.deleteMovieFavorite(movieFavoriteDummy)

        verify(localRepository).deleteMovieFavorite(movieFavoriteDummy)
    }

    @Test
    fun should_delete_movie_by_id_from_database_when_delete_movie_favorite_by_id_success() {
        doNothing().`when`(localRepository).deleteMovieFavoriteById(idMovie)

        mainRepository.deleteMovieFavoriteById(idMovie)

        verify(localRepository).deleteMovieFavoriteById(idMovie)
    }


    @Test
    fun should_insert_tv_favorite_to_database_when_insert_tv_favorite_success() {
        val tvFavoriteDummy = FakeDataDummy.getTvDummyEntity().first()

        doNothing().`when`(localRepository).insertTvFavorite(tvFavoriteDummy)

        mainRepository.insertTvFavorite(tvFavoriteDummy)

        verify(localRepository).insertTvFavorite(tvFavoriteDummy)
    }

    @Test
    fun should_delete_tv_favorite_from_database_when_delete_tv_favorite_success() {
        val tvFavoriteDummy = FakeDataDummy.getTvDummyEntity().first()

        doNothing().`when`(localRepository).deleteTvFavorite(tvFavoriteDummy)

        mainRepository.deleteTvFavorite(tvFavoriteDummy)

        verify(localRepository).deleteTvFavorite(tvFavoriteDummy)
    }

    @Test
    fun should_delete_tv_favorite_by_id_from_database_when_delete_tv_favorite_by_id_success() {
        doNothing().`when`(localRepository).deleteTvFavoriteById(idTv)

        mainRepository.deleteTvFavoriteById(idTv)

        verify(localRepository).deleteTvFavoriteById(idTv)
    }
}