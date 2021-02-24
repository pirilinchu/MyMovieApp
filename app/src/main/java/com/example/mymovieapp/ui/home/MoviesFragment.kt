package com.example.mymovieapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.example.mymovieapp.RecyclerViewAdapter
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie

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

        moviesNow = DataManager.moviesNow
        moviesPopular = DataManager.moviesPopular
        moviesIncoming = DataManager.moviesIncoming
    }
}

//class MoviesFragment : Fragment() {
//
//    private lateinit var moviesViewModel: MoviesViewModel
//
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        moviesViewModel =
//            ViewModelProvider(this).get(MoviesViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_movies, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        moviesViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }
//}