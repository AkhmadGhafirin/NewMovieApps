package com.cascer.madesubmission2.data.response.favorite

import android.content.ContentValues
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cascer.madesubmission2.utils.FAVORITE_MOVIE_TABLE_NAME
import kotlinx.android.parcel.Parcelize

@Entity(tableName = FAVORITE_MOVIE_TABLE_NAME)
@Parcelize
data class FavoriteMovie(
    @PrimaryKey
    val id: Int? = null,
    val overview: String? = null,
    @ColumnInfo(name = "original_language")
    val originalLanguage: String? = null,
    @ColumnInfo(name = "original_title")
    val originalTitle: String? = null,
    val video: Boolean? = null,
    val title: String? = null,
    @ColumnInfo(name = "genre_ids")
    val genreIds: List<Int>? = null,
    @ColumnInfo(name = "poster_path")
    val posterPath: String? = null,
    @ColumnInfo(name = "backdrop_path")
    val backdropPath: String? = null,
    @ColumnInfo(name = "release_date")
    val releaseDate: String? = null,
    val popularity: Double? = null,
    @ColumnInfo(name = "vote_average")
    val voteAverage: Double? = null,
    val adult: Boolean? = null,
    @ColumnInfo(name = "vote_count")
    val voteCount: Int? = null
) : Parcelable {

    fun fromContentValues(values: ContentValues?): FavoriteMovie {
        val id = values?.getAsInteger("id")
        val overview = values?.getAsString("overview")
        val title = values?.getAsString("title")
        val posterPath = values?.getAsString("poster_path")
        val backdropPath = values?.getAsString("backdrop_path")
        val releaseDate = values?.getAsString("release_date")
        val popularity = values?.getAsDouble("popularity")
        val voteAverage = values?.getAsDouble("vote_average")
        val voteCount = values?.getAsInteger("vote_count")
        return FavoriteMovie(
            id = id,
            overview = overview,
            originalLanguage = null,
            originalTitle = null,
            video = null,
            title = title,
            genreIds = null,
            posterPath = posterPath,
            backdropPath = backdropPath,
            releaseDate = releaseDate,
            popularity = popularity,
            voteAverage = voteAverage,
            adult = null,
            voteCount = voteCount
        )
    }
}
