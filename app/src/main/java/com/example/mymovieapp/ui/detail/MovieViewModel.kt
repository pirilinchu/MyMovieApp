package com.example.mymovieapp.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.db.DataBase
import com.example.mymovieapp.data.models.Movie
import com.example.mymovieapp.data.models.fromDetailMovieToMovie
import com.example.mymovieapp.data.models.fromDetailSerieToMovie
import com.example.mymovieapp.data.modelsApi.*
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
    private val _director: MutableLiveData<String> = MutableLiveData()
    private val _currentMovieOMDB: MutableLiveData<MovieIMDB> = MutableLiveData()
    private val _cast: MutableLiveData<List<Cast>> = MutableLiveData()
    val _status: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }
    val _isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply { value = true }
    val _videoId: MutableLiveData<String> = MutableLiveData()


    val currentMovie: LiveData<MovieDetail> =_currentMovie
    val currentSerie: LiveData<SerieDetail> =_currentSerie
    val tags: LiveData<List<String>> = _tags
    val title: LiveData<String> = _title
    val image: LiveData<String> = _image
    val description: LiveData<String> = _description
    val rating: LiveData<Double> = _rating
    val currentMovieOMDB: LiveData<MovieIMDB> =_currentMovieOMDB
    val cast: LiveData<List<Cast>> = _cast
    val status: LiveData<Boolean> = _status
    val director: LiveData<String> = _director
    val isLoading: LiveData<Boolean> =  _isLoading
    val videoId: LiveData<String> = _videoId

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
                loadTags(currentMovie.value?.genres ?: emptyList())
                loadMovieData()
                loadMovieCredits()
                loadAditionalInfo()
                loadMovieTrailer()
            }

            override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    private fun loadMovieTrailer() {
        var call = repository.getTrailer(currentMovie.value!!.id)

        call.enqueue(object : Callback<MovieTrailers> {
            override fun onResponse(
                call: Call<MovieTrailers>,
                response: Response<MovieTrailers>
            ) {
                var movieTrailers: MovieTrailers = response.body()!!
                _videoId.value = movieTrailers.results[0].key
            }

            override fun onFailure(call: Call<MovieTrailers>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    private fun loadSerieTrailer() {
        var call = repository.getTrailerSerie(currentSerie.value!!.id)

        call.enqueue(object : Callback<MovieTrailers> {
            override fun onResponse(
                call: Call<MovieTrailers>,
                response: Response<MovieTrailers>
            ) {
                var movieTrailers: MovieTrailers = response.body()!!
                _videoId.value = movieTrailers.results[0].key
            }

            override fun onFailure(call: Call<MovieTrailers>, t: Throwable) {
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
                loadSerieCredits()
                loadSerieTrailer()
            }

            override fun onFailure(call: Call<SerieDetail>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun loadAditionalInfo() {
        var call = repository.getAdditionalInfo(currentMovie.value!!.imdb_id)

        call.enqueue(object : Callback<MovieIMDB> {
            override fun onResponse(
                    call: Call<MovieIMDB>,
                    response: Response<MovieIMDB>
            ) {
                _currentMovieOMDB.value = response.body()
                _director.value = currentMovieOMDB.value?.Director ?: "No Director"
                _rating.value = currentMovieOMDB.value?.imdbRating!!.toDouble() ?: 0.0
                _isLoading.value = false
            }

            override fun onFailure(call: Call<MovieIMDB>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun loadMovieCredits() {
        var call = repository.getMovieCredits(currentMovie.value!!.id)

        call.enqueue(object : Callback<MovieCredits> {
            override fun onResponse(
                    call: Call<MovieCredits>,
                    response: Response<MovieCredits>
            ) {
                _cast.value = response.body()?.cast
            }

            override fun onFailure(call: Call<MovieCredits>, t: Throwable) {
                Log.d("tag", t.message.toString())
            }
        })
    }

    fun loadSerieCredits() {
        var call = repository.getSerieCredits(currentSerie.value!!.id)

        call.enqueue(object : Callback<MovieCredits> {
            override fun onResponse(
                    call: Call<MovieCredits>,
                    response: Response<MovieCredits>
            ) {
                _cast.value = response.body()?.cast
                _isLoading.value = false
            }

            override fun onFailure(call: Call<MovieCredits>, t: Throwable) {
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
        _title.value = currentMovie.value?.original_title ?: "No Title"
        _image.value = currentMovie.value?.poster_path ?: "No Image"
        _description.value = currentMovie.value?.overview ?: "No Description"
    }

    private fun loadSerieData() {
        _title.value = currentSerie.value?.original_name ?: "No Title"
        _image.value = currentSerie.value?.poster_path ?: "No Image"
        _description.value = currentSerie.value?.overview ?: "No Description"
        _rating.value = currentSerie.value?.vote_average ?: 0.0
    }
}