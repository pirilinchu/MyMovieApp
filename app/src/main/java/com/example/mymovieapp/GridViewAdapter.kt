package com.example.mymovieapp

import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.mymovieapp.db.DataBase
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.ui.detail.MovieActivity
import com.squareup.picasso.Picasso

class GridViewAdapter(
    private val context: Context,
    private val movies: List<Movie>
) : BaseAdapter() {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View = View.inflate(context, R.layout.movie_small_card, null)
        var movieImage: ImageView
        val database = DataBase.getDataBase(context)

        movieImage = view.findViewById(R.id.cardImageSmallId)

        var item: Movie = movies.get(position)
//        movieImage.setImageResource(item.Image!!)
        Picasso.get().load(item.Image).into(movieImage)
        view.setOnClickListener{
            val intent = Intent(context, MovieActivity::class.java)
            intent.putExtra("movie", movies[position].Id)
            context.startActivity(intent)
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