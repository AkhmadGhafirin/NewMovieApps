package com.cascer.madesubmission2.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.cascer.madesubmission2.data.response.favorite.FavoriteMovie
import com.cascer.madesubmission2.data.response.favorite.FavoriteState
import com.cascer.madesubmission2.data.response.favorite.FavoriteTvShow
import com.cascer.madesubmission2.utils.FAVORITE_MOVIE_TABLE_NAME
import com.cascer.madesubmission2.utils.FAVORITE_TV_SHOW_TABLE_NAME


@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteMovie(item: FavoriteMovie)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteTvShow(item: FavoriteTvShow)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteState(item: FavoriteState)

    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovieLiveData(): LiveData<List<FavoriteMovie>>

    @Query("SELECT * FROM favorite_movie")
    fun getFavoriteMovieList(): List<FavoriteMovie>

    @Query("SELECT * FROM favorite_tv_show")
    fun getFavoriteTvShowLiveData(): LiveData<List<FavoriteTvShow>>

    @Query("SELECT is_favorite FROM favorite_state WHERE id = :id LIMIT 1")
    fun getFavoriteState(id: Int): LiveData<Boolean>

    @Query("DELETE FROM favorite_state WHERE is_favorite == 0")
    fun deleteFavoriteState()

    @Query("DELETE FROM favorite_movie WHERE id == :id")
    fun deleteFavoriteMovie(id: Int)

    @Query("DELETE FROM favorite_tv_show WHERE id == :id")
    fun deleteFavoriteTvShow(id: Int)

    /**
     * For Content Provider
     */

    @Insert
    fun insertFavoriteMovieProvider(movie: FavoriteMovie): Long

    @Query("SELECT * FROM $FAVORITE_MOVIE_TABLE_NAME")
    fun selectAllFavoriteMovieProvider(): Cursor

    @Query("SELECT * FROM $FAVORITE_MOVIE_TABLE_NAME WHERE id = :id")
    fun selectFavoriteMovieProviderById(id: Long): Cursor

    @Query("DELETE FROM $FAVORITE_MOVIE_TABLE_NAME WHERE id = :id")
    fun deleteFavoriteMovieProviderById(id: Long): Int

    @Update
    fun updateFavoriteMovieProvider(movie: FavoriteMovie): Int


    @Insert
    fun insertFavoriteTvShowProvider(tvShow: FavoriteTvShow): Long

    @Query("SELECT * FROM $FAVORITE_TV_SHOW_TABLE_NAME")
    fun selectAllFavoriteTvShowProvider(): Cursor

    @Query("SELECT * FROM $FAVORITE_TV_SHOW_TABLE_NAME WHERE id = :id")
    fun selectFavoriteTvShowProvider(id: Long): Cursor

    @Query("DELETE FROM $FAVORITE_TV_SHOW_TABLE_NAME WHERE id = :id")
    fun deleteFavoriteTvShowProviderById(id: Long): Int

    @Update
    fun updateFavoriteTvShowProvider(tvShow: FavoriteTvShow): Int
}