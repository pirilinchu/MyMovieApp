package com.example.mymovieapp.ui.tv

import android.os.Bundle
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
import koleton.api.hideSkeleton
import koleton.api.loadSkeleton


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

        recyclerViewSeries.loadSkeleton(R.layout.movie_card)

        viewModel.tvShows.observe(viewLifecycleOwner, Observer {
            val recyclerViewSeriesAdapter: RecyclerViewSeriesAdapter = RecyclerViewSeriesAdapter(requireContext(), it)
            recyclerViewSeries.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            recyclerViewSeries.adapter = recyclerViewSeriesAdapter
        })

        viewModel.status.observe(viewLifecycleOwner, Observer {
            it?.let {
                if(it)
                {
                    viewModel._status.value = null
                    Toast.makeText(activity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        })

        viewModel.isLoading.observe(viewLifecycleOwner, Observer {
            if(it)
            {
                recyclerViewSeries.loadSkeleton(R.layout.serie_card)
            }
            else
            {
                recyclerViewSeries.hideSkeleton()
            }
        })

        return root
    }

    private fun loadTvShows() {
        viewModel.loadTvShows()
    }
}