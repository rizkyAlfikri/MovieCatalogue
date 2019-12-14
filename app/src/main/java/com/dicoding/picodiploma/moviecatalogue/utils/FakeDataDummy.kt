package com.dicoding.picodiploma.moviecatalogue.utils

import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvPopularEntity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MoviePopularResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResult

object FakeDataDummy {
    fun getMovieDummyResult(): List<MoviePopularResult> {
        return listOf(
            MoviePopularResult(
                title = "Joker",
                id = 475557,
                posterPath = "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                genreIds = listOf(80, 18, 53),
                releaseDate = "2019-10-02",
                voteAverage = 8.4
            )
        )
    }

    fun getMovieDummyEntity(): List<MoviePopularEntity> {
        return listOf(
            MoviePopularEntity(
                title = "Joker",
                id = 475557,
                imagePath = "${Config.IMAGE_URL_BASE_PATH}/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                genre = "Crime, Drama, Thriller",
                releaseDate = "2019-10-02",
                voteAverage = 8.4
            )
        )
    }

    fun getMovieGenreDummyResult(): List<MovieGenreResult> {
        return listOf(
            MovieGenreResult(
                80,
                "Crime"
            ),
            MovieGenreResult(
                18,
                "Drama"
            ),
            MovieGenreResult(
                53,
                "Thriller"
            )
        )
    }

    fun getTvDummyResult(): List<TvPopularResult> {
        return listOf(
            TvPopularResult(
                60625,
                "Rick and Morty",
                "/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                listOf(16, 35, 10759, 10765),
                8.6,
                "2013-12-02"
            )
        )
    }

    fun getTvDummyEntity(): List<TvPopularEntity> {
        return listOf(
            TvPopularEntity(
                60625,
                "Rick and Morty",
                "${Config.IMAGE_URL_BASE_PATH}/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                "Animation, Comedy, Action & Adventure, Sci-Fi & Fantasy",
                8.6,
                "2013-12-02"
            )
        )
    }

    fun getTvGenreDummyResult(): List<TvGenreResult> {
        return listOf(
            TvGenreResult(
                16,
                "Animation"
            ),
            TvGenreResult(
                35,
                "Comedy"
            ),
            TvGenreResult(
                10759,
                "Action & Adventure"
            ),
            TvGenreResult(
                10765,
                "Sci-Fi & Fantasy"
            )
        )
    }

    fun getMovieDetailResultDummy() =
        MovieDetailResult(
            listOf(
                MovieGenreResult(
                    80,
                    "Crime"
                ),
                MovieGenreResult(
                    53,
                    "Thriller"
                ),
                MovieGenreResult(
                    18,
                    "Drama"
                )
            ),
            "/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
            "http://www.jokermovie.net/",
            475557,
            "During the 1980s, a failed stand-up comedian is driven insane and turns " +
                    "to a life of crime and chaos in Gotham City while becoming an infamous " +
                    "psychopathic crime figure.",
            "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
            "2019-10-02",
            122,
            "Released",
            "Put on a happy face.",
            "Joker",
            8.4
        )

    fun getMovieDetaiEntityDummy() =  MovieDetailEntity(
        "Crime, Thriller, Drama",
        "${Config.IMAGE_URL_BASE_PATH}/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
        "http://www.jokermovie.net/",
        475557,
        "During the 1980s, a failed stand-up comedian is driven insane and turns " +
                "to a life of crime and chaos in Gotham City while becoming an infamous " +
                "psychopathic crime figure.",
        "${Config.IMAGE_URL_BASE_PATH}/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
        "2019-10-02",
        122,
        "Released",
        "Put on a happy face.",
        "Joker",
        8.4
    )
}