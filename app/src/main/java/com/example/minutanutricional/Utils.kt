package com.example.minutanutricional

import android.util.Patterns

object Utils {
    fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}