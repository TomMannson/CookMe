package com.tommannson.familycooking.infrastructure.textRecognition

import android.net.Uri
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Log
import androidx.activity.ComponentActivity
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import com.tommannson.remote.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber
import java.lang.ref.WeakReference
import java.security.KeyPairGenerator
import java.security.KeyStore
import javax.inject.Inject
import javax.inject.Singleton
import com.google.mlkit.vision.text.TextRecognition as PlayStoreTextRecognizer


interface TextRecognizer {

    fun ComponentActivity.registerTextRecognition()

    suspend fun processRecipe(file: Uri): String?
}

@Singleton
internal class ImageToTextDecoderImpl @Inject constructor() : TextRecognizer {

    private val recognizer =
        PlayStoreTextRecognizer.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)
    private val recipeFixer = RetrofitFactory.providerService()
    private lateinit var activity: WeakReference<ComponentActivity>

    override fun ComponentActivity.registerTextRecognition() {
        this@ImageToTextDecoderImpl.activity = WeakReference(this)
    }

    override suspend fun processRecipe(file: Uri): String? {
        return withContext(Dispatchers.Default) {
            val image = InputImage.fromFilePath(activity.get()!!, file);
            Timber.d( "IMAGE processing")
            try {
                val extractedText = recognizer.process(image).await()
                Timber.d(  "text extracted")
                val request =
                    recipeFixer.recognizeRecipe(mutableMapOf("query" to extractedText.text))
                request.execute()
                    .also {
                        Timber.d(  "recipe fixed")
                        logIfError(it)
                    }
                    .body()
            } catch (ex: Exception) {
                Timber.w(  "recipe fixed")
                null
            }
        }
    }

    private fun logIfError(it: Response<String>) {
        if (!it.isSuccessful) {
            Log.e("REQ_CODE", "${it.code()}")
            Log.e("REQ_HEADER", "${it.headers()}")
            Log.e("REQ_BODY", "${it.body()}")
        }
    }
}


