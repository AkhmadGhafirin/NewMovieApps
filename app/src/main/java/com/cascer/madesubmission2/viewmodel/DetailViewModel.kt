package com.cascer.madesubmission2.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cascer.madesubmission2.data.repository.MainRepository
import com.cascer.madesubmission2.data.response.favorite.FavoriteMovie
import com.cascer.madesubmission2.data.response.favorite.FavoriteState
import com.cascer.madesubmission2.data.response.favorite.FavoriteTvShow
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

    fun insertFavoriteMovie(item: FavoriteMovie) {
        repository.insertFavoriteMovie(item)
    }

    fun insertFavoriteTvShow(item: FavoriteTvShow) {
        repository.insertFavoriteTvShow(item)
    }

    fun insertFavoriteState(item: FavoriteState) {
        repository.insertFavoriteState(item)
    }

    fun getFavoriteState(id: Int): LiveData<Boolean> = repository.getFavoriteState(id)

    fun deleteFavoriteState() {
        repository.deleteFavoriteState()
    }

    fun deleteFavoriteMovie(id: Int) {
        repository.deleteFavoriteMovie(id)
    }

    fun deleteFavoriteTvShow(id: Int) {
        repository.deleteFavoriteTvShow(id)
    }
}