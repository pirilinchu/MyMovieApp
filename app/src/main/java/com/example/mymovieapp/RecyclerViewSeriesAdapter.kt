package com.example.mymovieapp

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.models.Movie
import com.squareup.picasso.Picasso

class RecyclerViewSeriesAdapter(
    private val context: Context,
    private val series: List<Movie>
) : RecyclerView.Adapter<RecyclerViewSeriesAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var serieImage: ImageView
        var serieTitle : TextView
        var seriePosition : Int

        init {
            serieImage = itemView.findViewById(R.id.cardSerieImageId)
            serieTitle = itemView.findViewById(R.id.cardSerieTitleId)
            seriePosition = 0
            itemView.setOnClickListener{
                val intent = Intent(context, MovieActivity::class.java)
                intent.putExtra("movie", series[seriePosition])
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.serie_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return series.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.serieTitle.text = series[position].Title
//        holder.serieImage.setImageResource(series[position].Image!!)
        Picasso.get().load(series[position].Image).into(holder.serieImage)
        holder.seriePosition = position
    }
}