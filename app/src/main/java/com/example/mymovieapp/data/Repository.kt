package com.example.mymovieapp.data

import androidx.lifecycle.LiveData
import com.example.mymovieapp.data.db.MovieDao
import com.example.mymovieapp.data.models.Movie
import com.example.mymovieapp.data.modelsApi.*
import com.example.mymovieapp.data.services.MoviesApi
import com.example.mymovieapp.data.services.ServiceBuilder
import retrofit2.Call

class Repository (private val movieDao: MovieDao) {
    val favoriteMovies: LiveData<List<Movie>> = movieDao.getAllMovies()
    private val moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
    private val omdbService = ServiceBuilder.buildServiceOMDB(MoviesApi::class.java)

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

    fun getMovieById(id: Int): Call<MovieDetail> {
        return moviesService.getMovieById(id)
    }

    fun getSerieById(id: Int): Call<SerieDetail> {
        return moviesService.getSerieById(id)
    }

    fun getAdditionalInfo(id: String): Call<MovieIMDB> {
        return omdbService.getMovieFromIMDB(id, "7ad828e9")
    }

    fun getMovieCredits(id: Int): Call<MovieCredits> {
        return moviesService.getMovieCredits(id)
    }

    fun getSerieCredits(id: Int): Call<MovieCredits> {
        return moviesService.getSerieCredits(id)
    }

    fun getTrailer(id: Int): Call<MovieTrailers> {
        return moviesService.getTrailers(id)
    }
}