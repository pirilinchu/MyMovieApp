package com.example.mymovieapp.ui.movies

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.data.models.Movie
import com.example.mymovieapp.ui.detail.MovieActivity
import com.squareup.picasso.Picasso

class RecyclerViewAdapter(
    private val context: Context,
    private val movies: List<Movie>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieImage: ImageView
        var movieTitle : TextView
        var moviePosition = 0

        init {
            movieImage = itemView.findViewById(R.id.cardImageId)
            movieTitle = itemView.findViewById(R.id.cardTitleId)
            itemView.setOnClickListener{
                val intent = Intent(context, MovieActivity::class.java)
                intent.putExtra("movie", movies[moviePosition].Id)
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = movies[position].Title
//        holder.movieImage.setImageResource(movies[position].Image!!)
        Picasso.get().load(movies[position].Image).into(holder.movieImage)
        holder.moviePosition = position
    }
}