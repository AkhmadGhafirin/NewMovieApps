package com.cascer.favoritemovieapp.data.response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FavoriteMovie(
    val id: Int? = null,
    val title: String? = null,
    val posterPath: String? = null,
    val voteAverage: Double? = null
) : Parcelable
