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

    fun canScheduleAlarms(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            alarmManager.canScheduleExactAlarms()
        } else {
            true
        }
    }

    fun scheduleTaskReminder(task: Task) {
        if (task.dueDate <= System.currentTimeMillis()) return

        val now = System.currentTimeMillis()
        val timeUntilDue = task.dueDate - now
        val oneDay = 24 * 60 * 60 * 1000L

        val reminderTime: Long
        val message: String

        when {
            // Vence en menos de 2 horas → notificar en 15 segundos (TEST)
            timeUntilDue <= 2 * 60 * 60 * 1000L -> {
                reminderTime = now + 15_000L
                message = "\"${task.title}\" vence muy pronto"
            }
            // Vence hoy (menos de 24h) → notificar en 15 segundos (TEST)
            timeUntilDue <= oneDay -> {
                reminderTime = now + 15_000L
                message = "\"${task.title}\" vence hoy"
            }
            // Vence mañana → notificar en 15 segundos (TEST)
            timeUntilDue <= 2 * oneDay -> {
                reminderTime = now + 15_000L
                message = "\"${task.title}\" vence mañana"
            }
            // Vence en más de 2 días → notificar 1 día antes
            else -> {
                reminderTime = task.dueDate - oneDay
                message = "\"${task.title}\" vence mañana"
            }
        }

        val intent = Intent(context, ReminderReceiver::class.java).apply {
            putExtra(ReminderReceiver.EXTRA_TITLE, "⏰ Recordatorio de tarea")
            putExtra(ReminderReceiver.EXTRA_MESSAGE, message)
            putExtra(ReminderReceiver.EXTRA_NOTIFICATION_ID, task.id.toInt())
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        scheduleExactAlarm(pendingIntent, reminderTime)
    }

    private fun scheduleExactAlarm(pendingIntent: PendingIntent, triggerAtMillis: Long) {
        try {
            when {
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                    if (alarmManager.canScheduleExactAlarms()) {
                        alarmManager.setExactAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent
                        )
                    } else {
                        // Fallback: alarma inexacta (funciona sin permiso especial)
                        alarmManager.setAndAllowWhileIdle(
                            AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent
                        )
                    }
                }
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> {
                    alarmManager.setExactAndAllowWhileIdle(
                        AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent
                    )
                }
                else -> {
                    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
                }
            }
        } catch (e: SecurityException) {
            // Último fallback si aún así falla
            alarmManager.set(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
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
