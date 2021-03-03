package com.example.mymovieapp.data.modelsApi

data class ApiResponseSerie(
    val dates: Dates,
    val page: Int,
    val results: List<ResultSerie>,
    val total_pages: Int,
    val total_results: Int
)