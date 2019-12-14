package com.dicoding.picodiploma.moviecatalogue.ui.detailtvshow

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvDetailEntity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.utils.Config
import com.dicoding.picodiploma.moviecatalogue.utils.TestContextProvider
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class DetailTvViewModelTest {

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: MainRepository

    @Mock
    private lateinit var observerTvDetail: Observer<TvDetailEntity>

    private lateinit var detailTvViewModel: DetailTvViewModel

    private val imageUrl = Config.IMAGE_URL_BASE_PATH
    private val idTv = 60625

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        detailTvViewModel = DetailTvViewModel(repository, TestContextProvider())
    }

    @Test
    fun return_tv_detail_entity_when_get_tv_detail_success() {

        val detailTvEntity =
            TvDetailEntity(
                genres = " Sci-Fi & Fantasy, Action & Adventure, Animation, Comedy ",
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

        Mockito.`when`(runBlocking { repository.getDetailTvData(idTv) })
            .thenReturn(detailTvEntity)

        detailTvViewModel.setTvId(idTv)
        detailTvViewModel.getTvDetail().observeForever(observerTvDetail)
        Mockito.verify(observerTvDetail).onChanged(detailTvEntity)
    }
}