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
import com.cascer.madesubmission2.db.MainDao
import com.cascer.madesubmission2.utils.ApiObserver
import com.cascer.madesubmission2.utils.PopUpMessage
import com.valdesekamdem.library.mdtoast.MDToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.Executor

class MainRepository(
    private val context: Context, private val dao: MainDao,
    private val apiService: ApiService, private val executor: Executor
) {

    private val compositeDisposable = CompositeDisposable()
    val detailMovieLiveData: MutableLiveData<MovieDetailResponse> = MutableLiveData()
    val detailTvShowLiveData: MutableLiveData<TvShowDetailResponse> = MutableLiveData()

    fun getMovieList(): LiveData<List<MoviesItem>> = dao.getMovieList()

    fun getTvShowList(): LiveData<List<TvShowItem>> = dao.getTvShowList()

    fun getFavoriteMovieList(): LiveData<List<MoviesItem>> = dao.getFavoriteMovieList()

    fun getFavoriteTvShowList(): LiveData<List<TvShowItem>> = dao.getFavoriteTvShowList()

    fun updateFavoriteMovie(favorite: Boolean, id: Int) {
        executor.execute { dao.updateFavoriteMovie(favorite, id) }
    }

    fun updateFavoriteTvShow(favorite: Boolean, id: Int) {
        Executor { dao.updateFavoriteTvShow(favorite, id) }
    }

    fun getFavoriteMovie(id: Int): LiveData<Boolean> = dao.getFavoriteMovie(id)

    fun getFavoriteTvShow(id: Int): LiveData<Boolean> = dao.getFavoriteTvShow(id)

    fun insertNowPlayingMovie(language: String) {
        apiService.getNowPlayingMovie(BuildConfig.API_KEY, 1, language)
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<MovieResponse>(compositeDisposable) {
                override fun onSuccess(data: MovieResponse) {
                    dao.insertMovieList(data.results ?: emptyList())
                    Log.d("SUCCESS_GET_MOVIE", "Success")
                }

                override fun onError(e: ErrorData) {
                    Log.d("ERROR_GET_MOVIE", e.message)
                    PopUpMessage().popUpToast(context, e.message, type = MDToast.TYPE_ERROR)
                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
                }
            })
    }

    fun getDetailMovie(id: Int, language: String) {
        apiService.getMovieDetail(id, BuildConfig.API_KEY, language)
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

    fun insertNowPlayingTvShow(language: String) {
        apiService.getNowPlayingTvShow(BuildConfig.API_KEY, 1, language)
            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<TvShowResponse>(compositeDisposable) {
                override fun onSuccess(data: TvShowResponse) {
                    dao.insertTvShowList(data.results ?: emptyList())
//                    Log.d("SUCCESS_GET_TV_SHOW", "Success")
                    Log.d("SUCCESS_GET_TV_SHOW", "Success")
                }

                override fun onError(e: ErrorData) {
                    Log.d("ERROR_GET_TV_SHOW", e.message)
                    PopUpMessage().popUpToast(context, e.message, type = MDToast.TYPE_ERROR)
                }
            })
    }

    fun getDetailTvShow(id: Int, language: String) {
        apiService.getTvShowDetail(id, BuildConfig.API_KEY, language)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : ApiObserver<TvShowDetailResponse>(compositeDisposable) {
                override fun onSuccess(data: TvShowDetailResponse) {
                    detailTvShowLiveData.postValue(data)
                    Log.d("SUCCESS_GET_TV_SHOW", "Success")
                }

                override fun onError(e: ErrorData) {
                    detailTvShowLiveData.postValue(null)
                    Log.d("ERROR_GET_TV_SHOW", e.message)
                    PopUpMessage().popUpToast(context, e.message, type = MDToast.TYPE_ERROR)
                }
            })
    }
}