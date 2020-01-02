package com.dicoding.picodiploma.moviecatalogue.ui.detailpeople

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class PeopleDetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var peopleObserver: Observer<Resource<PeopleDetailEntity>>

    private lateinit var peopleDetailViewModel: PeopleDetailViewModel

    private val idPeople = 119143

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        peopleDetailViewModel = PeopleDetailViewModel(mainRepository)
    }

    @Test
    fun return_people_detail_entity_when_get_people_detail_success() {
        val peopleDetailEntityDummy = FakeDataDummy.getPeopleDetailEntity()
        val peopleDetailEntityLive = MutableLiveData<Resource<PeopleDetailEntity>>()
        peopleDetailEntityLive.value = Resource.success(peopleDetailEntityDummy)

        `when`(mainRepository.getPeopleDetailData(idPeople)).thenReturn(peopleDetailEntityLive)

        peopleDetailViewModel.setIdPeople(idPeople)
        peopleDetailViewModel.getPeopleDetail.observeForever(peopleObserver)

        verify(peopleObserver).onChanged(Resource.success(peopleDetailEntityDummy))
    }
}