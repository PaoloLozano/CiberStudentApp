package com.cibertec.student.core.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {

    private val locale = Locale("es", "PE")

    fun formatTodayFull(): String {
        val sdf = SimpleDateFormat("EEEE, dd 'de' MMMM", locale)
        return sdf.format(Date()).replaceFirstChar { it.uppercase() }
    }

    fun formatDate(millis: Long): String {
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy", locale)
        return sdf.format(Date(millis))
    }

    fun formatShortDate(millis: Long): String {
        val sdf = SimpleDateFormat("dd MMM", locale)
        return sdf.format(Date(millis))
    }

    fun formatTime(millis: Long): String {
        val sdf = SimpleDateFormat("HH:mm", locale)
        return sdf.format(Date(millis))
    }

    fun isOverdue(millis: Long): Boolean = millis < System.currentTimeMillis()

    fun getDaysUntilDue(millis: Long): Int {
        val diff = millis - System.currentTimeMillis()
        return (diff / (1000 * 60 * 60 * 24)).toInt()
    }

    fun getDueDateLabel(millis: Long): String {
        if (millis <= 0) return "Sin fecha"
        val days = getDaysUntilDue(millis)
        return when {
            days < 0 -> "Vencida"
            days == 0 -> "Hoy"
            days == 1 -> "Mañana"
            days <= 7 -> "En $days días"
            else -> formatShortDate(millis)
        }
    }
}
