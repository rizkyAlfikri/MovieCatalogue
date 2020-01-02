package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse

import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movieimages.MovieImageResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MoviePopularResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviereviews.MovieReviewResponse

data class MovieDetailWithInfoResult(
    var movieDetailResult: MovieDetailResult,

    var listMovieReviewResult: MovieReviewResponse,

    var listMovieImageResult: MovieImageResponse,

    var listMovieSimilarResult: List<MoviePopularResult>
)