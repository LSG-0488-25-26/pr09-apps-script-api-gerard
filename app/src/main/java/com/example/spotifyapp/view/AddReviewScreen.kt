package com.example.spotifyapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifyapp.viewmodel.ReviewsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddReviewScreen(
    viewModel: ReviewsViewModel,
    onBack: () -> Unit,
    onReviewAdded: () -> Unit
) {
    var trackName by remember { mutableStateOf("") }
    var artistName by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf("") }
    var comment by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val postResult by viewModel.postResult.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)

    LaunchedEffect(postResult) {
        when (postResult) {
            "post_ok" -> onReviewAdded()
            "error_fields" -> errorMessage = "Omple els camps obligatoris"
            "error_server" -> errorMessage = "Error en enviar la review"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Review") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Enrere")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = trackName,
                onValueChange = { trackName = it },
                label = { Text("Nom de la cançó *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = artistName,
                onValueChange = { artistName = it },
                label = { Text("Artista *") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = rating,
                onValueChange = { rating = it },
                label = { Text("Puntuació (1-10) *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentari (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )

            if (errorMessage.isNotBlank()) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }

            Button(
                onClick = {
                    errorMessage = ""
                    viewModel.addReview(trackName, artistName, rating, comment)
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text("Enviar Review")
                }
            }
        }
    }
}