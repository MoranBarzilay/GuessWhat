package com.example.guesswhat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.example.guesswhat.ui.theme.GuessWhatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GuessWhatTheme {
                // Call the NavigationController function
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(Color(0xFF81D4FA), Color(0xFFA5D6A7)), // Purple gradient
                                    startY = 0f, // Start the gradient from the top
                                    endY = Float.POSITIVE_INFINITY // Extend to the bottom
                                )
                            )
                            .fillMaxSize() // Ensures the gradient takes up the full screen
                    ) {
                        NavigationController() // Your main content
                    }
                }
            }
        }
    }
}

@Composable
fun NavigationController() {
    // This variable will hold the current page
    var currentPage by remember { mutableStateOf("MainPage") }

    // Depending on the value of currentPage, display the corresponding page
    when (currentPage) {
        "MainPage" -> MainPage(
            onStartGameClick = { currentPage = "GamePage" },
            onSettingsClick = { currentPage = "SettingsPage" }
        )
        "GamePage" -> GamePage(onBackClick = { currentPage = "MainPage" })
        "SettingsPage" -> SettingsPage(onBackClick = { currentPage = "MainPage" })
    }
}

@Composable
fun MainPage(onStartGameClick: () -> Unit, onSettingsClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = onStartGameClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text("התחל משחק")
        }

        Button(
            onClick = onSettingsClick,
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.7f)
        ) {
            Text("הגדרות")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainPagePreview() {
    GuessWhatTheme {
        MainPage(onStartGameClick = {}, onSettingsClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun GamePagePreview() {
    GuessWhatTheme {
        GamePage(onBackClick = {})
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPagePreview() {
    GuessWhatTheme {
        SettingsPage(onBackClick = {})
    }
}
