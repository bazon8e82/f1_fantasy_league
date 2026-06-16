package com.example.f1fantasyleague.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.f1fantasyleague.data.models.Race
import com.example.f1fantasyleague.R

object NotificationScheduler {

    fun scheduleRaceNotifications(context: Context, race: Race) {
        val raceTime = race.raceDate?.toDate()?.time ?: return

        scheduleReminder(
            context = context,
            requestCode = race.raceId * 10 + 1,
            triggerTime = raceTime - 24 * 60 * 60 * 1000,
            title = context.getString(R.string.notification_title_24h),
            message = context.getString(R.string.notification_message_24h)
        )

        scheduleReminder(
            context = context,
            requestCode = race.raceId * 10 + 2,
            triggerTime = raceTime - 60 * 60 * 1000,
            title = context.getString(R.string.notification_title_1h),
            message = context.getString(R.string.notification_message_1h)
        )
    }

    private fun scheduleReminder(
        context: Context,
        requestCode: Int,
        triggerTime: Long,
        title: String,
        message: String
    ) {
        if (triggerTime <= System.currentTimeMillis()) return

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(EXTRA_NOTIFICATION_TITLE, title)
            putExtra(EXTRA_NOTIFICATION_MESSAGE, message)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }
}