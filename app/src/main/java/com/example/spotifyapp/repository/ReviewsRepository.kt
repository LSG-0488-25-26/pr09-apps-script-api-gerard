package com.example.spotifyapp.repository

import com.example.spotifyapp.BuildConfig
import com.example.spotifyapp.model.Review
import com.example.spotifyapp.network.RetrofitInstance

class ReviewsRepository {

    suspend fun getReviews(): List<Review> {
        return RetrofitInstance.api.getReviews(
            apiKey = BuildConfig.API_KEY,
            endpoint = "reviews"
        ).reviews
    }
}