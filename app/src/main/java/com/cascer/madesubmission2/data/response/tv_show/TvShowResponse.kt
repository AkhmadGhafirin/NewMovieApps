package com.cascer.madesubmission2.data.response.tv_show

import com.google.gson.annotations.SerializedName

data class TvShowResponse(
    val page: Int? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    val results: List<TvShowItem>? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
)
