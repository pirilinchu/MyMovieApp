package com.example.mymovieapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.GridViewAdapter
import com.example.mymovieapp.R
import com.example.mymovieapp.RecyclerViewSeriesAdapter
import com.example.mymovieapp.db.DataBase
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie

class ProfileFragment : Fragment() {

    private lateinit var gridView: GridView
    private lateinit var favorites: List<Movie>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        gridView = root.findViewById(R.id.gridViewFavorites)

        val database = DataBase.getDataBase(requireContext())

        database.favorites().getAllMovies().observe(viewLifecycleOwner, Observer {
            favorites = it
            DataManager.favorites = it.toCollection(ArrayList())

            var gridAdapter: GridViewAdapter = GridViewAdapter(requireContext(), favorites)

            gridView.adapter = gridAdapter
        })

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        favorites = DataManager.favorites
    }
}