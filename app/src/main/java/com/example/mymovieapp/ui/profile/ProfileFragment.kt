package com.example.mymovieapp.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.mymovieapp.GridViewAdapter
import com.example.mymovieapp.R

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