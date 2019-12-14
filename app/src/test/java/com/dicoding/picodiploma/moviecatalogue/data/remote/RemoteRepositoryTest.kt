package com.dicoding.picodiploma.moviecatalogue.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.picodiploma.moviecatalogue.FakeRemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MovieResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResult
import com.dicoding.picodiploma.moviecatalogue.network.ApiService
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.utils.LiveDataUtilsTest
import kotlinx.coroutines.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
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
    fun return_movie_popular_response_when_success() {
        runBlocking {
            val movieResult = FakeDataDummy.getMovieDummyResult()
            val movieGenre = FakeDataDummy.getMovieGenreDummyResult()
            val movieResponse = MovieResponse(1, movieResult, 1, 20)
            val movieGenreResponse = MovieGenreResponse(movieGenre)

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
    fun return_tv_show_popular_response_when_success() {
        val tvShowDummy = listOf(
            TvPopularResult(
                60625,
                "Rick and Morty",
                "/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                listOf(16, 35, 10759, 10765),
                8.6,
                "2013-12-02"
            )
        )

        val tvPopularResponseDummy =
            TvPopularResponse(
                1,
                tvShowDummy,
                1,
                1
            )

        runBlocking {

            `when`(apiService.getTvPopularApi(apiKey)).thenReturn(tvPopularResponseDummy)

            val result = fakeRemoteRepository.getPopularTvRepo()

            verify(apiService).getTvPopularApi(apiKey)

            assertNotNull(result)
            assertEquals(tvPopularResponseDummy, result)
        }
    }

    @Test
    fun return_movie_detail_result_when_success() {
        val movieDetailDummy = FakeDataDummy.getMovieDetailResultDummy()

        runBlocking {
            `when`(apiService.getMovieDetailApi(idMovie, apiKey)).thenReturn(movieDetailDummy)

            val result = fakeRemoteRepository.getDetailMovieRepo(idMovie)

            verify(apiService).getMovieDetailApi(idMovie, apiKey)
            assertNotNull(result.value)
            assertNotNull(result.value?.body)
            assertEquals(movieDetailDummy, result.value?.body)
        }
    }

    @Test
    fun return_tv_show_detail_result_when_success() {
        val detailTvDummy =
            TvDetailResult(
                "/mzzHr6g1yvZ05Mc7hNj3tUdy2bM.jpg",
                "2013-12-02",
                listOf(
                    TvGenreResult(
                        10765,
                        "Sci-Fi & Fantasy"
                    ),
                    TvGenreResult(
                        10759,
                        "Action & Adventure"
                    ),
                    TvGenreResult(
                        16,
                        "Animation"
                    ),
                    TvGenreResult(
                        35,
                        "Comedy"
                    )
                ),
                "http://www.adultswim.com/videos/rick-and-morty",
                listOf(22),
                60625,
                "Rick and Morty",
                "Rick is a mentally-unbalanced but scientifically-gifted old man who has " +
                        "recently reconnected with his family. He spends most of his time involving " +
                        "his young grandson Morty in dangerous, outlandish adventures throughout " +
                        "space and alternate universes. Compounded with Morty's already unstable " +
                        "family life, these events cause Morty much distress at home and school.",
                "/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                "Returning Series",
                8.6
            )

        runBlocking {
            `when`(apiService.getTvDetailApi(idTv, apiKey)).thenReturn(detailTvDummy)

            val result = fakeRemoteRepository.getDetailTvRepo(idTv)

            verify(apiService).getTvDetailApi(idTv, apiKey)

            assertNotNull(result)
            assertEquals(detailTvDummy, result)
        }
    }

    @Test
    fun return_tv_show_genre_response_when_success() {
        val tvGenreResponse =
            TvGenreResponse(
                listOf(
                    TvGenreResult(
                        10765,
                        "Sci-Fi & Fantasy"
                    ),
                    TvGenreResult(
                        10759,
                        "Action & Adventure"
                    ),
                    TvGenreResult(
                        16,
                        "Animation"
                    ),
                    TvGenreResult(
                        35,
                        "Comedy"
                    )
                )
            )

        runBlocking {
            `when`(apiService.getTvGenreApi(apiKey)).thenReturn(tvGenreResponse)

            val result = fakeRemoteRepository.getGenreTvRepo()

            verify(apiService).getTvGenreApi(apiKey)

            assertNotNull(result)
            assertEquals(tvGenreResponse, result)
        }
    }
}