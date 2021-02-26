package com.example.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.db.DataBase
import com.example.mymovieapp.models.*
import com.example.mymovieapp.modelsApi.Genre
import com.example.mymovieapp.modelsApi.MovieDetail
import com.example.mymovieapp.modelsApi.MovieIMDB
import com.example.mymovieapp.modelsApi.SerieDetail
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieActivity : AppCompatActivity() {

    private lateinit var currentMovie: MovieDetail
    private lateinit var currentMovieIMDB: MovieIMDB
    private lateinit var currentSerie: SerieDetail
    private lateinit var recyclerViewTags: RecyclerView
//    private lateinit var tags: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        this.getSupportActionBar()?.hide();

        val movieId = intent.getIntExtra("movie", -1)
        if(movieId != -1) getMovieById(movieId)

        val serieId = intent.getIntExtra("serie", -1)
        if(serieId != -1) getSerieById(serieId)

        val database = DataBase.getDataBase(this)

        val button: FloatingActionButton = findViewById(R.id.floatingActionButton)

        button.setOnClickListener{
            if(movieId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    database.favorites().insertMovie(fromDetailMovieToMovie(currentMovie))
                }
            }
            if(serieId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                        database.favorites().insertMovie(fromDetailSerieToMovie(currentSerie))
                }
            }
        }
        button.setOnLongClickListener{
//            database.favorites().deleteMovie(fromDetailMovieToMovie(currentMovie))
            Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show()
            true
        }
    }

    private fun loadTags (genres: List<Genre>) {
        var tags: MutableList<String> =  mutableListOf()
        for (i in currentMovie.genres)
        {
            tags.add(i.name)
        }
        recyclerViewTags = findViewById(R.id.recyclerViewDetailGenres)

        var layoutManager: FlexboxLayoutManager = FlexboxLayoutManager(applicationContext)
        layoutManager.flexDirection = FlexDirection.ROW;
        layoutManager.justifyContent = JustifyContent.FLEX_START
        var recyclerViewAdapter: RecyclerViewChipsAdapter = RecyclerViewChipsAdapter(applicationContext, tags)

        recyclerViewTags.layoutManager = layoutManager
        recyclerViewTags.adapter = recyclerViewAdapter
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

//        val textViewGenres: TextView = findViewById(R.id.textViewDetailGenres)
//        var genres: String = ""
//        for (i in currentMovie.genres)
//        {
//            genres += (i.name + ", ")
//        }
//        genres = genres.dropLast(2)
//        textViewGenres.text = genres
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

//        val textViewGenres: TextView = findViewById(R.id.textViewDetailGenres)
//        var genres: String = ""
//        for (i in currentSerie.genres)
//        {
//            genres += (i.name + ", ")
//        }
//        genres = genres.dropLast(2)
//        textViewGenres.text = genres
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
                //getAdditionalInfo(currentMovie.imdb_id)
                loadTags(currentMovie.genres)
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

    private fun getAdditionalInfo(id: String){
        var moviesService = ServiceBuilder.buildService2(MoviesApi::class.java)
        var call = moviesService.getMovieFromIMDB(id)

        call.enqueue(object : Callback<MovieIMDB> {
            override fun onResponse(
                call: Call<MovieIMDB>,
                response: Response<MovieIMDB>
            ) {
                currentMovieIMDB = response.body() as MovieIMDB
                displayAdditionalInfo()
            }

            override fun onFailure(call: Call<MovieIMDB>, t: Throwable) {
                Log.w("MyTag", "requestFailed", t)
            }
        })
    }

    private fun displayAdditionalInfo() {
        val textViewDirector: TextView = findViewById(R.id.textViewDirector)
        textViewDirector.text = currentMovieIMDB.Director

        val textViewCast: TextView = findViewById(R.id.textViewCast)
        textViewCast.text = currentMovieIMDB.Actors
    }
}