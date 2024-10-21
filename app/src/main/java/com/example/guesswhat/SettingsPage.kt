package com.example.guesswhat

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SettingsPage(onBackClick: () -> Unit) {
    // SharedPreferences for saving data
    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("GameSettings", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()

    var gameDuration by remember { mutableStateOf(sharedPreferences.getInt("gameDuration", 60).toString()) }
    var customWord by remember { mutableStateOf("") }
    val customWords = remember { mutableStateListOf(*sharedPreferences.getStringSet("customWords", emptySet())?.toTypedArray() ?: arrayOf()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Row for the "Game Duration" label and the input field
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(0.7f)
        ) {
            Text(
                text = "זמן משחק (שניות):",
                modifier = Modifier.padding(end = 8.dp),
                fontSize = 18.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(32.dp))
                    .background(Color.LightGray)
                    .padding(8.dp)
                    .fillMaxWidth() // Full width within the Row
            ) {
                BasicTextField(
                    value = gameDuration,
                    onValueChange = { gameDuration = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(
                        textAlign = TextAlign.Center, // Center the text
                        fontSize = 18.sp
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Input for Adding Custom Words
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(32.dp))
                .background(Color.LightGray)
                .padding(8.dp)
                .fillMaxWidth(0.7f) // Set maximum width for input field
        ) {
            BasicTextField(
                value = customWord,
                onValueChange = { customWord = it },
                textStyle = LocalTextStyle.current.copy(
                    textAlign = TextAlign.Center, // Center the text
                    fontSize = 18.sp
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                decorationBox = { innerTextField ->
                    // If customWord is empty, show the placeholder
                    if (customWord.isEmpty()) {
                        Text(
                            text = "הכנס מילה מותאמת אישית",  // Your placeholder text
                            style = LocalTextStyle.current.copy(
                                color = Color.Gray,  // Placeholder color
                                textAlign = TextAlign.Center, // Center the placeholder text
                                fontSize = 18.sp
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    innerTextField() // Display the actual text field
                }
            )
        }

        Button(onClick = {
            if (customWord.isNotBlank()) {
                customWords.add(customWord)
                customWord = ""
            }
        }) {
            Text("הוסף מילה")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Back Button
        Button(onClick = {
            // Save game duration and custom words to SharedPreferences
            editor.putInt("gameDuration", gameDuration.toInt())
            editor.putStringSet("customWords", customWords.toSet())
            editor.apply()
            onBackClick()
        }) {
            Text("חזרה לעמוד הראשי")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("מילים מותאמות אישית:")

        Spacer(modifier = Modifier.height(16.dp))

        // Add a scrollable container for custom words and center them with a gradient indication
        Box(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .height(200.dp) // Set a height to allow scrolling when words overflow
        ) {
            val scrollState = rememberScrollState()

            // This is the gradient at the top and bottom to indicate scrolling
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.horizontalGradient(
                            colors = listOf(Color.Gray.copy(alpha = 0.3f), Color.Transparent, Color.Gray.copy(alpha = 0.3f)),
                            startX = 0f, endX = Float.POSITIVE_INFINITY
                        )
                    )
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState), // Enable vertical scrolling
                horizontalAlignment = Alignment.CenterHorizontally // Center the words
            ) {
                for (word in customWords) {
                    Text(
                        text = word,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                            .clickable {
                                customWords.remove(word) // Remove word when clicked
                            },
                        fontSize = 18.sp,
                        color = Color.Black,
                        textAlign = TextAlign.Center // Center the text inside the column
                    )
                }
            }
        }
    }
}
