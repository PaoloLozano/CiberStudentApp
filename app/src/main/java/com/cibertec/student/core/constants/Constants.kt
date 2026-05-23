package com.cibertec.student.core.constants

object Constants {
    // Firestore collections
    const val COLLECTION_USERS = "users"

    // Shared preferences
    const val PREFS_NAME = "ciber_student_prefs"
    const val KEY_USER_ID = "user_id"

    // Attendance threshold
    const val MIN_ATTENDANCE_PERCENTAGE = 70f
    const val WARNING_ATTENDANCE_PERCENTAGE = 80f

    // Task reminder offset
    const val REMINDER_OFFSET_HOURS = 24L

    // Note colors
    val NOTE_COLORS = listOf(
        "#FFFFFF", "#E3F2FD", "#E8F5E9", "#FFF8E1",
        "#FCE4EC", "#EDE7F6", "#E0F7FA", "#F3E5F5"
    )

    // Course colors
    val COURSE_COLORS = listOf(
        "#1565C0", "#6A1B9A", "#2E7D32", "#E65100",
        "#00695C", "#C62828", "#283593", "#AD1457"
    )

    // Careers
    val CIBERTEC_CAREERS = listOf(
        "Administración de Empresas",
        "Administración de Negocios Internacionales",
        "Contabilidad",
        "Marketing",
        "Computación e Informática",
        "Diseño Gráfico",
        "Ingeniería de Sistemas",
        "Ingeniería Civil",
        "Ingeniería Industrial",
        "Administración y Gestión Comercial"
    )
}
