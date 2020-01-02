package com.dicoding.picodiploma.moviecatalogue.utils

import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviedetailentity.MovieDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.movieimageentity.MovieImageEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviepopularentity.MoviePopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviereviewentity.MovieReviewEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.movieentity.moviesimilarentity.MovieSimilarEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.peopleentity.PeopleEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.searchentity.SearchEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvdetailentity.TvDetailEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvimageentity.TvImageEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvpopularentity.TvPopularEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvreviewentity.TvReviewEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.local.entity.tvshowentity.tvsimilarentity.TvSimilarEntity
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviedetail.MovieDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviegenre.MovieGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movieimages.MovieImageResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movieimages.MoviePosterResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MoviePopularResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviepopular.MovieResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviereviews.MovieReviewResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.moviereviews.MovieReviewResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movievideos.MovieVideoResonse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.movieresponse.movievideos.MovieVideoResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.peopleresponse.PeopleDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.searchresponse.SearchResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowgenre.TvGenreResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowimages.TvBackdropResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowimages.TvImagesResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowreviews.TvReviewResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowreviews.TvReviewResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowvideos.TvVideosResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowvideos.TvVideosResult

object FakeDataDummy {
    fun getMovieDummyResult(): MovieResponse {
        return MovieResponse(
            1
            , listOf(
                MoviePopularResult(
                    title = "Joker",
                    id = 475557,
                    posterPath = "/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                    genreIds = listOf(80, 18, 53),
                    releaseDate = "2019-10-02",
                    voteAverage = 8.4
                )
            )
            , 1
            , 1
        )
    }

    fun getMovieDummyEntity(): List<MoviePopularEntity> {
        return listOf(
            MoviePopularEntity(
                idMovie = 475557,
                title = "Joker",
                id = null,
                imagePath = "${Config.IMAGE_URL_BASE_PATH}/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
                genre = "Crime, Drama, Thriller",
                releaseDate = "2019-10-02",
                voteAverage = 8.4,
                isFavorite = true
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

    fun getMovieImageResultDummy() =
        MovieImageResponse(475557, listOf(MoviePosterResult("/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg")))

    fun getMovieReviewResultDummy() = MovieReviewResponse(
        475557, 1, listOf(
            MovieReviewResult(
                "Wuchak",
                "*A Masterpiece*.\r\n\r\nThe movie shows the escalating events that made Arthur become the Joker. Initially an inoffensive poor and sick man, Arthur suffered a tide of unfortunate events that pushed him closer and closer to the edge. \r\n\r\nIgnored and despised by everyone, sick and alone in the world, and neglected by the State, Arthur becomes progressively more violent until he breaks.\r\n\r\nMuch more than one more Super-hero movie, *Joker* uses well-known characters to promote the reflection on the \"ignored\" ones. At least, ignored until they become a Joker.",
                "5df59eaad1a89300197854b5",
                "https://www.themoviedb.org/review/5df59eaad1a89300197854b5"
            )
        ), 1, 1
    )

    fun getMovieVideoResultDummy() =
        MovieVideoResonse(475557, listOf(MovieVideoResult("t433PEQGErc")))

    fun getMovieImageEntity() = MovieImageEntity(
        null,
        475557,
        "${Config.IMAGE_URL_BASE_PATH}/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg"
    )

    fun getMovieReviewEntity() = MovieReviewEntity(
        null, 475557,
        "Wuchak",
        "*A Masterpiece*.\r\n\r\nThe movie shows the escalating events that made Arthur become the Joker. Initially an inoffensive poor and sick man, Arthur suffered a tide of unfortunate events that pushed him closer and closer to the edge. \r\n\r\nIgnored and despised by everyone, sick and alone in the world, and neglected by the State, Arthur becomes progressively more violent until he breaks.\r\n\r\nMuch more than one more Super-hero movie, *Joker* uses well-known characters to promote the reflection on the \"ignored\" ones. At least, ignored until they become a Joker.",
        "5df59eaad1a89300197854b5",
        "https://www.themoviedb.org/review/5df59eaad1a89300197854b5"
    )

    fun getMovieSimilarEntity() = MovieSimilarEntity(
        null,
        475557,
        "${Config.IMAGE_URL_BASE_PATH}/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
        "Joker"
    )


    fun getTvDummyResult(): TvPopularResponse {
        return TvPopularResponse(
            1
            , listOf(
                TvPopularResult(
                    60625
                    , "Rick and Morty"
                    , "/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg"
                    , listOf(16, 35, 10759, 10765)
                    , 8.6
                    , "2013-12-02"
                )
            )
            , 1
            , 1
        )
    }

    fun getTvDummyEntity(): List<TvPopularEntity> {
        return listOf(
            TvPopularEntity(
                null,
                60625,
                "Rick and Morty",
                "${Config.IMAGE_URL_BASE_PATH}/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
                "Animation, Comedy, Action & Adventure, Sci-Fi & Fantasy",
                8.6,
                "2013-12-02",
                true
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
            8.4,
            ""
        )

    fun getMovieDetailEntityDummy() = MovieDetailEntity(
        "Crime, Thriller, Drama",
        "${Config.IMAGE_URL_BASE_PATH}/n6bUvigpRFqSwmPp1m2YADdbRBc.jpg",
        "http://www.jokermovie.net/",
        475557,
        "During the 1980s, a failed stand-up comedian is driven insane and turns " +
                "to a life of crime and chaos in Gotham City while becoming an infamous " +
                "psychopathic crime figure.",
        "${Config.IMAGE_URL_BASE_PATH}/udDclJoHjfjb8Ekgsd4FDteOkCU.jpg",
        "02-Oct-2019",
        122,
        "Released",
        "Put on a happy face.",
        "Joker",
        8.4,
        ""
    )

    fun getTvDetailResultDummy() = TvDetailResult(
        "/mzzHr6g1yvZ05Mc7hNj3tUdy2bM.jpg",
        "2013-12-02",
        listOf(
            TvGenreResult(
                10765,
                "Sci-Fi & Fantasy"
            ),
            TvGenreResult(
                10759,
                "Action & Adventure"
            ),
            TvGenreResult(
                16,
                "Animation"
            ),
            TvGenreResult(
                35,
                "Comedy"
            )
        ),
        "http://www.adultswim.com/videos/rick-and-morty",
        listOf(22),
        60625,
        "Rick and Morty",
        "Rick is a mentally-unbalanced but scientifically-gifted old man who has " +
                "recently reconnected with his family. He spends most of his time involving " +
                "his young grandson Morty in dangerous, outlandish adventures throughout " +
                "space and alternate universes. Compounded with Morty's already unstable " +
                "family life, these events cause Morty much distress at home and school.",
        "/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
        "Returning Series",
        8.6
    )

    fun getTvDetailEntity() = TvDetailEntity(
        genres = "Sci-Fi & Fantasy, Action & Adventure, Animation, Comedy",
        backdropPath = "${Config.IMAGE_URL_BASE_PATH}/mzzHr6g1yvZ05Mc7hNj3tUdy2bM.jpg",
        homepage = "http://www.adultswim.com/videos/rick-and-morty",
        id = 60625,
        overview = "Rick is a mentally-unbalanced but scientifically-gifted old man who has recently reconnected with his family. He spends most of his time involving his young grandson Morty in dangerous, outlandish adventures throughout space and alternate universes. Compounded with Morty's already unstable family life, these events cause Morty much distress at home and school.",
        imagePath = "${Config.IMAGE_URL_BASE_PATH}/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg",
        releaseDate = "2013-12-02",
        runtime = 22,
        status = "Returning Series",
        title = "Rick and Morty",
        voteAverage = 8.6,
        keyVideo = null
    )

    fun getTvImageResult() =
        TvImagesResponse(listOf(TvBackdropResult("/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg")), 60625)

    fun getTvReviewResult() = TvReviewResponse(
        60625, 1, listOf(
            TvReviewResult(
                "Wuchak",
                "*A Masterpiece*.\r\n\r\nThe movie shows the escalating events that made Arthur become the Joker. Initially an inoffensive poor and sick man, Arthur suffered a tide of unfortunate events that pushed him closer and closer to the edge. \r\n\r\nIgnored and despised by everyone, sick and alone in the world, and neglected by the State, Arthur becomes progressively more violent until he breaks.\r\n\r\nMuch more than one more Super-hero movie, *Joker* uses well-known characters to promote the reflection on the \"ignored\" ones. At least, ignored until they become a Joker.",
                "5df59eaad1a89300197854b5",
                "https://www.themoviedb.org/review/5df59eaad1a89300197854b5"
            )
        ), 1, 1
    )

    fun getTvVideoResult() = TvVideosResponse(60625, listOf(TvVideosResult("sLaXyz4SDtU")))

    fun getTvImageEntity() =
        TvImageEntity(null, 60625, "${Config.IMAGE_URL_BASE_PATH}/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg")

    fun getTvReviewEntity() = TvReviewEntity(
        null,
        60625,
        "Wuchak",
        "*A Masterpiece*.\r\n\r\nThe movie shows the escalating events that made Arthur become the Joker. Initially an inoffensive poor and sick man, Arthur suffered a tide of unfortunate events that pushed him closer and closer to the edge. \r\n\r\nIgnored and despised by everyone, sick and alone in the world, and neglected by the State, Arthur becomes progressively more violent until he breaks.\r\n\r\nMuch more than one more Super-hero movie, *Joker* uses well-known characters to promote the reflection on the \"ignored\" ones. At least, ignored until they become a Joker.",
        "5df59eaad1a89300197854b5",
        "https://www.themoviedb.org/review/5df59eaad1a89300197854b5"
    )

    fun getTvSimilarEntity() = TvSimilarEntity(
        null,
        60625,
        "Rick and Morty",
        "${Config.IMAGE_URL_BASE_PATH}/qJdfO3ahgAMf2rcmhoqngjBBZW1.jpg"
    )

    fun getSearchMovieTv() = SearchResponse(
        1, listOf(
            SearchResult(
                "2016-08-26"
                , listOf(
                    16,
                    18,
                    10749
                )
                , 372058
                , "372058"
                , "Kana Hanazawa"
                , "/xq1Ugd62d23K2knRUx6xxuALTZB.jpg"
                , "/ltGMeYsNJnx89wi9JVv1WDH72R3.jpg"
                , "2016-08-26"
                , "Your Name."
                , 8.5
            )
        )
        , 1
        , 1
    )

    fun getPeopleDetailResult() = PeopleDetailResult(
        "Kana Hanazawa is a Japanese voice actress affiliated with the Office Osawa talent agency. In 2012 she made her debut as a singer."
        , "1989-02-25"
        , null
        , 1
        , "http://www.hanazawakana-music.net"
        , 372058
        , "Kana Hanazawa"
        , "Tokyo Prefecture, Japan"
        , "/ltGMeYsNJnx89wi9JVv1WDH72R3.jpg"
    )

    fun getSearchEntity() = SearchEntity(
        null
        , 119143
        , "person"
    )

    fun getPeopleDetailEntity() = PeopleDetailEntity(
        null,
        "Kana Hanazawa is a Japanese voice actress affiliated with the Office Osawa talent agency. In 2012 she made her debut as a singer."
        , "Sat, 25-Feb-1989"
        , "Female"
        , "http://www.hanazawakana-music.net"
        , 372058
        , "Kana Hanazawa"
        , "Tokyo Prefecture, Japan"
        , "${Config.IMAGE_URL_BASE_PATH}/ltGMeYsNJnx89wi9JVv1WDH72R3.jpg"
    )

    fun getPeopleEntity() = PeopleEntity(
        null
        , 372058
        , "Kana Hanazawa"
        , "${Config.IMAGE_URL_BASE_PATH}/ltGMeYsNJnx89wi9JVv1WDH72R3.jpg"
    )

}