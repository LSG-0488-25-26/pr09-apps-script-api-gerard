package com.example.spotifyapp.repository

import com.example.spotifyapp.BuildConfig
import com.example.spotifyapp.model.Song
import com.example.spotifyapp.network.RetrofitInstance

class SongsRepository {

    suspend fun getSongs(): List<Song> {
        return RetrofitInstance.api.getSongs(
            apiKey = BuildConfig.API_KEY,
            endpoint = "songs"
        ).songs
    }
}