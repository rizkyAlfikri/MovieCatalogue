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
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResult
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.utils.LiveDataUtilsTest
import com.dicoding.picodiploma.moviecatalogue.utils.PagedListUtils
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import kotlinx.coroutines.runBlocking
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
    fun return_popular_movie_live_data_when_success() {

        `when`(localRepository.getAllMoviePopular()).thenReturn(dataSource)

        mainRepository.getPopularMovieData()

        val listDataMovie = FakeDataDummy.getMovieDummyEntity()
        val result = Resource.success(PagedListUtils.mockePagedList(listDataMovie))

        verify(localRepository).getAllMoviePopular()

        assertNotNull(result)
        assertNotNull(result.body)
        assertEquals(1, result.body?.size)
    }

    @Test
    fun return_popular_tv_show_live_data_when_success() {
        val expectedTvShowData =
            listOf(
                TvPopularEntity(
                    60625,
                    "Rick and Morty",
                    "$imageUrl/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                    "Animation, Comedy, Action & Adventure, Sci-Fi & Fantasy",
                    8.6,
                    "2013-12-02"
                )
            )

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

        val tvShowResponse =
            TvPopularResponse(
                1,
                tvShowDummy,
                1,
                1
            )
        val tvGenre =
            listOf(
                TvGenreResult(
                    16,
                    "Animation"
                ),
                TvGenreResult(
                    35,
                    "Comedy"
                ),
                TvGenreResult(
                    10759,
                    "Action & Adventure"
                ),
                TvGenreResult(
                    10765,
                    "Sci-Fi & Fantasy"
                )
            )
        val tvGenreResponse =
            TvGenreResponse(
                tvGenre
            )

        runBlocking {
            `when`(remote.getPopularTvRepo()).thenReturn(tvShowResponse)
            `when`(remote.getGenreTvRepo()).thenReturn(tvGenreResponse)

            val result = mainRepository.getPopularTvData()

            verify(remote).getPopularTvRepo()
            verify(remote).getGenreTvRepo()

            assertNotNull(result)
            assertEquals(expectedTvShowData, result)
        }
    }

    @Test
    fun return_movie_detail_entity_live_data_when_get_detail_movie_data_success() {
        val movieDetailResult = FakeDataDummy.getMovieDetailResultDummy()
        val movieDetailResultLive = MutableLiveData<ApiResponse<MovieDetailResult>>()
        movieDetailResultLive.value = ApiResponse.success(movieDetailResult)

        val movieDetailEntityDummy = FakeDataDummy.getMovieDetaiEntityDummy()
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
    fun return_detail_tv_live_data_when_success() {
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
                "Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.",
                "/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                "Returning Series",
                8.6
            )

        val expectedDetailTv =
            TvDetailEntity(
                genres = "Sci-Fi & Fantasy, Action & Adventure, Animation, Comedy",
                backdropPath = "$imageUrl/mzzHr6g1yvZ05Mc7hNj3tUdy2bM.jpg",
                homepage = "http://www.adultswim.com/videos/rick-and-morty",
                id = 60625,
                overview = "Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.",
                imagePath = "$imageUrl/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                releaseDate = "2013-12-02",
                runtime = 22,
                status = "Returning Series",
                title = "Rick and Morty",
                voteAverage = 8.6
            )

        runBlocking {
            `when`(remote.getDetailTvRepo(idTv)).thenReturn(detailTvDummy)

            val result = mainRepository.getDetailTvData(idTv)

            verify(remote).getDetailTvRepo(idTv)

            assertNotNull(result)
            assertEquals(expectedDetailTv, result)

        }
    }

}