package com.tommannson.familycooking.api

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tommannson.remote.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

object RecipePreprocesor {

    val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    fun processRecipe(context: Context, file: Uri, onResult: (String) -> Unit) {
        val image = InputImage.fromFilePath(context, file)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val blocks = visionText.textBlocks
                val text = visionText.text
                visionText.toString()

                GlobalScope.launch(Dispatchers.IO) {
                    val service = RetrofitFactory.providerService()
                    val wsResult =
                        service.recognizeRecipe(mutableMapOf("query" to text)).execute()
                            .also {
                                if (!it.isSuccessful) {
                                    Log.e("REQ_CODE", "${it.code()}")
                                    Log.e("REQ_HEADER", "${it.headers()}")
                                    Log.e("REQ_BODY", "${it.body()}")
                                }
                            }
                            .body()

                    onResult(wsResult.toString())
                }
            }
            .addOnFailureListener { e ->
                e.toString()
                // Task failed with an exception
                // ...
            }
    }

}