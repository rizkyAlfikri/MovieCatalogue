package com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse

import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowdetail.TvDetailResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowimages.TvImagesResponse
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowpopular.TvPopularResult
import com.dicoding.picodiploma.moviecatalogue.data.source.remote.response.tvshowresponse.tvshowreviews.TvReviewResponse

data class TvDetailWithInfoResult(
    var tvShowDetail: TvDetailResult,

    var listTvReview: TvReviewResponse,

    var listTvImage: TvImagesResponse,

    var listTvSimilar: List<TvPopularResult>
)