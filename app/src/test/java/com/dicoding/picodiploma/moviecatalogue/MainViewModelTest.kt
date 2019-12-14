package com.dicoding.picodiploma.moviecatalogue

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.utils.TestContextProvider
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class MainViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MainRepository

    @Mock
    private lateinit var movieObserver: Observer<Resource<PagedList<MoviePopularEntity>>>

    @Mock
    private lateinit var pagedListMovie: PagedList<MoviePopularEntity>

    @Mock
    private lateinit var tvShowObserver: Observer<Resource<List<TvPopularEntity>>>

    private lateinit var mainViewModel: MainViewModel


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel(repository)

    }

    @Test
    fun return_list_movie_popular_live_data_when_get_movie_popular_success() {
        val liveDataMovie = MutableLiveData<Resource<PagedList<MoviePopularEntity>>>()
        liveDataMovie.value = Resource.success(pagedListMovie)

        `when`(repository.getPopularMovieData()).thenReturn(liveDataMovie)

        mainViewModel.getMoviePopularData().observeForever(movieObserver)
        verify(movieObserver).onChanged(Resource.success(pagedListMovie))
    }


    @Test
    fun return_list_tv_popular_live_data_when_get_tv_popular_success() {
        val tvShowDummy = Resource.success(FakeDataDummy.getTvDummyEntity())
        val tvShowLiveData = MutableLiveData<Resource<List<TvPopularEntity>>>()
        tvShowLiveData.value = tvShowDummy


        `when`(runBlocking { repository.getPopularTvData() }).thenReturn(tvShowLiveData)

        mainViewModel.getTvPopular().observeForever(tvShowObserver)
        verify(tvShowObserver).onChanged(tvShowDummy)
    }
}
