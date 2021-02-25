package com.example.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.models.fromResultToMovie
import com.example.mymovieapp.modelsApi.ApiResponse
import com.example.mymovieapp.modelsApi.MovieDetail
import com.example.mymovieapp.modelsApi.SerieDetail
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import com.squareup.picasso.Picasso
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    private lateinit var currentMovie: MovieDetail
    private lateinit var currentSerie: SerieDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieId = intent.getIntExtra("movie", -1)
        if(movieId != -1) getMovieById(movieId)

        val serieId = intent.getIntExtra("serie", -1)
        if(serieId != -1) getSerieById(serieId)
    }

    private fun displayMovie() {
        val textViewTitle: TextView = findViewById(R.id.textViewDetailTitle)
        textViewTitle.text = currentMovie.original_title

        val imageView: ImageView = findViewById(R.id.imageViewDetailImage)
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + currentMovie.poster_path).into(imageView)

        val textViewDescription: TextView = findViewById(R.id.textViewDetailDescription)
        textViewDescription.text = currentMovie.overview

        val textViewRating: TextView = findViewById(R.id.textViewDetailRating)
        textViewRating.text = currentMovie.vote_average.toString()

        val textViewGenres: TextView = findViewById(R.id.textViewDetailGenres)
        var genres: String = ""
        for (i in currentMovie.genres)
        {
            genres += (i.name + ", ")
        }
        genres = genres.dropLast(2)
        textViewGenres.text = genres
    }

    private fun displaySerie() {
        val textViewTitle: TextView = findViewById(R.id.textViewDetailTitle)
        textViewTitle.text = currentSerie.original_name

        val imageView: ImageView = findViewById(R.id.imageViewDetailImage)
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + currentSerie.poster_path).into(imageView)

        val textViewDescription: TextView = findViewById(R.id.textViewDetailDescription)
        textViewDescription.text = currentSerie.overview

        val textViewRating: TextView = findViewById(R.id.textViewDetailRating)
        textViewRating.text = currentSerie.vote_average.toString()

        val textViewGenres: TextView = findViewById(R.id.textViewDetailGenres)
        var genres: String = ""
        for (i in currentSerie.genres)
        {
            genres += (i.name + ", ")
        }
        genres = genres.dropLast(2)
        textViewGenres.text = genres
    }

    private fun getMovieById(id: Int){
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMovieById(id)

        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(
                call: Call<MovieDetail>,
                response: Response<MovieDetail>
            ) {
                currentMovie = response.body() as MovieDetail
                displayMovie()
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    private fun getSerieById(id: Int){
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getSerieById(id)

        call.enqueue(object : Callback<SerieDetail> {
            override fun onResponse(
                call: Call<SerieDetail>,
                response: Response<SerieDetail>
            ) {
                currentSerie = response.body() as SerieDetail
                displaySerie()
            }

            override fun onFailure(call: Call<SerieDetail>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                Log.d("tag", t.message.toString())
            }
        })
    }
}