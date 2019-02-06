package com.example.jobschedulerex

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.support.v4.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * This service is used to receive firebase notifications while the app is in the foreground. It is also
 * used to access the registration token.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {

    //This is where firebase notifications are received
    @SuppressLint("NewApi")
    override fun onMessageReceived(message: RemoteMessage?) {
        val remoteMessage = message?.notification

        //Once we receive a message, we create our own notification here
        remoteMessage?.let {
            val title = it.title
            val body = it.body
            val notificationManager = applicationContext.getSystemService(NotificationManager::class.java)
            val builder = NotificationCompat.Builder(applicationContext, MESSAGE_CHANNEL)
            builder.apply {
                setSmallIcon(R.drawable.ic_launcher_foreground)
                setContentTitle(title)
                setContentText(body)
            }
            notificationManager.notify(FIREBASE_MESSAGE_ID, builder.build())
        }
    }

    //This is where the InstanceID token is received
    override fun onNewToken(s: String?) {
        super.onNewToken(s)
    }
}