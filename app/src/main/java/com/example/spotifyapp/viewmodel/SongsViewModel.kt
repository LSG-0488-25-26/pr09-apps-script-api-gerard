package com.example.spotifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyapp.model.Song
import com.example.spotifyapp.repository.SongsRepository
import kotlinx.coroutines.launch

class SongsViewModel : ViewModel() {

    private val repository = SongsRepository()

    private val _songs = MutableLiveData<List<Song>>()
    val songs: LiveData<List<Song>> = _songs

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadSongs() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                android.util.Log.d("SongsVM", "Iniciant petició...")
                val result = repository.getSongs()
                android.util.Log.d("SongsVM", "Rebudes ${result.size} cançons")
                _songs.value = result
            } catch (e: Exception) {
                android.util.Log.e("SongsVM", "Error: ${e.message}")
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}