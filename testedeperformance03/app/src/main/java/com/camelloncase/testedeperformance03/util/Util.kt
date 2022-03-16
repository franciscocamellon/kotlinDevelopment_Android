package com.camelloncase.testedeperformance03.util

import android.content.Context
import android.util.Patterns
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

fun showMessageToUser(context: Context?, message: String) {

    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

}

fun formattedCurrentDate(pattern: String): String {

    val date = Calendar.getInstance().time
    val formatter = SimpleDateFormat(pattern, Locale.getDefault())

    return formatter.format(date)
}

fun checkPasswordPattern(password: String): Boolean {

    val regex = "^(?=.*[-@!#\$%^&+=]).{6,}\$"
    val passwordPattern = Pattern.compile(regex)

    return when (password.isEmpty()) {
        true -> false
        else -> passwordPattern.matcher(password).matches()
    }
}

fun checkEmailPattern(email: String): Boolean {

    return when (email.isEmpty()) {
        true -> false
        else -> Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}