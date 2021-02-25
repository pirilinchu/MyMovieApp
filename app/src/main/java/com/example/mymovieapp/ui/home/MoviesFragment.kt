package com.example.mymovieapp.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
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

    private lateinit var recyclerViewNow: RecyclerView
    private lateinit var recyclerViewPopular: RecyclerView
    private lateinit var recyclerViewIncoming: RecyclerView

    private lateinit var moviesNow: List<Movie>
    private lateinit var moviesPopular: List<Movie>
    private lateinit var moviesIncoming: List<Movie>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var root: View = inflater.inflate(R.layout.fragment_movies, container, false)
        recyclerViewNow = root.findViewById(R.id.recyclerViewNow)
        recyclerViewPopular = root.findViewById(R.id.recyclerViewPopular)
        recyclerViewIncoming = root.findViewById(R.id.recyclerViewIncoming)

        var recyclerViewAdapterNow: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), moviesNow)
        var recyclerViewAdapterPopular: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), moviesPopular)
        var recyclerViewAdapterIncoming: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), moviesIncoming)

        recyclerViewNow.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewNow.adapter = recyclerViewAdapterNow

        recyclerViewPopular.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewPopular.adapter = recyclerViewAdapterPopular

        recyclerViewIncoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerViewIncoming.adapter = recyclerViewAdapterIncoming



        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (DataManager.moviesNow.size == 0) getMoviesNow()
        if (DataManager.moviesPopular.size == 0) getMoviesPopular()
        if (DataManager.moviesIncoming.size == 0) getMoviesIncoming()

        moviesNow = DataManager.moviesNow
        moviesPopular = DataManager.moviesPopular
        moviesIncoming = DataManager.moviesIncoming
    }

    private fun getMoviesIncoming() {
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMoviesIncoming()
        var moviesIncoming = ArrayList<Movie> ()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                for (i in response!!.results){
                    moviesIncoming.add(fromResultToMovie(i))
                }
                DataManager.moviesIncoming = moviesIncoming
                var recyclerViewAdapterIncoming: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), moviesIncoming)
                recyclerViewIncoming.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewIncoming.adapter = recyclerViewAdapterIncoming
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("tag", t.message.toString())
            }
        })
    }

    private fun getMoviesPopular() {
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMoviesPopular()
        var moviesPopular = ArrayList<Movie> ()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                for (i in response!!.results){
                    moviesPopular.add(fromResultToMovie(i))
                }
                DataManager.moviesPopular = moviesPopular
                var recyclerViewAdapterPopular: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), moviesPopular)
                recyclerViewPopular.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewPopular.adapter = recyclerViewAdapterPopular
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("tag", t.message.toString())
            }
        })
    }

    private fun getMoviesNow(){
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMoviesNow()
        var moviesNow = ArrayList<Movie> ()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                for (i in response!!.results){
                    moviesNow.add(fromResultToMovie(i))
                }
                DataManager.moviesNow = moviesNow
                var recyclerViewAdapterNow: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), moviesNow)
                recyclerViewNow.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
                recyclerViewNow.adapter = recyclerViewAdapterNow
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("tag", t.message.toString())
            }
        })
    }

}