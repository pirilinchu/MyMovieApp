package com.example.mymovieapp.ui.dashboard

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mymovieapp.RecyclerViewSeriesAdapter
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.db.DataBase
import com.example.mymovieapp.models.DataManager
import com.example.mymovieapp.models.Movie
import com.example.mymovieapp.models.fromSerieResultToMovie
import com.example.mymovieapp.modelsApi.ApiResponseSerie
import com.example.mymovieapp.services.MoviesApi
import com.example.mymovieapp.services.ServiceBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TvViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: Repository
    private val _tvShows: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _status: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    val tvShows: LiveData<List<Movie>> = _tvShows
    val status: LiveData<Boolean> = _status

    init {
        val dao = DataBase.getDataBase(app).favorites()
        repository = Repository(dao)
    }

    fun loadTvShows() {
        var call = repository.getTvShows()
        var tvShows: List<Movie> = emptyList()

        call.enqueue(object : Callback<ApiResponseSerie> {
            override fun onResponse(
                call: Call<ApiResponseSerie>,
                response: Response<ApiResponseSerie>
            ) {
                var response = response.body()
                for (i in response!!.results) {
                    tvShows = tvShows.plus(fromSerieResultToMovie(i))
                }
                _tvShows.value = tvShows
            }

            override fun onFailure(call: Call<ApiResponseSerie>, t: Throwable) {
                _status.value = true
                Log.d("tag", t.message.toString())
            }
        })
    }
}