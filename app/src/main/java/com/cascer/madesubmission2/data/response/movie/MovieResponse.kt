package com.cascer.madesubmission2.data.response.movie

import com.google.gson.annotations.SerializedName

data class MovieResponse(
	val dates: Dates? = null,
	val page: Int? = null,
	@SerializedName("total_pages")
	val totalPages: Int? = null,
	val results: List<MoviesItem>? = null,
	@SerializedName("total_results")
	val totalResults: Int? = null
)
