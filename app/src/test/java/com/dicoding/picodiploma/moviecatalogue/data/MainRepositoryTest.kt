package com.dicoding.picodiploma.moviecatalogue.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.dicoding.picodiploma.moviecatalogue.FakeMainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.LocalRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.ApiResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.RemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvResultWithGenre
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
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
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainRepositoryTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var remote: RemoteRepository

    @Mock
    private lateinit var localRepository: LocalRepository

    @Mock
    private lateinit var dataSource: DataSource.Factory<Int, MoviePopularEntity>

    private lateinit var mainRepository: FakeMainRepository

    private val imageUrl = "https://image.tmdb.org/t/p/w500/"

    private val idTv = 60625

    private val idMovie = 475557

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainRepository = FakeMainRepository(remote, localRepository, imageUrl)
    }

    @Test
    fun return_popular_movie_live_data_when_get_popular_movie_data_success() {

        val listDataMovie = FakeDataDummy.getMovieDummyEntity()
        val moviePopularLive = MutableLiveData<List<MoviePopularEntity>>()
        moviePopularLive.value = listDataMovie

        `when`(localRepository.getAllMoviePopular()).thenReturn(moviePopularLive)

        val result= LiveDataUtilsTest.getValue(mainRepository.getPopularMovieData())


        verify(localRepository).getAllMoviePopular()

        assertNotNull(result)
        assertNotNull(result.body)
        assertEquals(listDataMovie, result.body)
    }

    @Test
    fun return_popular_tv_show_live_data_when_get_popular_tv_data_success() {
        val listTvResult = FakeDataDummy.getTvDummyResult()
        val listGenreTv = FakeDataDummy.getTvGenreDummyResult()
        val liveDataTvResult = MutableLiveData<ApiResponse<TvResultWithGenre>>()
        liveDataTvResult.value = ApiResponse.success(TvResultWithGenre(listTvResult, listGenreTv))

        val listTvEntity = FakeDataDummy.getTvDummyEntity()
        val liveDataTvEntity = MutableLiveData<List<TvPopularEntity>>()
        liveDataTvEntity.value = listTvEntity

        `when`(remote.getPopularTvRepo()).thenReturn(liveDataTvResult)
        `when`(localRepository.getPopularTv()).thenReturn(liveDataTvEntity)

        val result = LiveDataUtilsTest.getValue(mainRepository.getPopularTvData())

        verify(remote).getPopularTvRepo()
        verify(localRepository).getPopularTv()

        assertNotNull(result.body)
        assertEquals(listTvEntity, result.body)
    }

    @Test
    fun return_movie_detail_entity_live_data_when_get_detail_movie_data_success() {
        val movieDetailResult = FakeDataDummy.getMovieDetailResultDummy()
        val movieDetailResultLive = MutableLiveData<ApiResponse<MovieDetailResult>>()
        movieDetailResultLive.value = ApiResponse.success(movieDetailResult)

        val movieDetailEntityDummy = FakeDataDummy.getMovieDetailEntityDummy()
        val movieDetailEntityLive = MutableLiveData<MovieDetailEntity>()
        movieDetailEntityLive.value = movieDetailEntityDummy

        `when`(remote.getDetailMovieRepo(idMovie)).thenReturn(movieDetailResultLive)
        `when`(localRepository.getMovieDetailById(idMovie)).thenReturn(movieDetailEntityLive)

        val result = LiveDataUtilsTest.getValue(mainRepository.getDetailMovieData(idMovie))

        verify(remote).getDetailMovieRepo(idMovie)
        verify(localRepository).getMovieDetailById(idMovie)

        assertNotNull(result.body)
        assertEquals(movieDetailEntityDummy, result.body)
    }

    @Test
    fun return_detail_tv_live_data_when_get_tv_detail_by_id_success() {
        val tvDetailResult = FakeDataDummy.getTvDetailResultDummy()
        val liveTvDetailResult = MutableLiveData<ApiResponse<TvDetailResult>>()
        liveTvDetailResult.value = ApiResponse.success(tvDetailResult)

        val tvDetailEntity = FakeDataDummy.getTvDetailEntity()
        val liveTvDetailEntity = MutableLiveData<TvDetailEntity>()
        liveTvDetailEntity.value = tvDetailEntity

        `when`(remote.getDetailTvRepo(idTv)).thenReturn(liveTvDetailResult)
        `when`(localRepository.getTvDetailById(idTv)).thenReturn(liveTvDetailEntity)

        val result = LiveDataUtilsTest.getValue(mainRepository.getTvDetailById(idTv))

        assertNotNull(result)
        assertEquals(tvDetailEntity, result)

    }

}