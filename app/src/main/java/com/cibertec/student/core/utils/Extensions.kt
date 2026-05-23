package com.cibertec.student.core.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

fun View.show() { visibility = View.VISIBLE }
fun View.hide() { visibility = View.GONE }
fun View.invisible() { visibility = View.INVISIBLE }

fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.showSnackbar(message: String, isError: Boolean = false) {
    val snackbar = Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG)
    if (isError) {
        snackbar.setBackgroundTint(requireContext().getColor(com.cibertec.student.R.color.error))
    }
    snackbar.show()
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { it.replaceFirstChar { c -> c.uppercase() } }

fun Int.toDayName(): String {
    return when (this) {
        1 -> "Dom"
        2 -> "Lun"
        3 -> "Mar"
        4 -> "Mié"
        5 -> "Jue"
        6 -> "Vie"
        7 -> "Sáb"
        else -> ""
    }
}
