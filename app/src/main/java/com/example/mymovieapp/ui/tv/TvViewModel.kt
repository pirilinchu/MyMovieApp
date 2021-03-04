package com.example.mymovieapp.ui.tv

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.db.DataBase
import com.example.mymovieapp.data.models.Movie
import com.example.mymovieapp.data.models.fromSerieResultToMovie
import com.example.mymovieapp.data.modelsApi.ApiResponseSerie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvViewModel(app: Application) : AndroidViewModel(app) {


    private val repository: Repository
    private val _tvShows: MutableLiveData<List<Movie>> = MutableLiveData()
    val _status: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
    val _isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val tvShows: LiveData<List<Movie>> = _tvShows
    val status: LiveData<Boolean> = _status
    val isLoading: LiveData<Boolean> =  _isLoading

    init {
        val dao = DataBase.getDataBase(app).favorites()
        repository = Repository(dao)
    }

    fun loadTvShows() {
        var call = repository.getTvShows()
        var tvShows: List<Movie> = emptyList()
        _isLoading.value = true
        call.enqueue(object : Callback<ApiResponseSerie> {
            override fun onResponse(
                call: Call<ApiResponseSerie>,
                response: Response<ApiResponseSerie>
            ) {
                var response = response.body()
                if(response != null) {
                    for (i in response.results) {
                        tvShows = tvShows.plus(fromSerieResultToMovie(i))
                    }
                }
                _isLoading.value = false
                _tvShows.value = tvShows
            }

            override fun onFailure(call: Call<ApiResponseSerie>, t: Throwable) {
                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }
}