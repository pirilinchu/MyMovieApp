package com.example.mymovieapp.data.services

import com.example.mymovieapp.data.modelsApi.*
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {
    @GET("movie/now_playing?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getMoviesNow(): Call<ApiResponse>

    @GET("movie/popular?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getMoviesPopular(): Call<ApiResponse>

    @GET("movie/upcoming?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getMoviesIncoming(): Call<ApiResponse>

    @GET("tv/popular?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getSeries(): Call<ApiResponseSerie>

    @GET("movie/{id}?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getMovieById(@Path("id") id: Int): Call<MovieDetail>

    @GET("tv/{id}?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getSerieById(@Path("id") id: Int): Call<SerieDetail>

    @GET("movie/{id}/credits?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US")
    fun getMovieCredits(@Path("id") id: Int): Call<MovieCredits>

    @GET("tv/{id}/credits?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US")
    fun getSerieCredits(@Path("id") id: Int): Call<MovieCredits>

    @GET("/")
    fun getMovieFromIMDB(
            @Query("i") i: String,
            @Query("apikey") apikey: String): Call<MovieIMDB>
}