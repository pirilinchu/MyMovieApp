package com.example.mymovieapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.RecyclerViewAdapter
import com.example.mymovieapp.RecyclerViewChipsAdapter
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.db.DataBase
import com.example.mymovieapp.models.*
import com.example.mymovieapp.modelsApi.Genre
import com.example.mymovieapp.modelsApi.MovieDetail
import com.example.mymovieapp.modelsApi.MovieIMDB
import com.example.mymovieapp.modelsApi.SerieDetail
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import com.example.mymovieapp.ui.movies.MoviesViewModel
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

    private lateinit var currentMovieIMDB: MovieIMDB
    private lateinit var viewModel: MovieViewModel

    override fun onCreate(savedInstanceState: Bundle?) {

        viewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        this.getSupportActionBar()?.hide();

        val movieId = intent.getIntExtra("movie", -1)
        if(movieId != -1) viewModel.loadMovie(movieId)

        val serieId = intent.getIntExtra("serie", -1)
        if(serieId != -1) viewModel.loadSerie(serieId)

        val button: FloatingActionButton = findViewById(R.id.floatingActionButton)

        val textViewTitle: TextView = findViewById(R.id.textViewDetailTitle)
        val imageView: ImageView = findViewById(R.id.imageViewDetailImage)
        val textViewDescription: TextView = findViewById(R.id.textViewDetailDescription)
        val textViewRating: TextView = findViewById(R.id.textViewDetailRating)
        var recyclerViewTags: RecyclerView = findViewById(R.id.recyclerViewDetailGenres)

        viewModel.title.observe(this, Observer {
            textViewTitle.text = it
        })

        viewModel.image.observe(this, Observer {
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + it).into(imageView)
        })

        viewModel.description.observe(this, Observer {
            textViewDescription.text = it
        })

        viewModel.rating.observe(this, Observer {
            textViewRating.text = it.toString()
        })

        viewModel.tags.observe(this, Observer {
            var layoutManager: FlexboxLayoutManager = FlexboxLayoutManager(applicationContext)
            layoutManager.flexDirection = FlexDirection.ROW;
            layoutManager.justifyContent = JustifyContent.FLEX_START
            var recyclerViewAdapter: RecyclerViewChipsAdapter = RecyclerViewChipsAdapter(applicationContext, it)

            recyclerViewTags.layoutManager = layoutManager
            recyclerViewTags.adapter = recyclerViewAdapter
        })

        button.setOnClickListener{
            if(movieId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    //repository.insert(fromDetailMovieToMovie(currentMovie))//.favorites().insertMovie(fromDetailMovieToMovie(currentMovie))
                    viewModel.insertMovie()
                }
            }
            if(serieId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertSerie()
                    //repository.insert(fromDetailSerieToMovie(currentSerie))//database.favorites().insertMovie(fromDetailSerieToMovie(currentSerie))
                }
            }
        }
        button.setOnLongClickListener{
//            database.favorites().deleteMovie(fromDetailMovieToMovie(currentMovie))
            Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show()
            true
        }
    }



//    private fun getAdditionalInfo(id: String){
//        var moviesService = ServiceBuilder.buildService2(MoviesApi::class.java)
//        var call = moviesService.getMovieFromIMDB(id)
//
//        call.enqueue(object : Callback<MovieIMDB> {
//            override fun onResponse(
//                call: Call<MovieIMDB>,
//                response: Response<MovieIMDB>
//            ) {
//                currentMovieIMDB = response.body() as MovieIMDB
//                displayAdditionalInfo()
//            }
//
//            override fun onFailure(call: Call<MovieIMDB>, t: Throwable) {
//                Log.w("MyTag", "requestFailed", t)
//            }
//        })
//    }
//
//    private fun displayAdditionalInfo() {
//        val textViewDirector: TextView = findViewById(R.id.textViewDirector)
//        textViewDirector.text = currentMovieIMDB.Director
//
//        val textViewCast: TextView = findViewById(R.id.textViewCast)
//        textViewCast.text = currentMovieIMDB.Actors
//    }
}