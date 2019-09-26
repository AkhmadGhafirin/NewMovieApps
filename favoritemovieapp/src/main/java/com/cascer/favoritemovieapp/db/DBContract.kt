package com.cascer.favoritemovieapp.db

import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import com.cascer.favoritemovieapp.utils.AUTHORITY_PROVIDER
import com.cascer.favoritemovieapp.utils.SCHEME_PROVIDER


class DBContract {

    class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_NAME = "favorite_movie"
        }
    }

    val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME_PROVIDER)
        .authority(AUTHORITY_PROVIDER)
        .appendPath(MovieColumns.TABLE_NAME)
        .build()

    fun getColumnString(cursor: Cursor, columnName: String): String {
        return cursor.getString(cursor.getColumnIndex(columnName))
    }

    fun getColumnInt(cursor: Cursor, columnName: String): Int {
        return cursor.getInt(cursor.getColumnIndex(columnName))
    }

    fun getColumnLong(cursor: Cursor, columnName: String): Long {
        return cursor.getLong(cursor.getColumnIndex(columnName))
    }
}