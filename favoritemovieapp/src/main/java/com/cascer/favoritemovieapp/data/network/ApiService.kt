package com.cascer.favoritemovieapp.data.network

import com.cascer.favoritemovieapp.data.response.detail.MovieDetailResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/{movie_id}")
    fun getMovieDetail(
        @Path("movie_id") movieID: Int,
        @Query("api_key") apiKey: String,
        @Query("language") language: String
    ): Observable<MovieDetailResponse>
}