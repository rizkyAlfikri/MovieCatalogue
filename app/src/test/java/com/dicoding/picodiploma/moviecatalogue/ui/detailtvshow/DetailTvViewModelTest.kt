package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.TvDetailWithInfoEntity
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

class DetailTvViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MainRepository

    @Mock
    private lateinit var observerTvDetail: Observer<Resource<TvDetailWithInfoEntity>>

    @Mock
    private lateinit var observerFavoriteTv: Observer<TvPopularEntity>

    private lateinit var detailTvViewModel: DetailTvViewModel

    private val idTv = 60625

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailTvViewModel = DetailTvViewModel(repository)
    }

    @Test
    fun return_tv_detail_entity_live_data_when_get_tv_detail_success() {
        val tvDetailWithInfoEntity = Resource.success(
            TvDetailWithInfoEntity(
                FakeDataDummy.getTvDetailEntity(),
                listOf(FakeDataDummy.getTvImageEntity()),
                listOf(FakeDataDummy.getTvReviewEntity()),
                listOf(FakeDataDummy.getTvSimilarEntity())
            )
        )


        val liveTvDetailEntity = MutableLiveData<Resource<TvDetailWithInfoEntity>>()
        liveTvDetailEntity.value = tvDetailWithInfoEntity

        `when`( repository.getDetailTvData(idTv))
            .thenReturn(liveTvDetailEntity)

        detailTvViewModel.setTvId(idTv)
        detailTvViewModel.getTvDetail.observeForever(observerTvDetail)

        verify(observerTvDetail).onChanged(tvDetailWithInfoEntity)
    }

    @Test
    fun return_tv_favorite_by_id_when_get_tv_favorite_by_id_success() {
        val tvFavoriteEntityDummy = FakeDataDummy.getTvDummyEntity().first()
        val tvFavoriteLive = MutableLiveData<TvPopularEntity>()
        tvFavoriteLive.value = tvFavoriteEntityDummy

        `when`(repository.getTvFavoriteById(idTv)).thenReturn(tvFavoriteLive)

        detailTvViewModel.setTvId(idTv)
        detailTvViewModel.getTvFavoriteById.observeForever(observerFavoriteTv)

        verify(observerFavoriteTv).onChanged(tvFavoriteEntityDummy)
    }

    @Test
    fun should_insert_tv_favorite_to_database_when_insert_movie_favorite_success() {
        val tvFavoriteEntityDummy = FakeDataDummy.getTvDummyEntity().first()

        runBlocking {
            doNothing().`when`(repository).insertTvFavorite(tvFavoriteEntityDummy)

            detailTvViewModel.insertTvFavorite(tvFavoriteEntityDummy)

//            verify(repository).insertTvFavorite(tvFavoriteEntityDummy)
        }
    }

    @Test
    fun should_delete_tv_favorite_from_database_when_delete_tv_favorite_success() {
        val tvFavoriteEntityDummy = FakeDataDummy.getTvDummyEntity().first()

        runBlocking {
            doNothing().`when`(repository).deleteTvFavorite(tvFavoriteEntityDummy)

            detailTvViewModel.deleteTvFavorite(tvFavoriteEntityDummy)

//            verify(repository).deleteTvFavorite(tvFavoriteEntityDummy)
        }
    }
}