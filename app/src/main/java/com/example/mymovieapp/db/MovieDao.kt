package com.example.mymovieapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mymovieapp.models.Movie

@Dao
interface MovieDao {
    @Query("SELECT * FROM favorites")
    fun getAllMovies(): LiveData<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(vararg movie: Movie)

    @Delete()
    fun deleteMovie(movie: Movie)

//    @Query("SELECT * FROM favorites WHERE Id= :id")
//    fun exists(vararg id: Int): Int
}