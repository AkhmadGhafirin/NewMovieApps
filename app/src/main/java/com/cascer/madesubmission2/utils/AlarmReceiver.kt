package com.cascer.madesubmission2.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.cascer.madesubmission2.R
import com.cascer.madesubmission2.data.response.MyTime
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val type = intent?.getStringExtra(KEY_ALARM_TYPE)
        val message = intent?.getStringExtra(KEY_ALARM_MESSAGE) ?: ""

        val title = context?.getString(R.string.app_name) ?: ""

        if (type == TYPE_RELEASED_TODAY) {
            context?.let { AlarmService().startJob(it) }
        } else {
            NotificationHelper().showDailyNotification(context, title, message)
        }
    }

    fun setPeriodicAlarm(context: Context?, type: String, time: MyTime, message: String) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(KEY_ALARM_MESSAGE, message)
        intent.putExtra(KEY_ALARM_TYPE, type)

        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, time.hour)
        calendar.set(Calendar.MINUTE, time.minute)
        calendar.set(Calendar.SECOND, time.second)

        val requestCode =
            if (type.equals(TYPE_RELEASED_TODAY, ignoreCase = true)) RELEASED_TODAY_ALARM_ID
            else DAILY_REMINDER_ALARM_ID

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun cancelAlarm(context: Context, type: String) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)

        val requestCode =
            if (type.equals(TYPE_RELEASED_TODAY, ignoreCase = true)) RELEASED_TODAY_ALARM_ID
            else DAILY_REMINDER_ALARM_ID
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        alarmManager.cancel(pendingIntent)
    }
}