package com.exfarnanda1945.cleanformvalidation.domain.use_case

data class ValidationResult(
    val isSuccess:Boolean,
    val errorMessage:String? = null
)