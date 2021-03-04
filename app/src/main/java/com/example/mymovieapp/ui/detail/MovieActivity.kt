package com.example.mymovieapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.baseImageUrl
import com.example.mymovieapp.data.modelsApi.MovieIMDB
import com.example.mymovieapp.ui.movies.RecyclerViewActorsAdapter
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.w3c.dom.Text

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
        val recyclerViewTags: RecyclerView = findViewById(R.id.recyclerViewDetailGenres)
        val recycerViewCast: RecyclerView = findViewById(R.id.recyclerViewCast)
        val textViewDirector: TextView = findViewById(R.id.textViewDirector)

        viewModel.title.observe(this, Observer {
            textViewTitle.text = it
        })

        viewModel.image.observe(this, Observer {
            if (it == "No Image") imageView.setImageResource(R.drawable.place_holder)
            else Picasso.get().load(baseImageUrl + it).into(imageView)
        })

        viewModel.description.observe(this, Observer {
            textViewDescription.text = it
        })

        viewModel.rating.observe(this, Observer {
            if(it == 0.0) textViewRating.text = "No Rating"
            else textViewRating.text = it.toString()
        })

        viewModel.director.observe(this, Observer {
            textViewDirector.text = it
        })

        viewModel.tags.observe(this, Observer {
            var layoutManager: FlexboxLayoutManager = FlexboxLayoutManager(applicationContext)
            layoutManager.flexDirection = FlexDirection.ROW;
            layoutManager.justifyContent = JustifyContent.FLEX_START
            var recyclerViewAdapter: RecyclerViewChipsAdapter = RecyclerViewChipsAdapter(applicationContext, it)

            recyclerViewTags.layoutManager = layoutManager
            recyclerViewTags.adapter = recyclerViewAdapter
        })

        viewModel.cast.observe(this, Observer {
            var recyclerViewAdapterCast: RecyclerViewActorsAdapter = RecyclerViewActorsAdapter(applicationContext, it)
            recycerViewCast.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.HORIZONTAL, false)
            recycerViewCast.adapter = recyclerViewAdapterCast
        })

        button.setOnClickListener{
            if(movieId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertMovie()
                }
            }
            if(serieId != -1) {
                CoroutineScope(Dispatchers.IO).launch {
                    viewModel.insertSerie()
                }
            }
        }
        button.setOnLongClickListener{
//            database.favorites().deleteMovie(fromDetailMovieToMovie(currentMovie))
            Toast.makeText(this, "Removed", Toast.LENGTH_SHORT).show()
            true
        }
    }
}
