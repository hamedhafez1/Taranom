package ir.roela.taranom.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ir.roela.taranom.R
import ir.roela.taranom.activity.MainActivity

object NotificationUtils {

    private const val CHANNEL_ID = "TARANOM_CHANNEL_ID"
    private const val CHANNEL_NAME = "TARANOM_CHANNEL_NAME"
    private const val CHANNEL_DESC = "TARANOM_CHANNEL_DESC"

    fun createNotification(context: Context, content: String?, id: Int) {
        val intent = Intent(context, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        createNotificationChannel(context)
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
            .setContentTitle(context.getString(R.string.app_name_fa))
            .setContentText(content)
            .setCustomContentView(getRemoteViews(context, content))
            .setCustomBigContentView(getRemoteViewsExpanded(context, content))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setSilent(true)
            .setAutoCancel(true)
        NotificationManagerCompat.from(context).notify(id, builder.build())
    }

    private fun getRemoteViews(context: Context, content: String?): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.notification_layout)
        remoteViews.setTextViewText(R.id.txt_notification_content, content)
        return remoteViews
    }

    private fun getRemoteViewsExpanded(context: Context, content: String?): RemoteViews {
        val remoteViews = RemoteViews(context.packageName, R.layout.notification_layout_expanded)
        remoteViews.setTextViewText(R.id.txt_notification_content_exp, content)
        return remoteViews
    }

    private fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationChannel.description = CHANNEL_DESC
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}