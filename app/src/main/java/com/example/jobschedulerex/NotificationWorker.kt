package com.example.jobschedulerex

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.support.v4.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

/**
 * This worker will be used to send a notification to the user.
 */
class NotificationWorker(private val context: Context, workerParameters: WorkerParameters) :
    Worker(context, workerParameters) {

    //Starting in Android 8.0 Oreo (API Level 26), all notifications must be associated with a notification channel
    //we create the channel here.
    init {
        val name = context.getString(R.string.notification_channel_name)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(MESSAGE_CHANNEL, name, NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    @SuppressLint("NewApi")
    override fun doWork(): Result {
        val data = inputData.getString(WORKER_RANDOM_TOAST)
        //Upon clicking the notification, we open up the browser.
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(URL)
        val browserPendingIntent = PendingIntent.getActivity(context, PENDING_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        //To create a new notification, we specify both a context and the channel that was previously created
        val builder = NotificationCompat.Builder(applicationContext, MESSAGE_CHANNEL)
        //A notification at a minimum needs a small icon, title, and descriptive text.
        builder.apply {
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentTitle("Random Int")
            setContentText("The random int generated is $data")
            setContentIntent(browserPendingIntent)
        }
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        //Each notification must have a unique ID as well
        notificationManager.notify(MESSAGE_ID, builder.build())
        return Result.success()
    }
}