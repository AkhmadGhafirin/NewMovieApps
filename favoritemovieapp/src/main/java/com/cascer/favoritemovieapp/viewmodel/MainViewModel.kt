package com.cascer.favoritemovieapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cascer.favoritemovieapp.data.repository.MainRepository
import com.cascer.favoritemovieapp.data.response.detail.MovieDetailResponse

class MainViewModel(repository: MainRepository, movieID: Int) : ViewModel() {

    var detailMovieLiveData: LiveData<MovieDetailResponse> = MutableLiveData()

    init {
        repository.getDetailMovie(movieID)
        detailMovieLiveData = repository.detailMovieLiveData
    }
}