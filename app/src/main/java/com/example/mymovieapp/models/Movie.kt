package com.example.mymovieapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymovieapp.R
import com.example.mymovieapp.db.MovieDB
import com.example.mymovieapp.modelsApi.MovieDetail
import com.example.mymovieapp.modelsApi.Result
import com.example.mymovieapp.modelsApi.ResultSerie
import com.example.mymovieapp.modelsApi.SerieDetail
import java.io.Serializable

@Entity(tableName = "favorites")
data class Movie (
    var Title: String? = null,
    var Image: String? = null,
    var Id: Int? = null,
    var Type: Char? = null,
    @PrimaryKey(autoGenerate = true)
    var IdMD: Int = 0
) : Serializable{

}

public fun fromResultToMovie(result: Result): Movie{
    var movie = Movie(result.title, "https://image.tmdb.org/t/p/w500" + result.poster_path, result.id, 'M')
    return movie;
}

public fun fromSerieResultToMovie(result: ResultSerie): Movie{
    var movie = Movie(result.name, "https://image.tmdb.org/t/p/w500" + result.poster_path, result.id, 'S')
    return movie;
}

public fun fromDetailMovieToMovie(detailMovie: MovieDetail): Movie{
    var movie = Movie(detailMovie.title, "https://image.tmdb.org/t/p/w500" + detailMovie.poster_path, detailMovie.id, 'M')
    return movie;
}

public fun fromDetailSerieToMovie(detailSerie: SerieDetail): Movie{
    var movie = Movie(detailSerie.name, "https://image.tmdb.org/t/p/w500" + detailSerie.poster_path, detailSerie.id, 'S')
    return movie;
}
