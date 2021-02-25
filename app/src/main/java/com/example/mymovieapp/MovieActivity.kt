package com.example.mymovieapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.mymovieapp.models.Movie
import com.squareup.picasso.Picasso

class MovieActivity : AppCompatActivity() {

    private lateinit var currentMovie: Movie

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie)

        val movie = intent.getSerializableExtra("movie") as Movie
        currentMovie = movie
        displayMovie()
    }

    private fun displayMovie() {
        val textView: TextView = findViewById(R.id.textViewDetailTitle)
        textView.text = currentMovie.Title

        val imageView: ImageView = findViewById(R.id.imageViewDetailImage)
//        imageView.setImageResource(currentMovie.Image!!)
        Picasso.get().load(currentMovie.Image).into(imageView)
    }
}