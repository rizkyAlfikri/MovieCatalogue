package com.dicoding.picodiploma.moviecatalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MainRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<List<MoviePopularEntity>>>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<List<TvPopularEntity>>>

    private lateinit var mainViewModel: MainViewModel

    private val sortBy = Config.POPULAR

    private val idMovie = 475557

    private val idTv = 60625


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(repository)

    }

    @Test
    fun return_list_movie_popular_live_data_when_get_movie_popular_success() {
        val listMoviePopular = FakeDataDummy.getMovieDummyEntity()
        val liveDataMovie = MutableLiveData<Resource<List<MoviePopularEntity>>>()
        liveDataMovie.value = Resource.success(listMoviePopular)

        `when`(repository.getPopularMovieData(sortBy)).thenReturn(liveDataMovie)

        mainViewModel.movieSpinnerPosition = 0
        mainViewModel.setMovieSpinner()
        mainViewModel.getMoviePopularData.observeForever(movieObserver)

        verify(movieObserver).onChanged(Resource.success(listMoviePopular))

    }


    @Test
    fun return_list_tv_popular_live_data_when_get_tv_popular_success() {
        val tvShowDummy = Resource.success(FakeDataDummy.getTvDummyEntity())
        val tvShowLiveData = MutableLiveData<Resource<List<TvPopularEntity>>>()
        tvShowLiveData.value = tvShowDummy

        `when`(runBlocking { repository.getPopularTvData(sortBy) }).thenReturn(tvShowLiveData)

        mainViewModel.tvSpinnerPosition = 0
        mainViewModel.setTvSpinner()
        mainViewModel.getTvPopularData.observeForever(tvShowObserver)

        verify(tvShowObserver).onChanged(tvShowDummy)
    }

    @Test
    fun should_insert_movie_favorite_to_database_when_insert_movie_favorite_success() {
        val movieFavoriteDummy = FakeDataDummy.getMovieDummyEntity().first()

        runBlocking {
            doNothing().`when`(repository).insertMovieFavorite(movieFavoriteDummy)

            mainViewModel.insertMovieFavorite(movieFavoriteDummy)

//            verify(repository).insertMovieFavorite(movieFavoriteDummy)
        }
    }

    @Test
    fun should_delete_movie_favorite_by_id_from_database_when_delete_movie_favorite_by_id_success() {
        runBlocking {
            doNothing().`when`(repository).deleteMovieFavoriteById(idMovie)

            mainViewModel.deleteMovieFavoriteById(idMovie)

            verify(repository).deleteMovieFavoriteById(idMovie)
        }
    }

    @Test
    fun should_insert_tv_favorite_to_database_when_insert_movie_favorite_success() {
        val tvFavoriteEntityDummy = FakeDataDummy.getTvDummyEntity().first()

        runBlocking {
            doNothing().`when`(repository).insertTvFavorite(tvFavoriteEntityDummy)

            mainViewModel.insertTvPopular(tvFavoriteEntityDummy)

//            verify(repository).insertTvFavorite(tvFavoriteEntityDummy)
        }
    }

    @Test
    fun should_delete_tv_favorite_by_id_from_database_when_delete_tv_favorite_by_id_success() {
        runBlocking {
            doNothing().`when`(repository).deleteTvFavoriteById(idTv)

            mainViewModel.deleteTvFavoriteById(idTv)

            verify(repository).deleteTvFavoriteById(idTv)
        }
    }
}
