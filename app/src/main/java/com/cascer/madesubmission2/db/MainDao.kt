package com.cascer.madesubmission2.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem

@Dao
interface MainDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovieList(list: List<MoviesItem>)

    @Query("SELECT * FROM movies")
    fun getMovieList(): LiveData<List<MoviesItem>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertTvShowList(list: List<TvShowItem>)

    @Query("SELECT * FROM tv_show")
    fun getTvShowList(): LiveData<List<TvShowItem>>

    @Query("SELECT * FROM movies WHERE isFavorite == 1")
    fun getFavoriteMovieList(): LiveData<List<MoviesItem>>

    @Query("SELECT * FROM tv_show WHERE isFavorite == 1")
    fun getFavoriteTvShowList(): LiveData<List<TvShowItem>>

    @Query("SELECT isFavorite FROM movies WHERE id = :id LIMIT 1")
    fun getFavoriteMovie(id: Int): LiveData<Boolean>

    @Query("SELECT isFavorite FROM tv_show WHERE isFavorite = :id LIMIT 1")
    fun getFavoriteTvShow(id: Int): LiveData<Boolean>

    @Query("UPDATE movies SET isFavorite = :favorite WHERE id = :id")
    fun updateFavoriteMovie(favorite: Boolean, id: Int)

    @Query("UPDATE tv_show SET isFavorite = :favorite WHERE id = :id")
    fun updateFavoriteTvShow(favorite: Boolean, id: Int)
}