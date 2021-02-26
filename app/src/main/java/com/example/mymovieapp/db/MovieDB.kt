package com.example.mymovieapp.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymovieapp.modelsApi.MovieDetail
import com.example.mymovieapp.modelsApi.SerieDetail
import java.io.Serializable

@Entity(tableName = "favorites")
class MovieDB (
    var Type: Char,
    @PrimaryKey
    var Id: Int
):Serializable{

}

