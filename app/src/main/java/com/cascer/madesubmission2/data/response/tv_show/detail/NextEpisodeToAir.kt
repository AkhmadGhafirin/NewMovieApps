package com.cascer.madesubmission2.data.response.tv_show.detail

import com.google.gson.annotations.SerializedName

data class NextEpisodeToAir(

	@field:SerializedName("production_code")
	val productionCode: String? = null,

	@field:SerializedName("air_date")
	val airDate: String? = null,

	@field:SerializedName("overview")
	val overview: String? = null,

	@field:SerializedName("episode_number")
	val episodeNumber: Int? = null,

	@field:SerializedName("show_id")
	val showId: Int? = null,

	@field:SerializedName("vote_average")
	val voteAverage: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("season_number")
	val seasonNumber: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("still_path")
	val stillPath: Any? = null,

	@field:SerializedName("vote_count")
	val voteCount: Int? = null
)