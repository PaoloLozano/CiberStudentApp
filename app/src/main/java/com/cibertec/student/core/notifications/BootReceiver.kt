package com.cibertec.student.core.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * Receiver to re-schedule alarms after device reboot.
 * In a real app, you'd re-fetch pending tasks and re-schedule their reminders.
 */
class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            // Re-schedule task reminders after reboot
            // This would be implemented via WorkManager in production
        }
    }
}
