package com.example.mymovieapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.GridView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
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

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        var gridView: GridView = root.findViewById(R.id.gridViewFavorites)

        viewModel.favorites.observe(viewLifecycleOwner, Observer {
            var gridAdapter: GridViewAdapter = GridViewAdapter(requireContext(), it)
            gridView.adapter = gridAdapter
        })



        return root
    }
}