package com.tommannson.familycooking

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tommannson.familycooking.ui.theme.FamilyCookingTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream


class MainActivity : ComponentActivity() {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FamilyCookingTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val scope = rememberCoroutineScope()

                    Greeting("Android")
                    Button(
                        onClick = {
                            scope.launch(Dispatchers.IO) {
//                                val imageUri = Uri.fromFile(File("file:///android_asset/img.png"))
                                val imageUri = getUriFromAsset(this@MainActivity, "img_3.png")!!
                                val image = InputImage.fromFilePath(this@MainActivity, imageUri)

                                val result = recognizer.process(image)
                                    .addOnSuccessListener { visionText ->
                                        val blocks = visionText.textBlocks
                                        val text = visionText.text
                                        visionText.toString()
                                    }
                                    .addOnFailureListener { e ->
                                        // Task failed with an exception
                                        // ...
                                    }

                            }

                        }) {
                        Text(text = "sdfdsf")
                    }
                }
            }
        }
    }

    private fun getUriFromAsset(context: Context, assetFileName: String): Uri? {
        val assetManager = context.assets
        var inputStream: InputStream? = null
        var outputStream: FileOutputStream? = null
        var tempFile: File? = null

        return try {
            inputStream = assetManager.open(assetFileName)
            tempFile = File.createTempFile("temp_asset", null, context.cacheDir)
            outputStream = FileOutputStream(tempFile)

            inputStream.copyTo(outputStream)

            Uri.fromFile(tempFile)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        } finally {
            inputStream?.close()
            outputStream?.close()
            tempFile?.deleteOnExit()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Column {

        Text(
            text = "Hello $name!",
            modifier = modifier
        )
        
    }
}

@Preview
@Composable
fun GreetingPreview() {
    FamilyCookingTheme {
        Greeting("Android")
    }
}