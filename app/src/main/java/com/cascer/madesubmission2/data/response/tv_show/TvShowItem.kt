package com.cascer.madesubmission2.data.response.tv_show

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "tv_show")
@Parcelize
data class TvShowItem(
    @SerializedName("first_air_date")
    val firstAirDate: String? = null,
    val overview: String? = null,
    @SerializedName("original_language")
    val originalLanguage: String? = null,
    @SerializedName("genre_ids")
    val genreIds: List<Int?>? = null,
    @SerializedName("poster_path")
    val posterPath: String? = null,
    @SerializedName("origin_country")
    val originCountry: List<String>? = null,
    @SerializedName("backdrop_path")
    val backdropPath: String? = null,
    @SerializedName("original_name")
    val originalName: String? = null,
    val popularity: Double? = null,
    @SerializedName("vote_average")
    val voteAverage: Double? = null,
    val name: String? = null,
    @PrimaryKey
    val id: Int? = null,
    val isFavorite: Boolean? = false,
    @SerializedName("vote_count")
    val voteCount: Int? = null
) : Parcelable