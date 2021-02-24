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

    private lateinit var recyclerView: RecyclerView
    private lateinit var movies: List<Movie>

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        var root: View = inflater.inflate(R.layout.fragment_movies, container, false)
        recyclerView = root.findViewById(R.id.recyclerViewNow)
        var recyclerViewAdapter: RecyclerViewAdapter = RecyclerViewAdapter(requireContext(), movies)
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = recyclerViewAdapter

        return root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        movies = DataManager.moviesNow
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