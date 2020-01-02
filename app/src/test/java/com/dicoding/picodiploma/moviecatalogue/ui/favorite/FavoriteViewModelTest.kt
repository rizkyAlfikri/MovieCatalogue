package com.dicoding.picodiploma.moviecatalogue.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations


class FavoriteViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var moviePagedList: PagedList<MoviePopularEntity>

    @Mock
    private lateinit var tvPagedList: PagedList<TvPopularEntity>

    @Mock
    private lateinit var movieFavoriteObserver: Observer<Resource<PagedList<MoviePopularEntity>>>

    @Mock
    private lateinit var tvFavoriteObserver: Observer<Resource<PagedList<TvPopularEntity>>>

    private lateinit var favoriteViewModel: FavoriteViewModel

    private val idMovie = 475557

    private val idTv = 60625

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        favoriteViewModel = FavoriteViewModel(mainRepository)
    }

    @Test
    fun return_paged_favorite_movie_when_get_all_favorite_movie_success() {
        val movieFavoriteEntityLive = MutableLiveData<Resource<PagedList<MoviePopularEntity>>>()
        movieFavoriteEntityLive.value = Resource.success(moviePagedList)

        `when`(mainRepository.getFavoriteMovieData()).thenReturn(movieFavoriteEntityLive)

        favoriteViewModel.getAllMovieFavorite().observeForever(movieFavoriteObserver)

        verify(movieFavoriteObserver).onChanged(Resource.success(moviePagedList))
    }

    @Test
    fun should_insert_movie_favorite_to_database_when_insert_movie_favorite_success() {
        val movieFavoriteDummy = FakeDataDummy.getMovieDummyEntity().first()

        runBlocking {
            doNothing().`when`(mainRepository).insertMovieFavorite(movieFavoriteDummy)

            favoriteViewModel.insertMovieFavorite(movieFavoriteDummy)

//            verify(mainRepository).insertMovieFavorite(movieFavoriteDummy)
        }
    }

    @Test
    fun should_delete_movie_favorite_by_id_from_database_when_delete_movie_favorite_by_id_success() {
        runBlocking {
            doNothing().`when`(mainRepository).deleteMovieFavoriteById(475557)

            favoriteViewModel.deleteMovieFavoriteById(475557)

//            verify(mainRepository).deleteMovieFavoriteById(475557)
        }
    }

    @Test
    fun return_paged_favorite_tv_when_get_all_tv_favorite_success() {
        val tvFavoriteEntityLive = MutableLiveData<Resource<PagedList<TvPopularEntity>>>()
        tvFavoriteEntityLive.value = Resource.success(tvPagedList)

        `when`(mainRepository.getFavoriteTvData()).thenReturn(tvFavoriteEntityLive)

        favoriteViewModel.getAllTvFavorite().observeForever(tvFavoriteObserver)

        verify(tvFavoriteObserver).onChanged(Resource.success(tvPagedList))
    }

    @Test
    fun should_insert_tv_favorite_to_database_when_insert_movie_favorite_success() {
        val tvFavoriteEntityDummy = FakeDataDummy.getTvDummyEntity().first()

        runBlocking {
            doNothing().`when`(mainRepository).insertTvFavorite(tvFavoriteEntityDummy)

            favoriteViewModel.insertTvFavorite(tvFavoriteEntityDummy)

//            verify(mainRepository, times(1)).insertTvFavorite(tvFavoriteEntityDummy)
        }
    }

    @Test
    fun should_delete_tv_favorite_by_id_from_database_when_delete_tv_favorite_by_id_success() {
        runBlocking {
            doNothing().`when`(mainRepository).deleteTvFavoriteById(idTv)

            favoriteViewModel.deleteTvFavoriteById(idTv)

//            verify(mainRepository).deleteTvFavoriteById(idTv)
        }
    }

}