package com.example.mymovieapp.ui.movies

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.data.modelsApi.Cast
import com.example.mymovieapp.data.modelsApi.Crew
import com.squareup.picasso.Picasso

class RecyclerViewActorsAdapter(
        private val context: Context,
        private val cast: List<Cast>
) : RecyclerView.Adapter<RecyclerViewActorsAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var actorImage: ImageView
        var actorName : TextView
        var actorPosition = 0

        init {
            actorImage = itemView.findViewById(R.id.cardImageIdActor)
            actorName = itemView.findViewById(R.id.cardTitleIdActor)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.actor_card, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return cast.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.actorName.text = cast[position]?.name

        var path: String
        if(cast[position].profile_path != null) {
            path = cast[position].profile_path as String
            Picasso.get().load("https://image.tmdb.org/t/p/w500" + path).into(holder.actorImage)
        }
        else {
            holder.actorImage.setImageResource(R.drawable.place_holder)
        }

        holder.actorPosition = position
    }
}