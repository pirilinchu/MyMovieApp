package com.example.mymovieapp.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "favorites")
class MovieDB (
    var Type: Char,
    @PrimaryKey
    var Id: Int
):Serializable{

}

