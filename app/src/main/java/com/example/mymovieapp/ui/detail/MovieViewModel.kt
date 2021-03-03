package com.example.mymovieapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.db.DataBase
import com.example.mymovieapp.data.models.fromDetailMovieToMovie
import com.example.mymovieapp.data.models.fromDetailSerieToMovie
import com.example.mymovieapp.data.modelsApi.Genre
import com.example.mymovieapp.data.modelsApi.MovieDetail
import com.example.mymovieapp.data.modelsApi.SerieDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: Repository
    private val _currentMovie: MutableLiveData<MovieDetail> = MutableLiveData()
    private val _currentSerie: MutableLiveData<SerieDetail> = MutableLiveData()
    private val _tags: MutableLiveData<List<String>> = MutableLiveData()
    private val _title: MutableLiveData<String> = MutableLiveData()
    private val _image: MutableLiveData<String> = MutableLiveData()
    private val _description: MutableLiveData<String> = MutableLiveData()
    private val _rating: MutableLiveData<Double> = MutableLiveData()

    val currentMovie: LiveData<MovieDetail> =_currentMovie
    val currentSerie: LiveData<SerieDetail> =_currentSerie
    val tags: LiveData<List<String>> = _tags
    val title: LiveData<String> = _title
    val image: LiveData<String> = _image
    val description: LiveData<String> = _description
    val rating: LiveData<Double> = _rating

    init {
        val dao = DataBase.getDataBase(app).favorites()
        repository = Repository(dao)
    }

    suspend fun insertMovie()
    {
        repository.insert(fromDetailMovieToMovie(currentMovie.value!!))
    }

    suspend fun insertSerie()
    {
        repository.insert(fromDetailSerieToMovie(currentSerie.value!!))
    }

    fun loadMovie(id: Int){
        var call = repository.getMovieById(id)

        call.enqueue(object : Callback<MovieDetail> {
            override fun onResponse(
                    call: Call<MovieDetail>,
                    response: Response<MovieDetail>
            ) {
                _currentMovie.value = response.body()
                loadTags(currentMovie.value!!.genres)
                loadMovieData()
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun loadSerie(id: Int){
        var call = repository.getSerieById(id)

        call.enqueue(object : Callback<SerieDetail> {
            override fun onResponse(
                    call: Call<SerieDetail>,
                    response: Response<SerieDetail>
            ) {
                _currentSerie.value = response.body()
                loadSerieData()
            }

            override fun onFailure(call: Call<SerieDetail>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    private fun loadTags (genres: List<Genre>) {
        var tags: List<String> = emptyList()
        for (i in genres)
        {
            tags = tags.plus(i.name)
        }
        _tags.value = tags
    }

    private fun loadMovieData() {
        _title.value = currentMovie.value?.original_title
        _image.value = currentMovie.value?.poster_path
        _description.value = currentMovie.value?.overview
        _rating.value = currentMovie.value?.vote_average
    }

    private fun loadSerieData() {
        _title.value = currentSerie.value?.original_name
        _image.value = currentSerie.value?.poster_path
        _description.value = currentSerie.value?.overview
        _rating.value = currentSerie.value?.vote_average
    }

}