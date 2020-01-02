package com.dicoding.picodiploma.moviecatalogue.ui.search

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.dicoding.picodiploma.moviecatalogue.data.source.MainRepository
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchWithMovieTvPeopleEntity
import com.dicoding.picodiploma.moviecatalogue.utils.FakeDataDummy
import com.dicoding.picodiploma.moviecatalogue.vo.Resource
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations

class SearchViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mainRepository: MainRepository

    @Mock
    private lateinit var searchObserver: Observer<Resource<SearchWithMovieTvPeopleEntity>>

    private lateinit var searchViewModel: SearchViewModel

    private val querySearch = "Kana Hanazawa"

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        searchViewModel = SearchViewModel(mainRepository)
    }

    @Test
    fun return_search_with_movie_tv_people_entity_when_get_search_data_success() {
        val searchWithMovieTvPeopleEntityDummy = SearchWithMovieTvPeopleEntity(
            FakeDataDummy.getSearchEntity(),
            FakeDataDummy.getMovieDummyEntity(),
            FakeDataDummy.getTvDummyEntity(),
            listOf(FakeDataDummy.getPeopleEntity())
        )

        val searchWithMovieTvPeopleEntityLive =
            MutableLiveData<Resource<SearchWithMovieTvPeopleEntity>>()
        searchWithMovieTvPeopleEntityLive.value =
            Resource.success(searchWithMovieTvPeopleEntityDummy)

        `when`(mainRepository.getSearchData(querySearch)).thenReturn(
            searchWithMovieTvPeopleEntityLive
        )

        searchViewModel.setQuery(querySearch)

        searchViewModel.getSearchData.observeForever(searchObserver)

        verify(searchObserver).onChanged(Resource.success(searchWithMovieTvPeopleEntityDummy))
    }
}