package com.tommannson.familycooking.infrastructure

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import com.tommannson.familycooking.infrastructure.cameraPicker.CameraPicker
import com.tommannson.familycooking.infrastructure.textRecognition.TextRecognizer

@Composable
fun InfrastructureLocalProvider(
    cameraPicker: CameraPicker,
    textRecognizer: TextRecognizer,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        LocalInfrastructureProvider provides InfrastructureProvider(
            cameraPicker = cameraPicker,
            textRecognizer = textRecognizer
        )
    ) {
        content()
    }
}


data class InfrastructureProvider(
    private val cameraPicker: CameraPicker,
    private val textRecognizer: TextRecognizer
) {
    suspend fun launchCamera() = cameraPicker.launchCamera()
    suspend fun processRecipe(file: Uri) = textRecognizer.processRecipe(file)
}

val LocalInfrastructureProvider =
    staticCompositionLocalOf<InfrastructureProvider> { error("No provider found") }