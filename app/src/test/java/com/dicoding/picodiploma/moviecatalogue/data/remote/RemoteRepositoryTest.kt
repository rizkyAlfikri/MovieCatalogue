package com.dicoding.picodiploma.moviecatalogue.data.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.dicoding.picodiploma.moviecatalogue.FakeRemoteRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.MovieDetailWithInfoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchWithGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.TvDetailWithInfoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResponse
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

    private val idPeople = 119143

    private val querySearch = "Kana Hanazawa"

    private val sortBy = Config.POPULAR

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        fakeRemoteRepository = FakeRemoteRepository(apiService, apiKey)
    }

    @Test
    fun return_movie_popular_api_response_when_get_movie_popular_repo_success() {
        val movieResponse = FakeDataDummy.getMovieDummyResult()
        val movieGenre = FakeDataDummy.getMovieGenreDummyResult()
        val movieGenreResponse = MovieGenreResponse(movieGenre)

        runBlocking {

            `when`(apiService.getMoviePopularApi(sortBy, apiKey)).thenReturn(movieResponse)
            `when`(apiService.getMovieGenreApi(apiKey)).thenReturn(movieGenreResponse)

            val result =
                LiveDataUtilsTest.getValue(fakeRemoteRepository.getPopularMovieRepo(sortBy))

            verify(apiService).getMoviePopularApi(sortBy, apiKey)
            verify(apiService).getMovieGenreApi(apiKey)

            assertNotNull(result)
            assertNotNull(result.body?.movieResult)
            assertNotNull(result.body?.movieGenreResult)
            assertEquals(movieResponse.results, result.body?.movieResult)
            assertEquals(movieGenre, result.body?.movieGenreResult)
        }
    }

    @Test
    fun return_tv_show_popular_api_response_when_get_popular_tv_repo_success() {
        val tvPopularResponse = FakeDataDummy.getTvDummyResult()
        val tvGenreResult = FakeDataDummy.getTvGenreDummyResult()
        val tvGenreResponse = TvGenreResponse(tvGenreResult)

        runBlocking {
            `when`(apiService.getTvPopularApi(sortBy, apiKey)).thenReturn(tvPopularResponse)
            `when`(apiService.getTvGenreApi(apiKey)).thenReturn(tvGenreResponse)

            val result = LiveDataUtilsTest.getValue(fakeRemoteRepository.getPopularTvRepo(sortBy))

            verify(apiService).getTvPopularApi(sortBy, apiKey)
            verify(apiService).getTvGenreApi(apiKey)

            assertNotNull(result.body)
            assertNotNull(result.body?.listTvShow)
            assertNotNull(result.body?.listTvGenre)
            assertEquals(tvPopularResponse.results, result.body?.listTvShow)
            assertEquals(tvGenreResult, result.body?.listTvGenre)
        }
    }

    @Test
    fun return_movie_detail_api_response_when_get_detail_movie_repo_success() {
        val movieDetailDummy = FakeDataDummy.getMovieDetailResultDummy()
        val movieVideoDummy = FakeDataDummy.getMovieVideoResultDummy()
        val movieReviewDummy = FakeDataDummy.getMovieReviewResultDummy()
        val movieImageDummy = FakeDataDummy.getMovieImageResultDummy()
        val movieSimilarDummy = FakeDataDummy.getMovieDummyResult()


        runBlocking {
            `when`(apiService.getMovieDetailApi(idMovie, apiKey)).thenReturn(movieDetailDummy)
            `when`(apiService.getMovieTrailerApi(idMovie, apiKey)).thenReturn(movieVideoDummy)
            `when`(apiService.getMovieReviewApi(idMovie, apiKey)).thenReturn(movieReviewDummy)
            `when`(apiService.getMovieImageApi(idMovie, apiKey)).thenReturn(movieImageDummy)
            `when`(apiService.getMovieSimilarApi(idMovie, apiKey)).thenReturn(movieSimilarDummy)

            movieDetailDummy.keyVideo = movieVideoDummy.results.first().key

            val result =
                LiveDataUtilsTest.getValue(fakeRemoteRepository.getDetailMovieRepo(idMovie))

            val movieDetailWithInfoResult = MovieDetailWithInfoResult(
                movieDetailDummy,
                movieReviewDummy,
                movieImageDummy,
                movieSimilarDummy.results
            )

            verify(apiService).getMovieDetailApi(idMovie, apiKey)
            verify(apiService).getMovieTrailerApi(idMovie, apiKey)
            verify(apiService).getMovieReviewApi(idMovie, apiKey)
            verify(apiService).getMovieImageApi(idMovie, apiKey)
            verify(apiService).getMovieSimilarApi(idMovie, apiKey)

            assertNotNull(result)
            assertNotNull(result.body)
            assertNotNull(result.body?.movieDetailResult)
            assertNotNull(result.body?.listMovieSimilarResult)
            assertNotNull(result.body?.listMovieReviewResult)
            assertNotNull(result.body?.listMovieImageResult)
            assertEquals(movieDetailWithInfoResult, result.body)
        }
    }

    @Test
    fun return_tv_show_detail_api_response_when_get_detail_tv_repo_success() {
        val tvDetailResultDummy = FakeDataDummy.getTvDetailResultDummy()
        val tvVideoResultDummy = FakeDataDummy.getTvVideoResult()
        val tvReviewResultDummy = FakeDataDummy.getTvReviewResult()
        val tvImageResultDummy = FakeDataDummy.getTvImageResult()
        val tvSimilarResultDummy = FakeDataDummy.getTvDummyResult()


        runBlocking {
            `when`(apiService.getTvDetailApi(idTv, apiKey)).thenReturn(tvDetailResultDummy)
            `when`(apiService.getTvVideoApi(idTv, apiKey)).thenReturn(tvVideoResultDummy)
            `when`(apiService.getTvReviewApi(idTv, apiKey)).thenReturn(tvReviewResultDummy)
            `when`(apiService.getTvImageApi(idTv, apiKey)).thenReturn(tvImageResultDummy)
            `when`(apiService.getTvSimilarApi(idTv, apiKey)).thenReturn(tvSimilarResultDummy)

            val result = LiveDataUtilsTest.getValue(fakeRemoteRepository.getDetailTvRepo(idTv))

            tvDetailResultDummy.keyVideo = tvVideoResultDummy.results.first().key
            val tvDetailWithInfoResult = TvDetailWithInfoResult(
                tvDetailResultDummy,
                tvReviewResultDummy,
                tvImageResultDummy,
                tvSimilarResultDummy.results
            )

            verify(apiService).getTvDetailApi(idTv, apiKey)
            verify(apiService).getTvImageApi(idTv, apiKey)
            verify(apiService).getTvVideoApi(idTv, apiKey)
            verify(apiService).getTvReviewApi(idTv, apiKey)
            verify(apiService).getTvSimilarApi(idTv, apiKey)

            assertNotNull(result)
            assertNotNull(result.body)
            assertNotNull(result.body?.tvShowDetail)
            assertNotNull(result.body?.listTvImage)
            assertNotNull(result.body?.listTvReview)
            assertNotNull(result.body?.listTvSimilar)
            assertEquals(tvDetailWithInfoResult, result.body)
        }
    }

    @Test
    fun return_search_with_genre_result_when_get_search_repo_success() {
        val searchResultDummy = FakeDataDummy.getSearchMovieTv()
        val movieGenreResultDummy = MovieGenreResponse(FakeDataDummy.getMovieGenreDummyResult())
        val tvGenreResultDummy = TvGenreResponse(FakeDataDummy.getTvGenreDummyResult())
        runBlocking {
            `when`(apiService.getSearchApi(querySearch, apiKey)).thenReturn(searchResultDummy)
            `when`(apiService.getMovieGenreApi(apiKey)).thenReturn(movieGenreResultDummy)
            `when`(apiService.getTvGenreApi(apiKey)).thenReturn(tvGenreResultDummy)

            val searchWithGenreResult = SearchWithGenreResult(
                searchResultDummy.results,
                movieGenreResultDummy.genres,
                tvGenreResultDummy.genres
            )


            val result = LiveDataUtilsTest.getValue(fakeRemoteRepository.getSearchRepo(querySearch))

            verify(apiService).getSearchApi(querySearch, apiKey)
            verify(apiService).getMovieGenreApi(apiKey)
            verify(apiService).getTvGenreApi(apiKey)

            assertNotNull(result.body)
            assertNotNull(result.body?.listSearchResult)
            assertNotNull(result.body?.listMovieGenre)
            assertNotNull(result.body?.listTvGenre)
            assertEquals(searchWithGenreResult, result.body)

        }
    }

    @Test
    fun return_people_detail_result_when_get_detail_person_repo_success() {
        val personDetailResultDummy = FakeDataDummy.getPeopleDetailResult()

        runBlocking {
            `when`(apiService.getPeopleDetail(idPeople, apiKey)).thenReturn(personDetailResultDummy)

            val result =
                LiveDataUtilsTest.getValue(fakeRemoteRepository.getDetailPersonRepo(idPeople))

            verify(apiService).getPeopleDetail(idPeople, apiKey)

            assertNotNull(result.body)
            assertEquals(personDetailResultDummy, result.body)

        }
    }
}