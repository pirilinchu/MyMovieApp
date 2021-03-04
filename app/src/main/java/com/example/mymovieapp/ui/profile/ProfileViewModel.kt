package com.example.mymovieapp.ui.profile

import android.app.Application
import androidx.lifecycle.*
import com.example.mymovieapp.data.Repository
import com.example.mymovieapp.data.db.DataBase
import com.example.mymovieapp.data.models.Movie
import kotlinx.coroutines.launch

class ProfileViewModel(app: Application) : AndroidViewModel(app) {

    private val repository: Repository

    val favorites: LiveData<List<Movie>>

    init {
        val dao = DataBase.getDataBase(app).favorites()
        repository = Repository(dao)
        favorites = repository.favoriteMovies
    }
}