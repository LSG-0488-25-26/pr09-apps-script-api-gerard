package com.example.spotifyapp.model

import com.google.gson.annotations.SerializedName

data class Song(
    @SerializedName("Track") val track_name: String = "",
    @SerializedName("Artist") val artist_name: String = "",
    @SerializedName("Album Name") val album_name: String = "",
    @SerializedName("Release Date") val release_date: String = "",
    @SerializedName("Spotify Popularity") val spotify_popularity: String = "",
    @SerializedName("Spotify Streams") val streams: String = ""
)