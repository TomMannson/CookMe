package com.tommannson.familycooking.domain

import android.net.Uri
import com.tommannson.familycooking.infrastructure.cameraPicker.CameraPicker
import javax.inject.Inject

fun interface LoadImageUseCase : suspend () -> Result<Uri>

internal class DefaultLoadImageUseCase @Inject constructor(
    private val cameraPicker: CameraPicker,
) : LoadImageUseCase {
    override suspend fun invoke(): Result<Uri> {
        return try {
            cameraPicker.launchCamera()
                ?.let { Result.success(it) }
                ?: throw IllegalStateException("Camera picking failed")
        } catch (ex: IllegalStateException) {
            Result.failure(ex)
        }
    }
}


