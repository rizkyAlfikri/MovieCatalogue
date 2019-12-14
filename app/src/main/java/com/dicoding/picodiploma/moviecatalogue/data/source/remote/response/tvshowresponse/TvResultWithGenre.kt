package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse

import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResult

data class TvResultWithGenre(
    val listTvShow: List<TvPopularResult>,
    val listTvGenre: List<TvGenreResult>
)