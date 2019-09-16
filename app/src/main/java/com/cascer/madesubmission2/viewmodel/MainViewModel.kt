package com.cascer.madesubmission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.movie.detail.MovieDetailResponse
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.data.response.tv_show.detail.TvShowDetailResponse

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var movieListLiveData: LiveData<List<MoviesItem>> = MutableLiveData()
    var tvShowListLiveData: LiveData<List<TvShowItem>> = MutableLiveData()
    var detailMovieLiveData: LiveData<MovieDetailResponse> = MutableLiveData()
    var detailTvShowLiveData: LiveData<TvShowDetailResponse> = MutableLiveData()

    init {
        movieListLiveData = repository.movieListLiveData
        tvShowListLiveData = repository.tvShowListLiveData
        detailMovieLiveData = repository.detailMovieLiveData
        detailTvShowLiveData = repository.detailTvShowLiveData
    }

    fun requestNowPlayingMovie(language: String) {
        repository.getNowPlayingMovie(language)
    }

    fun requestDetailMovie(id: Int, language: String) {
        repository.getDetailMovie(id, language)
    }

    fun requestNowPlayingTvShow(language: String) {
        repository.getNowPlayingTvShow(language)
    }

    fun requestDetailTvShow(id: Int, language: String) {
        repository.getDetailTvShow(id, language)
    }

//    fun getNowPlayingMovie(language: String): LiveData<List<MoviesItem>> =
//        repository.movieListLiveData
//
//    fun getDetailMovie(id: Int, language: String): LiveData<MovieDetailResponse> =
//        repository.getDetailMovie(id, language)
//
//    fun getNowPlayingTvShow(language: String): LiveData<List<TvShowItem>> =
//        repository.getNowPlayingTvShow(language)
//
//    fun getDetailTvShow(id: Int, language: String): LiveData<TvShowDetailResponse> =
//        repository.getDetailTvShow(id, language)
}