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

class RecyclerViewSeriesAdapter(
    private val context: Context,
    private val series: List<Movie>
) : RecyclerView.Adapter<RecyclerViewSeriesAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var serieImage: ImageView
        var serieTitle : TextView

        init {
            serieImage = itemView.findViewById(R.id.cardSerieImageId)
            serieTitle = itemView.findViewById(R.id.cardSerieTitleId)
            itemView.setOnClickListener{
                val toast = Toast.makeText(context, "Clicked Item", Toast.LENGTH_SHORT)
                toast.show()
                Log.d("Message", "Working")
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
        holder.serieImage.setImageResource(series[position].Image!!)
    }
}