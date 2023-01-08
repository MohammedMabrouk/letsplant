package com.mohamedmabrouk.letsplant.messagingService

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mohamedmabrouk.letsplant.firebase.FirebaseManager
import dagger.android.AndroidInjection
import javax.inject.Inject

class MessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var fireBaseManager: FirebaseManager

    override fun onCreate() {
        AndroidInjection.inject(this)
        super.onCreate()
    }

    /**
     * Called if the FCM registration token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the
     * FCM registration token is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
//        sendRegistrationToServer(token)
        //todo: check when uninstall and reinstall
    }

    override fun onMessageReceived(remotemsg: RemoteMessage) {
//        try {
//            val bundle = Bundle()
//            Log.d(TAG, "From -> " + remotemsg.from)
//            Log.d(TAG, "Notification Body -> " + remotemsg.notification?.body)
//            sendNotification(
//                remotemsg.notification?.title,
//                remotemsg.notification?.body,
//                remotemsg.data
//            )
//            bundle.putString("from", remotemsg.from)
//            bundle.putString("notification_title", remotemsg.notification?.title)
//            bundle.putString("notification_body", remotemsg.notification?.body)
//            bundle.putString("notification_data", remotemsg.data.toString())
//            fireBaseManager.logEvent(EventKeys.NOTIFICATION_RECEIVED, bundle)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            FirebaseCrashlytics.getInstance().log(e.localizedMessage ?: e.toString())
//        }
    }

    private fun broadcastNotification() {
//        try {
//            val intentResponse = Intent()
//            intentResponse.action = Constants.ACTION_NEW_NOTIFICATION
//            LocalBroadcastManager.getInstance(applicationContext).sendBroadcast(intentResponse)
//        } catch (e: Exception) {
//            e.printStackTrace()
//            FirebaseCrashlytics.getInstance().log(e.localizedMessage ?: e.toString())
//        }
    }

    private fun sendNotification(title: String?, messageBody: String?, data: Map<String, String>?) {
//        try {
//            broadcastNotification()
//            val NOTIFICATION_ID = 12345
//            val targetIntent = Intent(this, SplashActivity::class.java).apply {
//                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            }
//
//            val contentIntent =
//                PendingIntent.getActivity(this, 0, targetIntent, PendingIntent.FLAG_UPDATE_CURRENT)
//            val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//            val notificationBuilder = NotificationCompat.Builder(this, CHANNEL_ID)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(title)
//                .setContentText(messageBody)
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
//                .setAutoCancel(true)
//                .setSound(soundUri)
//                .setContentIntent(contentIntent)
//            val notificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
//        } catch (e: Exception) {
//            e.printStackTrace()
//            FirebaseCrashlytics.getInstance().log(e.localizedMessage ?: e.toString())
//        }
    }

    companion object {
        private const val TAG = "MessagingService"
        const val CHANNEL_ID = "CHANNEL_ID"
    }
}