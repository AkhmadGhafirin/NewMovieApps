package com.cascer.madesubmission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem

class MainViewModel(
    repository: MainRepository, language: String
) : ViewModel() {

    var movieListLiveData: LiveData<List<MoviesItem>> = MutableLiveData()
    var tvShowListLiveData: LiveData<List<TvShowItem>> = MutableLiveData()

    var favoriteMovieListLiveData: LiveData<List<MoviesItem>> = MutableLiveData()
    var favoriteTvShowListLiveData: LiveData<List<TvShowItem>> = MutableLiveData()

    init {
        repository.insertNowPlayingMovie(language)
        repository.insertNowPlayingTvShow(language)
        movieListLiveData = repository.getMovieList()
        tvShowListLiveData = repository.getTvShowList()
        favoriteMovieListLiveData = repository.getFavoriteMovieList()
        favoriteTvShowListLiveData = repository.getFavoriteTvShowList()
    }
}