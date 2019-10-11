package com.cascer.madesubmission2.data.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cascer.madesubmission2.BuildConfig
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.network.ApiService
import com.cascer.madesubmission2.data.network.ErrorData
import com.cascer.madesubmission2.data.response.favorite.FavoriteMovie
import com.cascer.madesubmission2.data.response.favorite.FavoriteState
import com.cascer.madesubmission2.data.response.favorite.FavoriteTvShow
import com.cascer.madesubmission2.data.response.movie.MovieResponse
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.data.response.movie.detail.MovieDetailResponse
import com.cascer.madesubmission2.data.response.tv_show.TvShowItem
import com.cascer.madesubmission2.data.response.tv_show.TvShowResponse
import com.cascer.madesubmission2.data.response.tv_show.detail.TvShowDetailResponse
import com.cascer.madesubmission2.db.MainDao
import com.cascer.madesubmission2.utils.ApiObserver
import com.cascer.madesubmission2.utils.NotificationHelper
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
    val searchMovieResultLiveData: MutableLiveData<List<MoviesItem>> = MutableLiveData()
    val searchTvShowResultLiveData: MutableLiveData<List<TvShowItem>> = MutableLiveData()
    val detailMovieLiveData: MutableLiveData<MovieDetailResponse> = MutableLiveData()
    val detailTvShowLiveData: MutableLiveData<TvShowDetailResponse> = MutableLiveData()

    fun insertFavoriteMovie(item: FavoriteMovie) {
        executor.execute { dao.insertFavoriteMovie(item) }
    }

    fun insertFavoriteTvShow(item: FavoriteTvShow) {
        executor.execute { dao.insertFavoriteTvShow(item) }
    }

    fun insertFavoriteState(item: FavoriteState) {
        executor.execute { dao.insertFavoriteState(item) }
    }

    fun deleteFavoriteState() {
        executor.execute { dao.deleteFavoriteState() }
    }

    fun deleteFavoriteMovie(id: Int) {
        executor.execute { dao.deleteFavoriteMovie(id) }
    }

    fun deleteFavoriteTvShow(id: Int) {
        executor.execute { dao.deleteFavoriteTvShow(id) }
    }

    fun getFavoriteMovieLiveData(): LiveData<List<FavoriteMovie>> = dao.getFavoriteMovieLiveData()

    fun getFavoriteMovieList(): List<FavoriteMovie> = dao.getFavoriteMovieList()

    fun getFavoriteTvShowLiveData(): LiveData<List<FavoriteTvShow>> =
        dao.getFavoriteTvShowLiveData()

    fun getFavoriteState(id: Int): LiveData<Boolean> = dao.getFavoriteState(id)

    fun searchMovie(language: String, query: String) {
        apiService.getNowPlayingMovie(BuildConfig.API_KEY, language, query)
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<MovieResponse>(compositeDisposable) {
                override fun onSuccess(data: MovieResponse) {
                    searchMovieResultLiveData.postValue(data.results)
                    Log.d("SUCCESS_GET_MOVIE", "Success")
                }

                override fun onError(e: ErrorData) {
                    searchMovieResultLiveData.postValue(null)
                    Log.d("ERROR_GET_MOVIE", e.message)
                    PopUpMessage().popUpToast(context, e.message, type = MDToast.TYPE_ERROR)
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
                    e.throwable
                    detailMovieLiveData.postValue(null)
                    Log.d("ERROR_GET_DETAIL_MOVIE", e.message)
                    PopUpMessage().popUpToast(context, e.message, type = MDToast.TYPE_ERROR)
                }
            })
    }

    fun searchTvShow(language: String, query: String) {
        apiService.getNowPlayingTvShow(BuildConfig.API_KEY, language, query)
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<TvShowResponse>(compositeDisposable) {
                override fun onSuccess(data: TvShowResponse) {
                    searchTvShowResultLiveData.postValue(data.results)
                    Log.d("SUCCESS_GET_TV_SHOW", "Success")
                }

                override fun onError(e: ErrorData) {
                    searchTvShowResultLiveData.postValue(null)
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

    fun getMovieReleasedToday(releaseDate: String, apiObserver: ApiObserver<MovieResponse>) {
        apiService.getMovieReleasedToday(BuildConfig.API_KEY, releaseDate, releaseDate)
            .subscribeOn(Schedulers.io())
            .subscribe(apiObserver)
    }

    fun getMovieReleasedToday(context: Context?, releaseDate: String) {
        apiService.getMovieReleasedToday(BuildConfig.API_KEY, releaseDate, releaseDate)
            .subscribeOn(Schedulers.io())
            .subscribe(object : ApiObserver<MovieResponse>(compositeDisposable) {
                override fun onSuccess(data: MovieResponse) {
                    if (data.results != null && data.results.isNotEmpty()) {
                        val result = data.results[0]
                        val title = result.title ?: ""
                        val message = context?.getString(R.string.release_today_message) ?: ""
                        NotificationHelper().showReleaseNotification(
                            context, title, message, result
                        )
                    }
                    Log.d("SUCCESS_GET_MOVIE_TODAY", "Success")
                }

                override fun onError(e: ErrorData) {
                    Log.d("ERROR_GET_MOVIE_TODAY", e.message)
                }
            })
    }
}