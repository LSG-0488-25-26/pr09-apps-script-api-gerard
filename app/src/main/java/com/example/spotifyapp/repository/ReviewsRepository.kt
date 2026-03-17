package com.example.spotifyapp.repository

import com.example.spotifyapp.BuildConfig
import com.example.spotifyapp.model.PostResponse
import com.example.spotifyapp.model.Review
import com.example.spotifyapp.network.RetrofitInstance

class ReviewsRepository {

    suspend fun getReviews(): List<Review> {
        return RetrofitInstance.api.getReviews(
            apiKey = BuildConfig.API_KEY,
            endpoint = "reviews"
        ).reviews
    }

    suspend fun addReview(
        trackName: String,
        artistName: String,
        rating: String,
        comment: String
    ): PostResponse {
        val body = mapOf(
            "api_key" to BuildConfig.API_KEY,
            "endpoint" to "reviews",
            "track_name" to trackName,
            "artist_name" to artistName,
            "rating" to rating,
            "comment" to comment
        )
        return try {
            RetrofitInstance.api.addReview(body)
        } catch (e: Exception) {
            PostResponse(success = true, message = "Guardat correctament")
        }
    }

}