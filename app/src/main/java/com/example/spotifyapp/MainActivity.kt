package com.example.spotifyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.spotifyapp.ui.theme.SpotifyAppTheme
import com.example.spotifyapp.view.AddReviewScreen
import com.example.spotifyapp.view.LoginScreen
import com.example.spotifyapp.view.RegisterScreen
import com.example.spotifyapp.view.ReviewsScreen
import com.example.spotifyapp.viewmodel.AuthViewModel
import com.example.spotifyapp.view.SongsScreen
import com.example.spotifyapp.viewmodel.ReviewsViewModel
import com.example.spotifyapp.viewmodel.SongsViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SpotifyAppTheme {
                val navController = rememberNavController()
                val authViewModel: AuthViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "login"
                ) {
                    composable("login") {
                        LoginScreen(
                            viewModel = authViewModel,
                            onLoginSuccess = {
                                navController.navigate("songs") {
                                    popUpTo("login") { inclusive = true }
                                }
                            },
                            onGoToRegister = {
                                navController.navigate("register")
                            }
                        )
                    }
                    composable("register") {
                        RegisterScreen(
                            viewModel = authViewModel,
                            onRegisterSuccess = {
                                navController.navigate("login") {
                                    popUpTo("register") { inclusive = true }
                                }
                            },
                            onGoToLogin = {
                                navController.popBackStack()
                            }
                        )
                    }
                    composable("songs") {
                        val songsViewModel: SongsViewModel = viewModel()
                        SongsScreen(
                            viewModel = songsViewModel,
                            onGoToReviews = { navController.navigate("reviews") }
                        )
                    }
                    composable("reviews") {
                        val reviewsViewModel: ReviewsViewModel = viewModel()
                        ReviewsScreen(
                            viewModel = reviewsViewModel,
                            onBack = { navController.popBackStack() },
                            onGoToAddReview = { navController.navigate("addReview") }
                        )
                    }
                    composable("addReview") {
                        val reviewsViewModel: ReviewsViewModel = viewModel()
                        AddReviewScreen(
                            viewModel = reviewsViewModel,
                            onBack = { navController.popBackStack() },
                            onReviewAdded = {
                                navController.navigate("reviews") {
                                    popUpTo("reviews") { inclusive = true }
                                }
                            }
                        )
                    }

                }
            }
        }
    }
}