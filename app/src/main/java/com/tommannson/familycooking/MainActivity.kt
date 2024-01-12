package com.tommannson.familycooking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.dhaval2404.imagepicker.ImagePicker
import com.tommannson.familycooking.infrastructure.InfraProvider
import com.tommannson.familycooking.ui.screens.createreceipt.CreateReceiptViewModel
import com.tommannson.familycooking.ui.theme.FamilyCookingTheme
import com.tommannson.paymentorganizer.theme.CMTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var infrastructure: InfraProvider
    val resultFlow = MutableStateFlow("")

    val viewModel: CreateReceiptViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infrastructure.register(this)

        setContent {
            CMTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        val scope = rememberCoroutineScope()
                        val processedResult by resultFlow.collectAsState()
                        Text("Załaduj przepis")
                        Button(onClick = {
                            viewModel.performImageLoading()
                        }) {
                            Text(text = "Open Camera")
                        }
                        Spacer(modifier = Modifier.height(50.dp))
                        Text(text = "Rezultat to:")
                        Text(text = processedResult)

                    }

//                    SearchScreen()

                }
            }
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


    @Composable
    fun MainActivity(modifier: Modifier = Modifier) {
        Button(
            onClick = {
//            val scope = rememberCoroutineScope()
//            scope.launch(Dispatchers.IO) {
////                                val imageUri = Uri.fromFile(File("file:///android_asset/img.png"))
//                val imageUri = getUriFromAsset(this@MainActivity, "img_3.png")!!
//                val image = InputImage.fromFilePath(this@MainActivity, imageUri)
//
//                val result = recognizer.process(image)
//                    .addOnSuccessListener { visionText ->
//                        val blocks = visionText.textBlocks
//                        val text = visionText.text
//                        visionText.toString()
//                    }
//                    .addOnFailureListener { e ->
//                        // Task failed with an exception
//                        // ...
//                    }
//
//            }

            }) {
            Text(text = "sdfdsf")
        }
    }


    @Preview
    @Composable
    fun GreetingPreview() {
        FamilyCookingTheme {
            Greeting("Android")
        }
    }