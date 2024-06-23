package com.synrgy.mobielib.utils.workers

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.synrgy.common.CHANNEL_ID
import com.synrgy.common.DELAY_TIME_MILLIS
import com.synrgy.common.NOTIFICATION_ID
import com.synrgy.mobielib.R

fun makeStatusNotification(message: String, ctx: Context){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val name = ctx.getString(R.string.app_name)
        val description = "Profile image is being uploaded"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance)
        channel.description = description
        val notificationManager = ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(ctx, CHANNEL_ID)
        .setSmallIcon(R.drawable.round_local_movies_24)
        .setContentTitle(ctx.getString(R.string.app_name))
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setVibrate(LongArray(0))


    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
        ActivityCompat.checkSelfPermission(ctx, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
        NotificationManagerCompat.from(ctx).notify(NOTIFICATION_ID, builder.build())
    }
}

fun sleep(){
    try{
        Thread.sleep(DELAY_TIME_MILLIS,0)
    } catch(e:InterruptedException){
        Log.e("SLEEP", e.message.toString())
    }
}