package com.cibertec.student.core.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import com.cibertec.student.domain.model.Task
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationScheduler @Inject constructor(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun scheduleTaskReminder(task: Task) {
        if (task.dueDate <= System.currentTimeMillis()) return

        // Remind 1 day before due date
        val reminderTime = task.dueDate - (24 * 60 * 60 * 1000L)
        if (reminderTime <= System.currentTimeMillis()) return

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(ReminderReceiver.EXTRA_TITLE, "Tarea próxima a vencer")
            putExtra(ReminderReceiver.EXTRA_MESSAGE, "${task.title} vence mañana")
            putExtra(ReminderReceiver.EXTRA_NOTIFICATION_ID, task.id.toInt())
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                alarmManager.setExactAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    reminderTime,
                    pendingIntent
                )
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, reminderTime, pendingIntent)
            }
        } catch (e: SecurityException) {
            // Permission not granted, skip scheduling
        }
    }

    fun cancelTaskReminder(taskId: Long) {
        val intent = Intent(context, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            taskId.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
    }

    fun scheduleClassReminder(courseId: Long, courseName: String, triggerAtMillis: Long) {
        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(ReminderReceiver.EXTRA_TITLE, "Clase en 15 minutos")
            putExtra(ReminderReceiver.EXTRA_MESSAGE, "Tienes $courseName próximamente")
            putExtra(ReminderReceiver.EXTRA_NOTIFICATION_ID, courseId.toInt() + 10000)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            courseId.toInt() + 10000,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        try {
            alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                triggerAtMillis,
                AlarmManager.INTERVAL_DAY * 7,
                pendingIntent
            )
        } catch (e: SecurityException) {
            // Permission not granted
        }
    }
}
