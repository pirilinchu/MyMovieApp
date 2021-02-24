package com.example.mymovieapp

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.models.Movie

class GridViewAdapter(
    private val context: Context,
    private val movies: List<Movie>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.movie_small_card, null)
        var movieImage: ImageView

        movieImage = view.findViewById(R.id.cardImageSmallId)

        var item: Movie = movies.get(position)
        movieImage.setImageResource(item.Image!!)

        view.setOnClickListener{
            val toast = Toast.makeText(context, "Clicked Item2", Toast.LENGTH_SHORT)
            toast.show()
            Log.d("Message", "Working2")
        }
        return view
    }

    override fun getItem(position: Int): Any {
        return movies.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return movies.size
    }
}