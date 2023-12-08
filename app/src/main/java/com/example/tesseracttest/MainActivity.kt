package com.example.tesseracttest

import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tesseracttest.ui.theme.TesseractTestTheme
import com.googlecode.tesseract.android.TessBaseAPI
import java.io.InputStream


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TesseractTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val tess = TessBaseAPI()

                    val dataPath: String = filesDir.absolutePath

                    if (!tess.init(
                            dataPath,
                            "por"
                        )
                    ) { // could be multiple languages, like "eng+deu+fra"
                        // Error initializing Tesseract (wrong/inaccessible data path or not existing language file(s))
                        // Release the native Tesseract instance
                        tess.recycle();
                    }

                    val assetManager: AssetManager = assets
                    val inputStream: InputStream = assetManager.open("123.png")
                    val bitmap: Bitmap = BitmapFactory.decodeStream(inputStream)
                    tess.setImage(bitmap)

                    val text = tess.utF8Text

                    Greeting(text)

                    tess.recycle();
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TesseractTestTheme {
        Greeting("Android")
    }
}