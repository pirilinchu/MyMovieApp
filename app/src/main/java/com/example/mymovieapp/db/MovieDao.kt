package com.example.mymovieapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mymovieapp.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorites")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert
    fun insertMovie(vararg movie: Movie)
}