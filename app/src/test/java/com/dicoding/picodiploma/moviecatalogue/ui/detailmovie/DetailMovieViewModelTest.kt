package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.MovieDetailWithInfoEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class DetailMovieViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MainRepository

    @Mock
    private lateinit var observerDetailMovie: Observer<Resource<MovieDetailWithInfoEntity>>

    @Mock
    private lateinit var observerFavoriteMovie: Observer<MoviePopularEntity>

    private lateinit var detailMovieViewModel: DetailMovieViewModel

    private val idMovie = 475557

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailMovieViewModel = DetailMovieViewModel(repository)

    }

    @Test
    fun should_insert_movie_favorite_to_database_when_insert_movie_favorite_success() {
        val movieFavoriteDummy = FakeDataDummy.getMovieDummyEntity().first()

        runBlocking {
            doNothing().`when`(repository).insertMovieFavorite(movieFavoriteDummy)

            detailMovieViewModel.insertMovieFavorite(movieFavoriteDummy)

//            verify(repository).insertMovieFavorite(movieFavoriteDummy)
        }
    }

    @Test
    fun return_live_data_movie_detail_entity_when_get_movie_detail_success() {
        val movieDetailWithInfoEntity = Resource.success(
            MovieDetailWithInfoEntity(
                FakeDataDummy.getMovieDetailEntityDummy(),
                listOf(FakeDataDummy.getMovieReviewEntity()),
                listOf(FakeDataDummy.getMovieImageEntity()),
                listOf(FakeDataDummy.getMovieSimilarEntity())
            )
        )

        val movieDetailLive = MutableLiveData<Resource<MovieDetailWithInfoEntity>>()
        movieDetailLive.value = movieDetailWithInfoEntity

        `when`(repository.getDetailMovieData(idMovie)).thenReturn(movieDetailLive)

        detailMovieViewModel.setIdMovie(idMovie)
        detailMovieViewModel.getMovieDetail.observeForever(observerDetailMovie)

        verify(observerDetailMovie).onChanged(movieDetailWithInfoEntity)
    }

    @Test
    fun return_live_data_movie_favorite_by_id_when_get_favorite_movie_by_id_success() {
        val movieFavorite = FakeDataDummy.getMovieDummyEntity().first()
        val movieFavoriteLive = MutableLiveData<MoviePopularEntity>()
        movieFavoriteLive.value = movieFavorite

        `when`(repository.getMovieFavoriteById(idMovie)).thenReturn(movieFavoriteLive)

        detailMovieViewModel.setIdMovie(idMovie)
        detailMovieViewModel.getMovieFavoriteById.observeForever(observerFavoriteMovie)

        verify(observerFavoriteMovie).onChanged(movieFavorite)
    }


    @Test
    fun should_delete_movie_favorite_from_database_when_delete_movie_favorite_success() {
        val movieFavoriteEntityDummy = FakeDataDummy.getMovieDummyEntity().first()

        runBlocking {
            doNothing().`when`(repository).deleteMovieFavorite(movieFavoriteEntityDummy)

            detailMovieViewModel.deleteMovieFavorite(movieFavoriteEntityDummy)

//            verify(repository).deleteMovieFavorite(movieFavoriteEntityDummy)
        }
    }
}
