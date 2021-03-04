package com.example.mymovieapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mymovieapp.baseImageUrl
import com.example.mymovieapp.data.modelsApi.MovieDetail
import com.example.mymovieapp.data.modelsApi.Result
import com.example.mymovieapp.data.modelsApi.ResultSerie
import com.example.mymovieapp.data.modelsApi.SerieDetail
import java.io.Serializable

@Entity(tableName = "favorites")
data class Movie (
    var Title: String? = null,
    var Image: String? = null,
    @PrimaryKey()
    var Id: Int? = null,
    var Type: Char? = null,
    var IdMD: Int = 0
) : Serializable{

}

public fun fromResultToMovie(result: Result): Movie{
    var movie = Movie(result.title, baseImageUrl + result.poster_path, result.id, 'M')
    return movie;
}

public fun fromSerieResultToMovie(result: ResultSerie): Movie{
    var movie = Movie(result.name, baseImageUrl + result.poster_path, result.id, 'S')
    return movie;
}

public fun fromDetailMovieToMovie(detailMovie: MovieDetail): Movie{
    var movie = Movie(detailMovie.title, baseImageUrl + detailMovie.poster_path, detailMovie.id, 'M')
    return movie;
}

public fun fromDetailSerieToMovie(detailSerie: SerieDetail): Movie{
    var movie = Movie(detailSerie.name, baseImageUrl + detailSerie.poster_path, detailSerie.id, 'S')
    return movie;
}
