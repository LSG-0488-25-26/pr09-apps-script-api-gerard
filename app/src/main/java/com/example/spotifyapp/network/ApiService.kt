package com.example.spotifyapp.network

import com.example.spotifyapp.model.SongsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("exec")
    suspend fun getSongs(
        @Query("api_key") apiKey: String,
        @Query("endpoint") endpoint: String
    ): SongsResponse
}

