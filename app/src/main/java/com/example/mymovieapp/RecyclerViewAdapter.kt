package com.example.mymovieapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    private val itemTitles = arrayOf("movie1", "movie2", "movie3", "movie4")

    private val itemImages = intArrayOf(R.drawable.poster, R.drawable.poster, R.drawable.poster, R.drawable.poster2)

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var movieImage: ImageView
        var movieTitle : TextView

        init {
            movieImage = itemView.findViewById(R.id.cardImageId)
            movieTitle = itemView.findViewById(R.id.cardTitleId)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemTitles.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.movieTitle.text = itemTitles [position]
        holder.movieImage.setImageResource(itemImages[position])
        holder.itemView.setOnClickListener{
            Toast.makeText(it.context, "Clicked Item", Toast.LENGTH_SHORT)
        }
    }
}