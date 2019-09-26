package com.cascer.favoritemovieapp.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cascer.favoritemovieapp.BuildConfig
import com.cascer.favoritemovieapp.data.network.ApiService
import com.cascer.favoritemovieapp.data.network.ErrorData
import com.cascer.favoritemovieapp.data.response.detail.MovieDetailResponse
import com.cascer.favoritemovieapp.utils.ApiObserver
import com.cascer.favoritemovieapp.utils.PopUpMessage
import com.valdesekamdem.library.mdtoast.MDToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainRepository(private val context: Context, private val apiService: ApiService) {

    private val compositeDisposable = CompositeDisposable()
    val detailMovieLiveData: MutableLiveData<MovieDetailResponse> = MutableLiveData()

    fun getDetailMovie(id: Int) {
        apiService.getMovieDetail(id, BuildConfig.API_KEY, "en-US")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<MovieDetailResponse>(compositeDisposable) {
                override fun onSuccess(data: MovieDetailResponse) {
                    detailMovieLiveData.postValue(data)
                    Log.d("SUCCESS_GET_MOVIE", "Success")
                }

                override fun onError(e: ErrorData) {
                    detailMovieLiveData.postValue(null)
                    Log.d("ERROR_GET_DETAIL_MOVIE", e.message)
                    PopUpMessage().popUpToast(context, e.message, type = MDToast.TYPE_ERROR)
                }
            })
    }
}