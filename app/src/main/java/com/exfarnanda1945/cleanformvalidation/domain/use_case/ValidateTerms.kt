package com.exfarnanda1945.cleanformvalidation.domain.use_case

class ValidateTerms {
    fun execute(acceptedTerms:Boolean): ValidationResult {
        if (!acceptedTerms) {
            return ValidationResult(
                isSuccess = false,
                errorMessage = "Please accepted the terms"
            )
        }

        return ValidationResult(true)
    }
}