package com.example.mymovieapp.ui.notifications

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.db.DataBase
import com.example.mymovieapp.models.Movie
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: Repository
    private val _status: MutableLiveData<Boolean> = MutableLiveData<Boolean>().apply {
        value = false
    }

    val favorites: LiveData<List<Movie>>
    val status: LiveData<Boolean> = _status

    init {
        val dao = DataBase.getDataBase(app).favorites()
        repository = Repository(dao)
        favorites = repository.favoriteMovies
    }

    fun insert(movie: Movie) {
        viewModelScope.launch {
            repository.insert(movie)
        }
    }

    fun delete(movie: Movie) {
        viewModelScope.launch {
            repository.delete(movie)
        }
    }
}