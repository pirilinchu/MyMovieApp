package com.example.mymovieapp.ui.home

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.RecyclerViewAdapter
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.models.fromResultToMovie
import com.example.mymovieapp.modelsApi.ApiResponse
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel : ViewModel() {

    private val _moviesNow: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _moviesPopular: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _moviesIncoming: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _status: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    val moviesNow: LiveData<List<Movie>> = _moviesNow
    val moviesPopular: LiveData<List<Movie>> = _moviesPopular
    val moviesIncoming: LiveData<List<Movie>> = _moviesIncoming
    val status: LiveData<Boolean> = _status

    fun getMoviesNow(){
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMoviesNow()
        var moviesNow: List<Movie> = emptyList()
        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                for (i in response!!.results){
                    moviesNow = moviesNow.plus(fromResultToMovie(i))
                }
                _moviesNow.value = moviesNow
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun getMoviesIncoming() {
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMoviesIncoming()
        var moviesIncoming: List<Movie> = emptyList()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                for (i in response!!.results){
                    moviesIncoming = moviesIncoming.plus(fromResultToMovie(i))
                }
                _moviesIncoming.value = moviesIncoming
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun getMoviesPopular() {
        var moviesService = ServiceBuilder.buildService(MoviesApi::class.java)
        var call = moviesService.getMoviesPopular()
        var moviesPopular: List<Movie> = emptyList()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                for (i in response!!.results){
                    moviesPopular = moviesPopular.plus(fromResultToMovie(i))
                }
                _moviesPopular.value = moviesPopular
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }


}