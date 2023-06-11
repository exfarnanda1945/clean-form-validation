package com.exfarnanda1945.cleanformvalidation.domain.use_case

class ValidateRepeatedPassword {
    fun execute(password: String,repeatedPassword: String): ValidationResult {

        // If Password != Repeated Password
        if (password != repeatedPassword) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "The password don't match"
            )
        }

        return ValidationResult(true)
    }
}