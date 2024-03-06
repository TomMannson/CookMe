package com.tommannson.familycooking.infrastructure.cameraPicker

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.core.net.toFile
import androidx.exifinterface.media.ExifInterface
import java.io.File

class ExifImageRotator {

    fun rotateFileIfNeeded(activity: Activity, originalLocation: Uri): Uri {
        val originalFile = originalLocation.toFile()
        val loadedFileMetadata = ExifInterface(originalFile);
        val orientation = loadedFileMetadata.getAttribute(ExifInterface.TAG_ORIENTATION) ?: ExifInterface.ORIENTATION_NORMAL.toString()

        val localFile = File(activity.filesDir, originalFile.name)

        val loadedBitmap = BitmapFactory.decodeFile(originalFile.absolutePath)
        val rotatedBitmap = rotateBitmap(loadedBitmap, selectCorrectRotation(orientation))
        localFile.outputStream().use {
            rotatedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
        }
        return Uri.fromFile(localFile)
    }

    private fun rotateBitmap(bitmapOrg: Bitmap, rotation: Float): Bitmap{
        val matrix = Matrix()
        matrix.postRotate(rotation)

        return Bitmap.createBitmap(
            bitmapOrg,
            0,
            0,
            bitmapOrg.width,
            bitmapOrg.height,
            matrix,
            true
        )
    }

    private fun selectCorrectRotation(orientation: String): Float {
        return when(orientation){
            ExifInterface.ORIENTATION_NORMAL.toString() -> 0f
            ExifInterface.ORIENTATION_ROTATE_90.toString() -> 90f
            ExifInterface.ORIENTATION_ROTATE_180.toString() -> 180f
            ExifInterface.ORIENTATION_ROTATE_270.toString() -> 270f
            else -> 0f
        }
    }
}