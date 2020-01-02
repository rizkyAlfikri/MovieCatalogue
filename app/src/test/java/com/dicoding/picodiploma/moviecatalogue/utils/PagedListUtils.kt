package com.dicoding.picodiploma.moviecatalogue.utils

import androidx.paging.PagedList
import org.mockito.Mockito.*
import org.mockito.stubbing.Answer

class PagedListUtils {
    companion object{
        fun <T> mockePagedList(list: List<T>): PagedList<T> {
            val pagedList: PagedList<T> = mock(PagedList::class.java) as PagedList<T>
            val answer : Answer<T> = Answer {
                val index: Int = it.arguments as Int
                return@Answer list[index]
            }

            `when`(pagedList[anyInt()]).thenAnswer(answer)
            `when`(pagedList.size).thenReturn(list.size)

            return pagedList
        }
    }
}