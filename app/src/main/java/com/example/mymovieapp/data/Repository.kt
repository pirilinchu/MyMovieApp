package com.example.mymovieapp.data

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovieapp.db.MovieDao
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.models.fromSerieResultToMovie
import com.example.mymovieapp.modelsApi.ApiResponse
import com.example.mymovieapp.modelsApi.ApiResponseSerie
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Repository (private val movieDao: MovieDao) {
    val favoriteMovies: LiveData<List<Movie>> = movieDao.getAllMovies()
    private val moviesService = ServiceBuilder.buildService(MoviesApi::class.java)

    suspend fun insert(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun delete(movie: Movie) {
        movieDao.deleteMovie(movie)
    }

    fun getTvShows(): Call<ApiResponseSerie> {
        return moviesService.getSeries()
    }

    fun getMoviesNow(): Call<ApiResponse> {
        return moviesService.getMoviesNow()
    }

    fun getMoviesPopular(): Call<ApiResponse> {
        return moviesService.getMoviesPopular()
    }

    fun getMoviesIncoming(): Call<ApiResponse> {
        return moviesService.getMoviesIncoming()
    }
}