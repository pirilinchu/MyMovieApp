package com.example.mymovieapp.data

import androidx.lifecycle.LiveData
import com.example.mymovieapp.db.MovieDao
import com.example.mymovieapp.models.Movie

class Repository (private val movieDao: MovieDao) {
    val favoriteMovies: LiveData<List<Movie>> = movieDao.getAllMovies()

    suspend fun insert(movie: Movie) {
        movieDao.insertMovie(movie)
    }

    suspend fun delete(movie: Movie) {
        movieDao.deleteMovie(movie)
    }
}