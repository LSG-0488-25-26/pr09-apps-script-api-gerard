package com.example.spotifyapp.network

import com.example.spotifyapp.model.PostResponse
import com.example.spotifyapp.model.SongsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.spotifyapp.model.ReviewsResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @GET("exec")
    suspend fun getSongs(
        @Query("api_key") apiKey: String,
        @Query("endpoint") endpoint: String
    ): SongsResponse

    @GET("exec")
    suspend fun getReviews(
        @Query("api_key") apiKey: String,
        @Query("endpoint") endpoint: String
    ): ReviewsResponse

    @POST("exec")
    suspend fun addReview(
        @Body body: Map<String, String>
    ): PostResponse
}

