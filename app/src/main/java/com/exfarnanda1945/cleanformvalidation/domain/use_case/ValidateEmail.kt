package com.exfarnanda1945.cleanformvalidation.domain.use_case

import android.util.Patterns

class ValidateEmail {
    fun execute(email: String): ValidationResult {
        // If Email is blank
        if (email.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This email can't blank"
            )
        }

        // If Email is invalid
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Invalid email"
            )
        }

        return ValidationResult(true)
    }
}