package com.synrgy.mobielib.utils.workers

import android.content.Context
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.renderscript.Allocation
import android.renderscript.Element
import android.renderscript.RenderScript
import android.renderscript.ScriptIntrinsicBlur
import android.text.TextUtils
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.google.firebase.perf.FirebasePerformance
import com.google.firebase.perf.metrics.Trace
import com.synrgy.common.KEY_IMAGE_URI
import com.synrgy.common.OUTPUT_PATH
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.UUID


const val TAG = "BLUR_WORKER"
@Suppress("DEPRECATION")
class BlurWorker(ctx: Context, params: WorkerParameters) : Worker(ctx, params) {
    private lateinit var trace: Trace
    override fun doWork(): Result {
        trace = FirebasePerformance.getInstance().newTrace("blur_worker_trace")
        trace.start()

        val imageUri = inputData.getString(KEY_IMAGE_URI) ?: return Result.failure()
        makeStatusNotification("Blurring image", applicationContext)
        sleep()

        return try {
            if (TextUtils.isEmpty(imageUri)) {
                Log.e(TAG, "Invalid input uri")
                throw IllegalArgumentException("Invalid input uri")
            }
            val resolver = applicationContext.contentResolver
            val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val source = ImageDecoder.createSource(resolver, Uri.parse(imageUri))
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(resolver, Uri.parse(imageUri))
            }
            val output = blurBitmap(bitmap, applicationContext)
            val outputUri = writeBitmapToFile(applicationContext, output)
            val outputData = workDataOf(KEY_IMAGE_URI to outputUri.toString())
            trace.stop()
            Result.success(outputData)

        } catch (throwable: Throwable) {
            Log.e(TAG, "Error applying blur")
            throwable.printStackTrace()
            trace.stop()
            Result.failure()
        }
    }

    private fun blurBitmap(image: Bitmap, context: Context): Bitmap {
        val rsContext = RenderScript.create(context)
        try {
            val convertedBitmap = image.copy(Bitmap.Config.ARGB_8888, true)
            val output = Bitmap.createBitmap(
                convertedBitmap.width, convertedBitmap.height, convertedBitmap.config
            )
            val inAlloc = Allocation.createFromBitmap(rsContext, convertedBitmap)
            val outAlloc = Allocation.createTyped(rsContext, inAlloc.type)
            val theIntrinsic = ScriptIntrinsicBlur.create(rsContext, Element.U8_4(rsContext))
            theIntrinsic.apply {
                setRadius(20f)
                theIntrinsic.setInput(inAlloc)
                theIntrinsic.forEach(outAlloc)
            }
            outAlloc.copyTo(output)
            return output
        } finally {
            rsContext.finish()
        }
    }

    private fun writeBitmapToFile(applicationContext: Context, bitmap: Bitmap): Uri {
        val name = String.format("profile-blurred-%s.png", UUID.randomUUID().toString())
        val outputDir = File(applicationContext.filesDir, OUTPUT_PATH)
        if (!outputDir.exists()) {
            outputDir.mkdirs()
        }
        val outputFile = File(outputDir, name)
        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(outputFile)
            bitmap.compress(Bitmap.CompressFormat.PNG, 0, out)
        } finally {
            out?.let {
                {
                    try {
                        it.close()
                    } catch (ignore: IOException) {
                    }
                }
            }
        }
        return Uri.fromFile(outputFile)
    }
}
