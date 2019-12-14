package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse

import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MoviePopularResult

data class MovieResultWithGenre(
    val movieResult: List<MoviePopularResult>,
    val movieGenreResult: List<MovieGenreResult>
)