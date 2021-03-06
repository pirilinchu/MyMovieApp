package com.example.mymovieapp.ui.movies

import android.app.Application
import android.preference.PreferenceManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.db.DataBase
import com.example.mymovieapp.data.models.Movie
import com.example.mymovieapp.data.models.fromResultToMovie
import com.example.mymovieapp.data.modelsApi.ApiResponse
import com.example.mymovieapp.data.modelsApi.ApiResponseSerie
import com.example.mymovieapp.keyIncoming
import com.example.mymovieapp.keyNow
import com.example.mymovieapp.keyPopular
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesViewModel(app: Application) : AndroidViewModel(app) {

    val preferences = PreferenceManager.getDefaultSharedPreferences(app)

    private val repository: Repository
    private val _moviesNow: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _moviesPopular: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _moviesIncoming: MutableLiveData<List<Movie>> = MutableLiveData()
    val _status: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    val moviesNow: LiveData<List<Movie>> = _moviesNow
    val moviesPopular: LiveData<List<Movie>> = _moviesPopular
    val moviesIncoming: LiveData<List<Movie>> = _moviesIncoming
    val status: LiveData<Boolean> = _status

    init {
        val dao = DataBase.getDataBase(app).favorites()
        repository = Repository(dao)
    }

    fun getMoviesNow(){
        var call = repository.getMoviesNow()
        var moviesNow: List<Movie> = emptyList()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                if(response != null) {
                    saveCache(response, keyNow)
                    for (i in response.results){
                        moviesNow = moviesNow.plus(fromResultToMovie(i))
                    }
                }
                _moviesNow.value = moviesNow
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val response = getCache(keyNow)

                for (i in response.results){
                    moviesNow = moviesNow.plus(fromResultToMovie(i))
                }
                _moviesNow.value = moviesNow

                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun getMoviesIncoming() {
        var call = repository.getMoviesIncoming()
        var moviesIncoming: List<Movie> = emptyList()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                if(response != null) {
                    saveCache(response, keyIncoming)
                    for (i in response.results){
                        moviesIncoming = moviesIncoming.plus(fromResultToMovie(i))
                    }
                }
                _moviesIncoming.value = moviesIncoming
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val response = getCache(keyIncoming)

                for (i in response.results){
                    moviesIncoming = moviesIncoming.plus(fromResultToMovie(i))
                }
                _moviesIncoming.value = moviesIncoming

                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun getMoviesPopular() {
        var call = repository.getMoviesPopular()
        var moviesPopular: List<Movie> = emptyList()

        call.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(
                call: Call<ApiResponse>,
                response: Response<ApiResponse>
            ) {
                var response = response.body()
                if(response != null) {
                    saveCache(response, keyPopular)
                    for (i in response!!.results){
                        moviesPopular = moviesPopular.plus(fromResultToMovie(i))
                    }
                }
                _moviesPopular.value = moviesPopular
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                val response = getCache(keyPopular)

                for (i in response!!.results) {
                    moviesPopular = moviesPopular.plus(fromResultToMovie(i))
                }
                _moviesPopular.value = moviesPopular
                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun saveCache (response: ApiResponse, key: String) {
        var editor = preferences.edit()
        var gson = Gson()
        var jsonString = gson.toJson(response)

        editor.putString(key, jsonString)
        editor.apply()
    }

    private fun getCache(key: String): ApiResponse {
        var responseString = preferences.getString(key, "None")
        var gson = Gson()
        return gson.fromJson(responseString, ApiResponse::class.java)
    }


}