package com.cibertec.student

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CiberStudentApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            val reminderChannel = NotificationChannel(
                CHANNEL_REMINDERS,
                getString(R.string.notif_channel_reminders),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Recordatorios de clases y eventos"
                enableLights(true)
                enableVibration(true)
            }

            val tasksChannel = NotificationChannel(
                CHANNEL_TASKS,
                getString(R.string.notif_channel_tasks),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notificaciones de tareas próximas a vencer"
                enableLights(true)
                enableVibration(true)
            }

            notificationManager.createNotificationChannels(listOf(reminderChannel, tasksChannel))
        }
    }

    companion object {
        const val CHANNEL_REMINDERS = "channel_reminders"
        const val CHANNEL_TASKS = "channel_tasks"
    }
}
