package com.example.slideshow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SlideshowApp()
        }
    }
}

@Composable
fun SlideshowApp () {
    val images = listOf(
        R.drawable.bridge,
        R.drawable.sunset,
        R.drawable.mttam,
        R.drawable.gertrude,
        R.drawable.overlook
    )
    val captions = listOf(
        "Bayonne Bridge",
        "Sunset from West Side Highway",
        "Mount Tammany Peak",
        "Gertrude's Nose",
        "Overlook Mountain"
    )
    var currIndex by remember { mutableStateOf(0) }
    var pictureInput by remember { mutableStateOf("") }
    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(
            text = "Slideshow",
            fontSize = 25.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        SlideshowImage(image = images[currIndex], caption = captions[currIndex])
        Spacer(modifier = Modifier.height(10.dp))
        Text("Picture ${currIndex + 1}/${images.size}",
            fontSize = 15.sp)
        Spacer(modifier = Modifier.height(10.dp))

        Row {
            Button(onClick = {
                if (currIndex == 0) {
                    currIndex = images.size - 1
                } else {
                    currIndex--
                }
            }) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = {
                if (currIndex == images.size - 1) {
                    currIndex = 0
                } else {
                    currIndex++
                }
            }) {
                Text("Next")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            EditNumberField(
                value = pictureInput,
                onValueChange = { pictureInput = it },
                modifier = Modifier.width(160.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Button(onClick = {
                val number = pictureInput.toIntOrNull() ?: 0

                if (number in 1..images.size) {
                    currIndex = number - 1
                    pictureInput = ""
                }
            }) {
                Text("Go")
            }
        }
    }
}

@Composable
fun SlideshowImage (image: Int, caption: String) {
    Image(
        painter = painterResource(image),
        contentDescription = caption,
        modifier = Modifier.fillMaxWidth().height(250.dp),
        contentScale = ContentScale.Fit
    )
    Spacer(modifier = Modifier.height(8.dp))
    Text(text = caption,
        fontSize = 18.sp)
}

@Composable
fun EditNumberField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier,
        label = { Text("Picture #") },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            keyboardType = KeyboardType.Number,
            imeAction = ImeAction.Done
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SlideshowPreview() {
        SlideshowApp()
}