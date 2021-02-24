package com.example.mymovieapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.RecyclerViewAdapter
import com.example.mymovieapp.RecyclerViewSeriesAdapter
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie

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

        series = DataManager.series
    }
}