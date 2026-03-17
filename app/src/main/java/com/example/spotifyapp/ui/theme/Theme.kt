package com.example.spotifyapp.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val SpotifyDarkColorScheme = darkColorScheme(
    primary            = SpotifyGreen,
    onPrimary          = SpotifyBlack,
    primaryContainer   = SpotifyGreenDark,
    onPrimaryContainer = SpotifyWhite,

    background         = SpotifyBlack,
    onBackground       = SpotifyWhite,

    surface            = SpotifyDarkGray,
    onSurface          = SpotifyWhite,
    surfaceVariant     = SpotifyMediumGray,
    onSurfaceVariant   = SpotifyTextGray,

    secondary          = SpotifyGreen,
    onSecondary        = SpotifyBlack,

    error              = SpotifyError,
    onError            = SpotifyWhite,

    outline            = SpotifyLightGray,
    outlineVariant     = SpotifyMediumGray
)

@Composable
fun SpotifyAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SpotifyDarkColorScheme,
        typography  = Typography,
        content     = content
    )
}