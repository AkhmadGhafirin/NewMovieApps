package com.cascer.madesubmission2.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.cascer.madesubmission2.data.response.favorite.FavoriteMovie
import com.cascer.madesubmission2.db.MainDao
import com.cascer.madesubmission2.utils.AUTHORITY_PROVIDER
import com.cascer.madesubmission2.utils.CODE_MOVIE_DIR
import com.cascer.madesubmission2.utils.CODE_MOVIE_ITEM
import com.cascer.madesubmission2.utils.FAVORITE_MOVIE_TABLE_NAME
import org.koin.android.ext.android.inject


class FavoriteProvider : ContentProvider() {

    private val mainDao: MainDao by inject()

    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init {
        uriMatcher.addURI(AUTHORITY_PROVIDER, FAVORITE_MOVIE_TABLE_NAME, CODE_MOVIE_DIR)
        uriMatcher.addURI(AUTHORITY_PROVIDER, "$FAVORITE_MOVIE_TABLE_NAME/*", CODE_MOVIE_ITEM)
    }

    override fun onCreate(): Boolean {
        return true
    }

    override fun query(
        uri: Uri, projection: Array<out String>?, selection: String?,
        selectionArgs: Array<out String>?, SortOrder: String?
    ): Cursor? {
        val code = uriMatcher.match(uri)
        if (code == CODE_MOVIE_DIR || code == CODE_MOVIE_ITEM) {
            val context = context ?: return null
            val cursor: Cursor = if (code == CODE_MOVIE_DIR) {
                mainDao.selectAllFavoriteMovieProvider()
            } else {
                mainDao.selectFavoriteMovieProviderById(ContentUris.parseId(uri))
            }
            cursor.setNotificationUri(context.contentResolver, uri)
            return cursor
        } else {
            throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun insert(uri: Uri, contentValues: ContentValues?): Uri? {
        when (uriMatcher.match(uri)) {
            CODE_MOVIE_DIR -> {
                val context = context ?: return null
                val id = mainDao
                    .insertFavoriteMovieProvider(FavoriteMovie().fromContentValues(contentValues))
                context.contentResolver.notifyChange(uri, null)
                return ContentUris.withAppendedId(uri, id)
            }
            CODE_MOVIE_ITEM -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }


    override fun update(
        uri: Uri, contentValues: ContentValues?, selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        when (uriMatcher.match(uri)) {
            CODE_MOVIE_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_MOVIE_ITEM -> {
                val context = context ?: return 0
                val movie = FavoriteMovie().fromContentValues(contentValues)
                val count = mainDao
                    .updateFavoriteMovieProvider(movie)
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        when (uriMatcher.match(uri)) {
            CODE_MOVIE_DIR -> throw IllegalArgumentException("Invalid URI, cannot update without ID$uri")
            CODE_MOVIE_ITEM -> {
                val context = context ?: return 0
                val count = mainDao
                    .deleteFavoriteMovieProviderById(ContentUris.parseId(uri))
                context.contentResolver.notifyChange(uri, null)
                return count
            }
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            CODE_MOVIE_DIR -> "vnd.android.cursor.dir/$AUTHORITY_PROVIDER.$FAVORITE_MOVIE_TABLE_NAME"
            CODE_MOVIE_ITEM -> "vnd.android.cursor.item/$AUTHORITY_PROVIDER.$FAVORITE_MOVIE_TABLE_NAME"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }
}