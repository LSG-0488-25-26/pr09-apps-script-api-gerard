package com.example.spotifyapp.model

data class SongsResponse(
    val songs: List<Song> = emptyList(),
    val total: Int = 0
)

data class ReviewsResponse(
    val reviews: List<Review> = emptyList(),
    val total: Int = 0
)

data class PostResponse(
    val success: Boolean = false,
    val message: String = ""
)