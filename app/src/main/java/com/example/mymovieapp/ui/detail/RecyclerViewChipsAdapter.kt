package com.example.mymovieapp.ui.detail

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mymovieapp.R
import com.google.android.material.chip.Chip

class RecyclerViewChipsAdapter(
    private val context: Context,
    private val tags: List<String>
) : RecyclerView.Adapter<RecyclerViewChipsAdapter.ViewHolder>() {

    inner class ViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
        var text: Chip

        init {
            text = itemView.findViewById(R.id.textViewChip)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.chip, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text = tags[position]
    }
}