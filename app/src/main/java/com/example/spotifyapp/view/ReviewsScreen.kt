package com.example.spotifyapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifyapp.model.Review
import com.example.spotifyapp.ui.theme.SpotifyGreen
import com.example.spotifyapp.ui.theme.SpotifyMediumGray
import com.example.spotifyapp.ui.theme.SpotifyTextGray
import com.example.spotifyapp.viewmodel.ReviewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewsScreen(
    viewModel: ReviewsViewModel = viewModel(),
    onBack: () -> Unit,
    onGoToAddReview: () -> Unit
) {
    val reviews by viewModel.reviews.observeAsState(emptyList())
    val isLoading by viewModel.isLoading.observeAsState(false)
    val error by viewModel.error.observeAsState("")

    LaunchedEffect(Unit) { viewModel.loadReviews() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Reviews", fontWeight = FontWeight.Bold)
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Enrere",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }
                },
                actions = {
                    TextButton(onClick = onGoToAddReview) {
                        Text(
                            text = "+ Nova",
                            color = SpotifyGreen,
                            fontWeight = FontWeight.Bold
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        when {
            isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = SpotifyGreen)
                }
            }
            error.isNotBlank() -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(error, color = MaterialTheme.colorScheme.error)
                }
            }
            reviews.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎵", fontSize = 48.sp)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            "Encara no hi ha reviews",
                            color = SpotifyTextGray,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(onClick = onGoToAddReview) {
                            Text("Afegeix la primera", color = SpotifyGreen)
                        }
                    }
                }
            }
            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(reviews) { review ->
                        ReviewItem(review = review)
                    }
                }
            }
        }
    }
}

@Composable
fun ReviewItem(review: Review) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SpotifyMediumGray)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = review.track_name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = review.artist_name,
                        fontSize = 13.sp,
                        color = SpotifyTextGray
                    )
                }
                Text(
                    text = "⭐ ${review.rating}/10",
                    fontSize = 13.sp,
                    color = SpotifyGreen,
                    fontWeight = FontWeight.Bold
                )
            }

            if (review.comment.isNotBlank()) {
                Spacer(modifier = Modifier.height(10.dp))
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 0.5.dp)
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "\"${review.comment}\"",
                    fontSize = 13.sp,
                    color = SpotifyTextGray,
                    lineHeight = 18.sp
                )
            }
        }
    }
}