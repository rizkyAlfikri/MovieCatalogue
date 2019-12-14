package com.dicoding.picodiploma.moviecatalogue.ui.detailmovie

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
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
    private lateinit var observerDetailMovie: Observer<Resource<MovieDetailEntity>>

    private lateinit var detailMovieViewModel: DetailMovieViewModel

    private val idMovie = 475557

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailMovieViewModel = DetailMovieViewModel(repository)

    }

    @Test
    fun return_live_data_movie_detail_entity_when_get_movie_detail_success() {
        val movieDetailEntity = Resource.success(FakeDataDummy.getMovieDetaiEntityDummy())
        val movieDetailLive = MutableLiveData<Resource<MovieDetailEntity>>()
        movieDetailLive.value = movieDetailEntity

        `when`(repository.getDetailMovieData(idMovie)).thenReturn(movieDetailLive)

        detailMovieViewModel.setIdMovie(idMovie)

        detailMovieViewModel.getMovieDetail().observeForever(observerDetailMovie)

        verify(observerDetailMovie).onChanged(movieDetailEntity)
    }
}
