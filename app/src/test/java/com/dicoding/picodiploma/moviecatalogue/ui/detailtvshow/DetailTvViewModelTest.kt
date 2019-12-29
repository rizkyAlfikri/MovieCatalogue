package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.utils.TestContextProvider
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class DetailTvViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MainRepository

    @Mock
    private lateinit var observerTvDetail: Observer<Resource<TvDetailEntity>>

    @Mock
    private lateinit var observerFavoriteTv: Observer<TvPopularEntity>

    private lateinit var detailTvViewModel: DetailTvViewModel

    private val idTv = 60625

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailTvViewModel = DetailTvViewModel(repository, TestContextProvider())
    }

    @Test
    fun return_tv_detail_entity_live_data_when_get_tv_detail_success() {

        val detailTvEntity = Resource.success(FakeDataDummy.getTvDetailEntity())
        val liveTvDetailEntity = MutableLiveData<Resource<TvDetailEntity>>()
        liveTvDetailEntity.value = detailTvEntity

        `when`(runBlocking { repository.getDetailTvData(idTv) })
            .thenReturn(liveTvDetailEntity)

        detailTvViewModel.setTvId(idTv)
        detailTvViewModel.getTvDetail().observeForever(observerTvDetail)
        verify(observerTvDetail).onChanged(detailTvEntity)
    }


}