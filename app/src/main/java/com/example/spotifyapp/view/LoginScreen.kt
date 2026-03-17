package com.example.spotifyapp.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.spotifyapp.ui.theme.SpotifyGreen
import com.example.spotifyapp.ui.theme.SpotifyMediumGray
import com.example.spotifyapp.ui.theme.SpotifyTextGray
import com.example.spotifyapp.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onGoToRegister: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val authResult by viewModel.authResult.observeAsState()

    LaunchedEffect(authResult) {
        when (authResult) {
            "login_ok"           -> onLoginSuccess()
            "error_fields"       -> errorMessage = "Omple tots els camps"
            "error_credentials"  -> errorMessage = "Usuari o contrasenya incorrectes"
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo / títol
            Text(
                text = "🎵",
                fontSize = 56.sp,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Spotify App",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Inicia sessió per continuar",
                style = MaterialTheme.typography.bodyMedium,
                color = SpotifyTextGray
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Camps
            OutlinedTextField(
                value = username,
                onValueChange = { username = it; errorMessage = "" },
                label = { Text("Usuari") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SpotifyGreen,
                    focusedLabelColor = SpotifyGreen,
                    cursorColor = SpotifyGreen,
                    unfocusedContainerColor = SpotifyMediumGray,
                    focusedContainerColor = SpotifyMediumGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it; errorMessage = "" },
                label = { Text("Contrasenya") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = SpotifyGreen,
                    focusedLabelColor = SpotifyGreen,
                    cursorColor = SpotifyGreen,
                    unfocusedContainerColor = SpotifyMediumGray,
                    focusedContainerColor = SpotifyMediumGray
                ),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (errorMessage.isNotBlank()) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.login(username, password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SpotifyGreen,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            ) {
                Text(
                    text = "Iniciar sessió",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(onClick = onGoToRegister) {
                Text(
                    text = "No tens compte? ",
                    color = SpotifyTextGray
                )
                Text(
                    text = "Registra't",
                    color = SpotifyGreen,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}