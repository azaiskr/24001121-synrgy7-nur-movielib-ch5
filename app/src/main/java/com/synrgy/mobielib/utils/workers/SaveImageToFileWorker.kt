package com.synrgy.mobielib.utils.workers

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.synrgy.common.KEY_IMAGE_URI
import java.io.IOException


private const val TAG_SAVE_IMAGE_TO_FILE = "SaveImageToFileWorker"

class SaveImageToFileWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {

    override fun doWork(): Result {

        makeStatusNotification("Saving image", applicationContext)
        sleep()

        val resolver = applicationContext.contentResolver
        return try {
            val resourceUri = inputData.getString(KEY_IMAGE_URI)
                ?: throw IllegalArgumentException("Invalid input uri")
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(
                    resolver,
                    Uri.parse(resourceUri)
                )
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(
                    resolver,
                    Uri.parse(resourceUri)
                )
            }
            val imageUrl = saveImageToGallery(bitmap)
            if (imageUrl.isEmpty()) {
                makeStatusNotification("Failed to save image : Image Uri is empty", applicationContext)
                throw IOException("Writing to MediaStore failed")
            } else {
                val output = workDataOf(KEY_IMAGE_URI to imageUrl)
                makeStatusNotification("Image saved successfully", applicationContext)
                Result.success(output)
            }

        } catch (exception: Exception) {
            Log.e(TAG_SAVE_IMAGE_TO_FILE, exception.message ?: "")
            makeStatusNotification("Failed to save image : ${exception.message}", applicationContext)
            exception.printStackTrace()
            Result.failure()
        }
    }

    private fun saveImageToGallery(bitmap: Bitmap): String {
        val contentValues = ContentValues().apply {
            put(MediaStore.Images.Media.DISPLAY_NAME, "Blurred_Image_${System.currentTimeMillis()}.jpg")
            put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/Blurred Images")
        }

        val resolver = applicationContext.contentResolver
        val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
        if (uri != null) {
            resolver.openOutputStream(uri).use { outputStream ->
                if (outputStream != null) {
                    if (!bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)) {
                        throw IOException("Failed to save bitmap.")
                    }
                } else {
                    throw IOException("OutputStream is null.")
                }
            }
        } else {
            throw IOException("Failed to create new MediaStore record.")
        }
        return uri.toString()
    }

}