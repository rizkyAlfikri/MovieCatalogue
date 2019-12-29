package com.dicoding.picodiploma.moviecatalogue.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.picodiploma.moviecatalogue.FakeRemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MovieResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResponse
import com.dicoding.picodiploma.moviecatalogue.network.ApiService
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.utils.LiveDataUtilsTest
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class RemoteRepositoryTest {

    @get:Rule
    val instantTask = InstantTaskExecutorRule()

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var fakeRemoteRepository: FakeRemoteRepository

    private val apiKey = Config.API_KEY

    private val idMovie = 475557

    private val idTv = 60625

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fakeRemoteRepository = FakeRemoteRepository(apiService, apiKey)
    }

    @Test
    fun return_movie_popular_api_response_when_get_movie_popular_repo_success() {
        val movieResult = FakeDataDummy.getMovieDummyResult()
        val movieGenre = FakeDataDummy.getMovieGenreDummyResult()
        val movieResponse = MovieResponse(1, movieResult, 1, 20)
        val movieGenreResponse = MovieGenreResponse(movieGenre)

        runBlocking {

            `when`(apiService.getMoviePopularApi(apiKey)).thenReturn(movieResponse)
            `when`(apiService.getMovieGenreApi(apiKey)).thenReturn(movieGenreResponse)

            val result = LiveDataUtilsTest.getValue(fakeRemoteRepository.getPopularMovieRepo())

            verify(apiService).getMoviePopularApi(apiKey)
            verify(apiService).getMovieGenreApi(apiKey)

            assertNotNull(result)
            assertNotNull(result.body?.movieResult)
            assertNotNull(result.body?.movieGenreResult)
            assertEquals(movieResult, result.body?.movieResult)
            assertEquals(movieGenre, result.body?.movieGenreResult)
        }
    }

    @Test
    fun return_tv_show_popular_api_response_when_get_popular_tv_repo_success() {
        val tvPopularResult = FakeDataDummy.getTvDummyResult()
        val tvGenreResult = FakeDataDummy.getTvGenreDummyResult()
        val tvPopularResponse = TvPopularResponse(1, tvPopularResult, 1, 1)
        val tvGenreResponse = TvGenreResponse(tvGenreResult)

        runBlocking {
            `when`(apiService.getTvPopularApi(apiKey)).thenReturn(tvPopularResponse)
            `when`(apiService.getTvGenreApi(apiKey)).thenReturn(tvGenreResponse)

            val result = LiveDataUtilsTest.getValue(fakeRemoteRepository.getPopularTvRepo())

            verify(apiService).getTvPopularApi(apiKey)
            verify(apiService).getTvGenreApi(apiKey)

            assertNotNull(result.body)
            assertNotNull(result.body?.listTvShow)
            assertNotNull(result.body?.listTvGenre)
            assertEquals(tvPopularResult, result.body?.listTvShow)
            assertEquals(tvGenreResult, result.body?.listTvGenre)
        }
    }

    @Test
    fun return_movie_detail_api_response_when_get_detail_movie_repo_success() {
        val movieDetailDummy = FakeDataDummy.getMovieDetailResultDummy()

        runBlocking {
            `when`(apiService.getMovieDetailApi(idMovie, apiKey)).thenReturn(movieDetailDummy)

            val result = LiveDataUtilsTest.getValue(fakeRemoteRepository.getDetailMovieRepo(idMovie))

            verify(apiService).getMovieDetailApi(idMovie, apiKey)
            assertNotNull(result)
            assertNotNull(result.body)
            assertEquals(movieDetailDummy, result.body)
        }
    }

    @Test
    fun return_tv_show_detail_api_response_when_get_detail_tv_repo_success() {
        val tvDetailResult = FakeDataDummy.getTvDetailResultDummy()

        runBlocking {
            `when`(apiService.getTvDetailApi(idTv, apiKey)).thenReturn(tvDetailResult)


            val result = LiveDataUtilsTest.getValue(fakeRemoteRepository.getDetailTvRepo(idTv))

            verify(apiService).getTvDetailApi(idTv, apiKey)

            assertNotNull(result)
            assertNotNull(result.body)
            assertEquals(tvDetailResult, result.body)
        }
    }
}