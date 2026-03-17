package com.example.spotifyapp.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotifyapp.ui.theme.SpotifyGreen
import com.example.spotifyapp.ui.theme.SpotifyMediumGray
import com.example.spotifyapp.ui.theme.SpotifyTextGray
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
            "post_ok"      -> onReviewAdded()
            "error_fields" -> errorMessage = "Omple els camps obligatoris"
            "error_server" -> errorMessage = "Error en enviar la review"
        }
    }

    val textFieldColors = OutlinedTextFieldDefaults.colors(
        focusedBorderColor = SpotifyGreen,
        focusedLabelColor = SpotifyGreen,
        cursorColor = SpotifyGreen,
        unfocusedContainerColor = SpotifyMediumGray,
        focusedContainerColor = SpotifyMediumGray,
        unfocusedBorderColor = MaterialTheme.colorScheme.outline
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Review", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Enrere",
                            tint = MaterialTheme.colorScheme.onBackground
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Escriu la teva review",
                fontSize = 14.sp,
                color = SpotifyTextGray
            )

            OutlinedTextField(
                value = trackName,
                onValueChange = { trackName = it; errorMessage = "" },
                label = { Text("Nom de la cançó *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors,
                singleLine = true
            )

            OutlinedTextField(
                value = artistName,
                onValueChange = { artistName = it; errorMessage = "" },
                label = { Text("Artista *") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors,
                singleLine = true
            )

            OutlinedTextField(
                value = rating,
                onValueChange = { rating = it; errorMessage = "" },
                label = { Text("Puntuació (1-10) *") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors,
                singleLine = true
            )

            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Comentari (opcional)") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors,
                minLines = 3,
                maxLines = 5
            )

            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 13.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Button(
                onClick = {
                    errorMessage = ""
                    viewModel.addReview(trackName, artistName, rating, comment)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50.dp),
                enabled = !isLoading,
                colors = ButtonDefaults.buttonColors(
                    containerColor = SpotifyGreen,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                    disabledContainerColor = SpotifyGreen.copy(alpha = 0.4f)
                )
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(
                        text = "Enviar Review",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
            }
        }
    }
}