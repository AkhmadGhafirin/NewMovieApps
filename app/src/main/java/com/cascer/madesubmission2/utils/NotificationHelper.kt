package com.cascer.madesubmission2.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.movie.MoviesItem
import com.cascer.madesubmission2.feature.detail.DetailActivity
import com.cascer.madesubmission2.feature.main.MainActivity

class NotificationHelper {

    fun createNotificationChannel(
        context: Context, importance: Int, showBadge: Boolean, name: String, description: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager?.createNotificationChannel(channel)
        }
    }

    fun showDailyNotification(context: Context?, title: String, message: String) {
        val notificationManagerCompat =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationID = DAILY_REMINDER_ALARM_ID

        val channelID = "${context.packageName}-${context.getString(R.string.app_name)}"
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        notificationManagerCompat.notify(notificationID, builder.build())
    }

    fun showReleaseNotification(
        context: Context?, title: String, message: String, data: MoviesItem
    ) {
        val notificationManagerCompat =
            context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val channelID = "${context.packageName}-${context.getString(R.string.app_name)}"
        val notificationID = RELEASED_TODAY_ALARM_ID

        val intent = Intent(context, DetailActivity::class.java)
        intent.putExtra("data", data)
        intent.putExtra("from", context.getString(R.string.movie_label))
        intent.putExtra("type", "basic")

        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationID,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val builder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_movie)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)

        notificationManagerCompat.notify(notificationID, builder.build())
    }
}