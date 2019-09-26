package com.cascer.madesubmission2.data.response.movie

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MoviesItem(
    val overview: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("original_title")
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int>? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("release_date")
    val releaseDate: String? = null,
    val popularity: Double? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    val id: Int? = null,
    val adult: Boolean? = null,
    @SerializedName("vote_count")
    val voteCount: Int? = null
) : Parcelable
