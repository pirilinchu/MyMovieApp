package com.example.mymovieapp.services

import com.example.mymovieapp.modelsApi.ApiResponse
import retrofit2.Call
import retrofit2.http.GET

interface MoviesApi {
    @GET("movie/now_playing?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getMoviesNow(): Call<ApiResponse>

    @GET("movie/popular?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getMoviesPopular(): Call<ApiResponse>

    @GET("movie/upcoming?api_key=d3a6bd82af12c91d08149f6613e5dce2&language=en-US&page=1")
    fun getMoviesIncoming(): Call<ApiResponse>

}