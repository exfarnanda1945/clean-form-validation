package com.exfarnanda1945.cleanformvalidation.presentation

sealed class RegistrationFormEvent {
    data class OnChangeEmail(val email: String) : RegistrationFormEvent()
    data class OnChangePassword(val password: String) : RegistrationFormEvent()
    data class OnChangePasswordRepeated(val passwordRepeated: String) : RegistrationFormEvent()
    data class OnChangeTerms(val acceptedTerms: Boolean) : RegistrationFormEvent()

    object OnSubmitForm:RegistrationFormEvent()

}
