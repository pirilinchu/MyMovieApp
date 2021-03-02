package com.example.mymovieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.RecyclerViewAdapter
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.models.fromResultToMovie
import com.example.mymovieapp.modelsApi.ApiResponse
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesFragment : Fragment() {

    private lateinit var viewModel: MoviesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(MoviesViewModel::class.java)

        loadMovies()

        val root: View = inflater.inflate(R.layout.fragment_movies, container, false)

        val recyclerViewNow: RecyclerView = root.findViewById(R.id.recyclerViewNow)
        val recyclerViewPopular: RecyclerView = root.findViewById(R.id.recyclerViewPopular)
        val recyclerViewIncoming: RecyclerView = root.findViewById(R.id.recyclerViewIncoming)

        viewModel.moviesNow.observe(viewLifecycleOwner, Observer {
            var recyclerViewAdapterNow: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), it)
            recyclerViewNow.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewNow.adapter = recyclerViewAdapterNow
        })

        viewModel.moviesIncoming.observe(viewLifecycleOwner, Observer {
            var recyclerViewAdapterIncoming: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), it)
            recyclerViewIncoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewIncoming.adapter = recyclerViewAdapterIncoming
        })

        viewModel.moviesPopular.observe(viewLifecycleOwner, Observer {
            var recyclerViewAdapterPopular: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), it)
            recyclerViewPopular.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
            recyclerViewPopular.adapter = recyclerViewAdapterPopular
        })

        return root
    }

    private fun loadMovies() {
        viewModel.getMoviesNow()
        viewModel.getMoviesPopular()
        viewModel.getMoviesIncoming()
    }
}