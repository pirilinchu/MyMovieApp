package com.example.mymovieapp.ui.dashboard

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
import com.example.mymovieapp.RecyclerViewSeriesAdapter
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.models.fromResultToMovie
import com.example.mymovieapp.models.fromSerieResultToMovie
import com.example.mymovieapp.modelsApi.ApiResponse
import com.example.mymovieapp.modelsApi.ApiResponseSerie
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class TvFragment : Fragment() {

    private lateinit var viewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(TvViewModel::class.java)

        loadTvShows()

        val root: View = inflater.inflate(R.layout.fragment_tv, container, false)

        val recyclerViewSeries: RecyclerView = root.findViewById(R.id.recyclerViewSeries)

        viewModel.tvShows.observe(viewLifecycleOwner, Observer {
            val recyclerViewSeriesAdapter: RecyclerViewSeriesAdapter = RecyclerViewSeriesAdapter(requireContext(), it)
            recyclerViewSeries.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewSeries.adapter = recyclerViewSeriesAdapter
        })

        return root
    }

    private fun loadTvShows() {
        viewModel.loadTvShows()
    }
}