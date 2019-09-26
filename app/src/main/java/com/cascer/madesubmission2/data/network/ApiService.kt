package com.cascer.madesubmission2.data.network

import com.cascer.madesubmission2.data.response.movie.MovieResponse
import com.cascer.madesubmission2.data.response.movie.detail.MovieDetailResponse
import com.cascer.madesubmission2.data.response.tv_show.TvShowResponse
import com.cascer.madesubmission2.data.response.tv_show.detail.TvShowDetailResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/movie")
    fun getNowPlayingMovie(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Observable<MovieResponse>

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<MovieDetailResponse>

    @GET("search/tv")
    fun getNowPlayingTvShow(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("query") query: String
    ): Observable<TvShowResponse>

    @GET("tv/{tv_id}")
    fun getTvShowDetail(
        @Path("tv_id") tvID: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<TvShowDetailResponse>

    @GET("discover/movie")
    fun getMovieReleasedToday(
        @Query("api_key") apiKey: String,
        @Query("primary_release_date.gte") primaryReleaseDateGTE: String,
        @Query("primary_release_date.lte") primaryReleaseDateLTE: String
    ): Observable<MovieResponse>
}