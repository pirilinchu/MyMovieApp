package com.example.mymovieapp.ui.dashboard

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

    private lateinit var recyclerViewSeries: RecyclerView
    private lateinit var series: List<Movie>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var root: View = inflater.inflate(R.layout.fragment_tv, container, false)
        recyclerViewSeries = root.findViewById(R.id.recyclerViewSeries)

        var recyclerViewAdapter: RecyclerViewSeriesAdapter = RecyclerViewSeriesAdapter(requireContext(), series)

        recyclerViewSeries.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerViewSeries.adapter = recyclerViewAdapter
        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(DataManager.series.size == 0) getShows()
        series = DataManager.series
    }

    private fun getShows() {
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getSeries()
        var series = ArrayList<Movie>()

        call.enqueue(object : Callback<ApiResponseSerie> {
            override fun onResponse(
                call: Call<ApiResponseSerie>,
                response: Response<ApiResponseSerie>
            ) {
                Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
                var response = response.body()
                for (i in response!!.results) {
                    series.add(fromSerieResultToMovie(i))
                }
                DataManager.series = series

                var recyclerViewAdapter: RecyclerViewSeriesAdapter = RecyclerViewSeriesAdapter(requireContext(), series)
                recyclerViewSeries.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                recyclerViewSeries.adapter = recyclerViewAdapter
            }

            override fun onFailure(call: Call<ApiResponseSerie>, t: Throwable) {
                Toast.makeText(activity, t.message, Toast.LENGTH_SHORT).show()
                Log.d("tag", t.message.toString())
            }
        })
    }
}