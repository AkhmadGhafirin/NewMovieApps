package com.cascer.madesubmission2.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.movie.detail.MovieDetailResponse
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.data.response.tv_show.detail.TvShowDetailResponse

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    fun getNowPlayingMovie(language: String): LiveData<List<MoviesItem>> =
        repository.getNowPlayingMovie(language)

    fun getDetailMovie(id: Int, language: String): LiveData<MovieDetailResponse> =
        repository.getDetailMovie(id, language)

    fun getNowPlayingTvShow(language: String): LiveData<List<TvShowItem>> =
        repository.getNowPlayingTvShow(language)

    fun getDetailTvShow(id: Int, language: String): LiveData<TvShowDetailResponse> =
        repository.getDetailTvShow(id, language)
}