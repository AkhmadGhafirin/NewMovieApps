package com.cascer.madesubmission2.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cascer.madesubmission2.data.response.favorite.FavoriteMovie
import com.cascer.madesubmission2.data.response.favorite.FavoriteState
import com.cascer.madesubmission2.data.response.favorite.FavoriteTvShow

@Database(
    entities = [FavoriteMovie::class, FavoriteTvShow::class, FavoriteState::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class MainDB : RoomDatabase() {

    abstract fun dao(): MainDao

    companion object {

        private var INSTANCE: MainDB? = null

        fun getDatabase(context: Context): MainDB {
            val currentInstance = INSTANCE
            if (currentInstance != null) {
                return currentInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDB::class.java,
                    "main_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}