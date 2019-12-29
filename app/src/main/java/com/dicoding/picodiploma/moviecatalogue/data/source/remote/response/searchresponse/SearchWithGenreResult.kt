package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse

import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult

data class SearchWithGenreResult(
    val listSearchResult: List<SearchResult>,
    val listMovieGenre: List<MovieGenreResult>,
    val listTvGenre: List<TvGenreResult>
    )