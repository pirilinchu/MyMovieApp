package com.example.mymovieapp.models

import com.example.mymovieapp.R

object DataManager {

    var moviesNow = ArrayList<Movie>()
    var moviesPopular = ArrayList<Movie>()
    var moviesIncoming = ArrayList<Movie>()

    init {
        initializeMoviesNow()
        initializeMoviesPopular()
        initializeMoviesIncoming()
    }

    private fun initializeMoviesNow() {
        var movie: Movie = Movie("movie1", R.drawable.poster)
        moviesNow.add(movie)
        movie = Movie("movie2", R.drawable.poster)
        moviesNow.add(movie)
        movie = Movie("movie3", R.drawable.poster)
        moviesNow.add(movie)
        movie = Movie("movie4", R.drawable.poster2)
        moviesNow.add(movie)
    }

    private fun initializeMoviesPopular() {
        var movie: Movie = Movie("movie43", R.drawable.poster)
        moviesPopular.add(movie)
        movie = Movie("movie2", R.drawable.poster)
        moviesPopular.add(movie)
        movie = Movie("movie3", R.drawable.poster)
        moviesPopular.add(movie)
        movie = Movie("movie4", R.drawable.poster2)
        moviesPopular.add(movie)
        movie = Movie("movie5", R.drawable.poster2)
        moviesPopular.add(movie)
    }

    private fun initializeMoviesIncoming() {
        var movie: Movie = Movie("movie112", R.drawable.poster)
        moviesIncoming.add(movie)
        movie = Movie("movie2", R.drawable.poster)
        moviesIncoming.add(movie)
        movie = Movie("movie3", R.drawable.poster)
        moviesIncoming.add(movie)
    }
}