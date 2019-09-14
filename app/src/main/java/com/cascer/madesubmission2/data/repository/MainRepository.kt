package com.cascer.madesubmission2.data.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cascer.madesubmission2.BuildConfig
import com.cascer.madesubmission2.data.network.ApiService
import com.cascer.madesubmission2.data.network.ErrorData
import com.cascer.madesubmission2.data.response.movie.MovieResponse
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.movie.detail.MovieDetailResponse
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.data.response.tv_show.TvShowResponse
import com.cascer.madesubmission2.data.response.tv_show.detail.TvShowDetailResponse
import com.cascer.madesubmission2.utils.ApiObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainRepository(private val context: Context, private val apiService: ApiService) {

    private val compositeDisposable = CompositeDisposable()

    fun getNowPlayingMovie(language: String): LiveData<List<MoviesItem>> {
        val result: MutableLiveData<List<MoviesItem>> = MutableLiveData()
        apiService.getNowPlayingMovie(BuildConfig.API_KEY, 1, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<MovieResponse>(compositeDisposable) {
                override fun onSuccess(data: MovieResponse) {
                    result.postValue(data.results)
                    Log.d("SUCCESS_GET_MOVIE", "Success")
                }

                override fun onError(e: ErrorData) {
                    result.postValue(null)
                    Log.d("ERROR_GET_MOVIE", e.message)
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            })
        return result
    }

    fun getDetailMovie(id: Int, language: String): LiveData<MovieDetailResponse> {
        val result: MutableLiveData<MovieDetailResponse> = MutableLiveData()
        apiService.getMovieDetail(id, BuildConfig.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<MovieDetailResponse>(compositeDisposable) {
                override fun onSuccess(data: MovieDetailResponse) {
                    result.postValue(data)
                    Log.d("SUCCESS_GET_MOVIE", "Success")
                }

                override fun onError(e: ErrorData) {
                    result.postValue(null)
                    Log.d("ERROR_GET_DETAIL_MOVIE", e.message)
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            })
        return result
    }

    fun getNowPlayingTvShow(language: String): LiveData<List<TvShowItem>> {
        val result: MutableLiveData<List<TvShowItem>> = MutableLiveData()
        apiService.getNowPlayingTvShow(BuildConfig.API_KEY, 1, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<TvShowResponse>(compositeDisposable) {
                override fun onSuccess(data: TvShowResponse) {
                    result.postValue(data.results)
                    Log.d("SUCCESS_GET_TV_SHOW", "Success")
                }

                override fun onError(e: ErrorData) {
                    result.postValue(null)
                    Log.d("ERROR_GET_TV_SHOW", e.message)
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            })
        return result
    }

    fun getDetailTvShow(id: Int, language: String): LiveData<TvShowDetailResponse> {
        val result: MutableLiveData<TvShowDetailResponse> = MutableLiveData()
        apiService.getTvShowDetail(id, BuildConfig.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<TvShowDetailResponse>(compositeDisposable) {
                override fun onSuccess(data: TvShowDetailResponse) {
                    result.postValue(data)
                    Log.d("SUCCESS_GET_TV_SHOW", "Success")
                }

                override fun onError(e: ErrorData) {
                    result.postValue(null)
                    Log.d("ERROR_GET_TV_SHOW", e.message)
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            })
        return result
    }
}