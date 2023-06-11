package com.exfarnanda1945.cleanformvalidation.domain.use_case

class ValidatePassword {
    fun execute(password: String): ValidationResult {

        // If Password is blank
        if (password.isBlank()) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This password can't blank"
            )
        }

        // If Password length less than 6
        if (password.length < 6) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This password needs to consists of at least 6 character"
            )
        }

        // If password doesn't contain at least 1 character and 1 number
        val containsLetterAndDigit = password.any{ it.isDigit() } && password.any { it.isLetter() }
        if(!containsLetterAndDigit){
            return ValidationResult(
                isSuccess = false,
                errorMessage = "This password needs to contain at least one letter and digit"
            )
        }

        return ValidationResult(true)
    }
}