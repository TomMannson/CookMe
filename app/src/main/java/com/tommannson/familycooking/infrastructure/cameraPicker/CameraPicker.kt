package com.tommannson.familycooking.infrastructure.cameraPicker

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.github.dhaval2404.imagepicker.ImagePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import java.lang.ref.WeakReference
import javax.inject.Inject
import javax.inject.Singleton


interface CameraPicker {

    fun ComponentActivity.registerCameraPicker()

    suspend fun launchCamera(): Uri?
}

@Singleton
internal class CameraPickerImpl @Inject constructor() : CameraPicker {

    private val cameraPickerResult = MutableSharedFlow<Uri?>(replay = 1)
    private val imageRotator = ExifImageRotator()
    private lateinit var cameraPickerLauncher: ActivityResultLauncher<Intent?>
    private lateinit var activity: WeakReference<ComponentActivity>
    override fun ComponentActivity.registerCameraPicker() {
        activity = WeakReference(this)
        cameraPickerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
                val resultCode = result.resultCode
                val data = result.data

                if (resultCode == Activity.RESULT_OK) {
                    cameraPickerResult.tryEmit(data?.data)
                } else {
                    cameraPickerResult.tryEmit(null)
                }
            }
    }

    override suspend fun launchCamera(): Uri? {
        return activity.get()?.run {
            createAndStartIntent()
            withContext(Dispatchers.Default) {
                val originalLocation = cameraPickerResult.first()
                    ?.also { cameraPickerResult.resetReplayCache() }
                    ?: return@withContext null

                imageRotator.rotateFileIfNeeded(this@run, originalLocation)
            }
        }
    }

    private fun ComponentActivity.createAndStartIntent() {
        ImagePicker.with(this)
            .compress(1024 * 4)
            .maxResultSize(
                1920,
                1920
            )    //Final image resolution will be less than 1080 x 1080(Optional)
            .createIntent { intent ->
                cameraPickerLauncher.launch(intent)
            }
    }
}