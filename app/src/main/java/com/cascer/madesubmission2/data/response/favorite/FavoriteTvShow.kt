package com.cascer.madesubmission2.data.response.favorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cascer.madesubmission2.utils.FAVORITE_TV_SHOW_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = FAVORITE_TV_SHOW_TABLE_NAME)
@Parcelize
data class FavoriteTvShow(
    @ColumnInfo(name = "first_air_date")
    val firstAirDate: String? = null,
    val overview: String? = null,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int?>? = null,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "origin_country")
    val originCountry: List<String>? = null,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,
    @ColumnInfo(name = "original_name")
    val originalName: String? = null,
    val popularity: Double? = null,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = null,
    val name: String? = null,
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null
) : Parcelable