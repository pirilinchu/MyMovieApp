package com.example.mymovieapp.data.modelsApi

data class MovieCredits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)