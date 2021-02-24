package com.example.mymovieapp.models

import com.example.mymovieapp.R

object DataManager {

    var moviesNow = ArrayList<Movie>()

    init {
        initializeMovies()
    }

    private fun initializeMovies() {
        var movie: Movie = Movie("movie1", R.drawable.poster)
        moviesNow.add(movie)
        movie = Movie("movie2", R.drawable.poster)
        moviesNow.add(movie)
        movie = Movie("movie3", R.drawable.poster)
        moviesNow.add(movie)
        movie = Movie("movie4", R.drawable.poster2)
        moviesNow.add(movie)
    }
}