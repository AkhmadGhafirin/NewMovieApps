package com.cascer.madesubmission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.data.response.movie.detail.MovieDetailResponse
import com.cascer.madesubmission2.data.response.tv_show.detail.TvShowDetailResponse

class DetailViewModel(
    private val repository: MainRepository, from: String, movieID: Int, language: String
) : ViewModel() {

    var detailMovieLiveData: LiveData<MovieDetailResponse> = MutableLiveData()
    var detailTvShowLiveData: LiveData<TvShowDetailResponse> = MutableLiveData()

    init {
        if (from == "Movie") {
            repository.getDetailMovie(movieID, language)
        } else {
            repository.getDetailTvShow(movieID, language)
        }
        detailMovieLiveData = repository.detailMovieLiveData
        detailTvShowLiveData = repository.detailTvShowLiveData
    }

    fun updateFavoriteMovie(favorite: Boolean, id: Int) {
        repository.updateFavoriteMovie(favorite, id)
    }

    fun updateFavoriteTvShow(favorite: Boolean, id: Int) {
        repository.updateFavoriteTvShow(favorite, id)
    }

    fun getFavoriteMovie(id: Int): LiveData<Boolean> = repository.getFavoriteMovie(id)

    fun getFavoriteTvShow(id: Int): LiveData<Boolean> = repository.getFavoriteTvShow(id)
}