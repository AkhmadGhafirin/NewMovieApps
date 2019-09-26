package com.cascer.madesubmission2.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.data.response.favorite.FavoriteMovie
import com.cascer.madesubmission2.data.response.favorite.FavoriteTvShow
import com.cascer.madesubmission2.data.response.movie.MovieResponse
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.utils.ApiObserver

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var movieListLiveData: LiveData<List<MoviesItem>> = MutableLiveData()
    var tvShowListLiveData: LiveData<List<TvShowItem>> = MutableLiveData()

    var favoriteMovieListLiveData: LiveData<List<FavoriteMovie>> = MutableLiveData()
    var favoriteTvShowListLiveData: LiveData<List<FavoriteTvShow>> = MutableLiveData()

    init {
        movieListLiveData = repository.searchMovieResultLiveData
        tvShowListLiveData = repository.searchTvShowResultLiveData
        favoriteMovieListLiveData = repository.getFavoriteMovieLiveData()
        favoriteTvShowListLiveData = repository.getFavoriteTvShowLiveData()
    }

    fun getFavoriteMovieList(): List<FavoriteMovie> = repository.getFavoriteMovieList()

    fun searchMovie(language: String, query: String) {
        repository.searchMovie(language, query)
    }

    fun searchTvShow(language: String, query: String) {
        repository.searchTvShow(language, query)
    }

    fun getMovieReleasedToday(context: Context?, releaseDate: String) {
        repository.getMovieReleasedToday(context, releaseDate)
    }

    fun getMovieReleasedToday(releaseDate: String, apiObserver: ApiObserver<MovieResponse>) {
        repository.getMovieReleasedToday(releaseDate, apiObserver)
    }
}