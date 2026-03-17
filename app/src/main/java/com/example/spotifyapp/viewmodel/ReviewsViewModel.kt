package com.example.spotifyapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotifyapp.model.Review
import com.example.spotifyapp.repository.ReviewsRepository
import kotlinx.coroutines.launch

class ReviewsViewModel : ViewModel() {

    private val repository = ReviewsRepository()

    private val _reviews = MutableLiveData<List<Review>>()
    val reviews: LiveData<List<Review>> = _reviews

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun loadReviews() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val result = repository.getReviews()
                _reviews.value = result
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}