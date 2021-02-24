package com.example.mymovieapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.models.Movie

class RecyclerViewAdapter(
    private val context: Context,
    private val movies: List<Movie>
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieImage: ImageView
        var movieTitle : TextView

        init {
            movieImage = itemView.findViewById(R.id.cardImageId)
            movieTitle = itemView.findViewById(R.id.cardTitleId)
            itemView.setOnClickListener{
                val toast = Toast.makeText(context, "Clicked Item", Toast.LENGTH_SHORT)
                toast.show()
                Log.d("Message", "Working")
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
        holder.movieImage.setImageResource(movies[position].Image!!)
    }
}