package com.example.mymovieapp.models

import com.example.mymovieapp.R
import com.example.mymovieapp.modelsApi.Result
import com.example.mymovieapp.modelsApi.ResultSerie
import java.io.Serializable

data class Movie (
    var Title: String? = null,
    var Image: String? = null,
    var Id: Int? = null
) : Serializable{

}

public fun fromResultToMovie(result: Result): Movie{
    var movie = Movie(result.title, "https://image.tmdb.org/t/p/w500" + result.poster_path, result.id)
    return movie;
}

public fun fromSerieResultToMovie(result: ResultSerie): Movie{
    var movie = Movie(result.name, "https://image.tmdb.org/t/p/w500" + result.poster_path, result.id)
    return movie;
}
