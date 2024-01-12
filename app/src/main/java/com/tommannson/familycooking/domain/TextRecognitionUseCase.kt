package com.tommannson.familycooking.domain

import android.net.Uri
import com.tommannson.familycooking.infrastructure.textRecognition.TextRecognizer
import javax.inject.Inject

typealias TextRecognitionResult = String

fun interface TextRecognitionUseCase : suspend (Uri) -> Result<TextRecognitionResult>


internal class DefaultTextRecognitionUseCase @Inject constructor(
    private val textRecognition: TextRecognizer,
) : TextRecognitionUseCase {
    override suspend fun invoke(imagePath: Uri): Result<TextRecognitionResult> {
        return try {
            textRecognition.processRecipe(imagePath)
                ?.let { Result.success(it) }
                ?: throw IllegalStateException("Recognition failed")
        } catch (ex: IllegalStateException) {
            Result.failure(ex)
        }
    }
}