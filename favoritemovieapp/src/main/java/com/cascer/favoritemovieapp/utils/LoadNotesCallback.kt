package com.cascer.favoritemovieapp.utils

import android.database.Cursor

interface LoadNotesCallback {
    fun postExecute(cursor: Cursor)
}
