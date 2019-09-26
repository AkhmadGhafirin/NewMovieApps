package com.cascer.madesubmission2.data.response.favorite

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "favorite_state")
@Parcelize
data class FavoriteState(
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false
) : Parcelable
