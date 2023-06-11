package com.exfarnanda1945.cleanformvalidation.presentation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.exfarnanda1945.cleanformvalidation.domain.use_case.ValidateEmail
import com.exfarnanda1945.cleanformvalidation.domain.use_case.ValidatePassword
import com.exfarnanda1945.cleanformvalidation.domain.use_case.ValidateRepeatedPassword
import com.exfarnanda1945.cleanformvalidation.domain.use_case.ValidateTerms
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val validateEmail: ValidateEmail = ValidateEmail(),
    private val validatePassword: ValidatePassword = ValidatePassword(),
    private val validateRepeatedPassword: ValidateRepeatedPassword = ValidateRepeatedPassword(),
    private val validateTerms: ValidateTerms = ValidateTerms()
) : ViewModel() {
    var state by mutableStateOf(RegistrationFormState())

    private val registrationValidationChannel = Channel<ValidationEvent>()
    val registrationValidation = registrationValidationChannel.receiveAsFlow()

    fun onEvent(event: RegistrationFormEvent) {
        when (event) {
            is RegistrationFormEvent.OnChangeEmail -> {
                state = state.copy(email = event.email)
            }

            is RegistrationFormEvent.OnChangePassword -> {
                state = state.copy(password = event.password)
            }

            is RegistrationFormEvent.OnChangePasswordRepeated -> {
                state = state.copy(repeatedPassword = event.passwordRepeated)
            }

            is RegistrationFormEvent.OnChangeTerms -> {
                state = state.copy(acceptedTerms = event.acceptedTerms)
            }

            RegistrationFormEvent.OnSubmitForm -> submitData()
        }
    }

    private fun submitData() {
        val emailResult = validateEmail.execute(state.email)
        val passwordResult = validatePassword.execute(state.password)
        val repeatedPasswordResult =
            validateRepeatedPassword.execute(state.password, state.repeatedPassword)
        val termsResult = validateTerms.execute(state.acceptedTerms)

        val hasError = listOf(
            emailResult,
            passwordResult,
            repeatedPasswordResult,
            termsResult
        ).any { !it.isSuccess }

        Log.d("Error", "submitData: $hasError")

        if (hasError) {
            state = state.copy(
                emailError = emailResult.errorMessage,
                passwordError = passwordResult.errorMessage,
                repeatedPasswordError = repeatedPasswordResult.errorMessage,
                acceptedTermsError = termsResult.errorMessage,
            )
            // Stop submit data execution
            return
        } else {
            resetErrorState()
        }



        viewModelScope.launch {
            registrationValidationChannel.send(ValidationEvent.Success)
        }
    }

    private fun resetErrorState() {
        state = state.copy(
            emailError = null,
            passwordError = null,
            repeatedPasswordError = null,
            acceptedTermsError = null,
        )
    }

    sealed class ValidationEvent {
        object Success : ValidationEvent()
    }

}