package com.example.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.models.fromResultToMovie
import com.example.mymovieapp.modelsApi.ApiResponse
import com.example.mymovieapp.modelsApi.MovieDetail
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    private lateinit var currentMovie: MovieDetail

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movieId = intent.getIntExtra("movie", -1)
        getMovieById(movieId)
    }

    private fun displayMovie() {
        val textViewTitle: TextView = findViewById(R.id.textViewDetailTitle)
        textViewTitle.text = currentMovie.original_title

        val imageView: ImageView = findViewById(R.id.imageViewDetailImage)
        Picasso.get().load("https://image.tmdb.org/t/p/w500" + currentMovie.poster_path).into(imageView)

        val textViewDescription: TextView = findViewById(R.id.textViewDetailDescription)
        textViewDescription.text = currentMovie.overview
    }

    private fun getMovieById(id: Int){
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMovieById(id)

        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(
                call: Call<MovieDetail>,
                response: Response<MovieDetail>
            ) {
                Toast.makeText(applicationContext, "Ok", Toast.LENGTH_SHORT).show()
                currentMovie = response.body() as MovieDetail
                displayMovie()
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                Log.d("tag", t.message.toString())
            }
        })
    }
}