package com.exfarnanda1945.cleanformvalidation.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.exfarnanda1945.cleanformvalidation.ui.theme.CleanFormValidationTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CleanFormValidationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val viewModel = viewModel<MainViewModel>()
                    val state = viewModel.state
                    val context = LocalContext.current

                    LaunchedEffect(key1 = context) {
                        viewModel.registrationValidation.collect { event ->
                            when (event) {
                                MainViewModel.ValidationEvent.Success -> Toast.makeText(
                                    context,
                                    "Registration Successful",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        TextField(
                            value = state.email,
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.OnChangeEmail(it))
                            },
                            isError = state.emailError != null,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Email")

                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email
                            ),
                            supportingText = {
                                Text(
                                    text = state.emailError ?: "",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = state.password,
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.OnChangePassword(it))
                            },
                            isError = state.passwordError != null,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Password")

                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            supportingText = {
                                Text(
                                    text = state.passwordError ?: "",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        TextField(
                            value = state.repeatedPassword,
                            onValueChange = {
                                viewModel.onEvent(RegistrationFormEvent.OnChangePasswordRepeated(it))
                            },
                            isError = state.repeatedPasswordError != null,
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = {
                                Text(text = "Repeat Password")

                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Password
                            ),
                            visualTransformation = PasswordVisualTransformation(),
                            supportingText = {
                                Text(
                                    text = state.repeatedPasswordError ?: "",
                                    color = MaterialTheme.colorScheme.error
                                )
                            }
                        )
                        Spacer(modifier = Modifier.height(16.dp))

                        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = state.acceptedTerms,
                                onCheckedChange = {
                                    viewModel.onEvent(
                                        RegistrationFormEvent.OnChangeTerms(it)
                                    )
                                })
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = "Accepted Terms")
                        }

                        if (state.acceptedTermsError != null) {
                            Text(
                                text = state.acceptedTermsError,
                                color = MaterialTheme.colorScheme.error
                            )
                        }

                        Button(onClick = {
                            viewModel.onEvent(RegistrationFormEvent.OnSubmitForm)
                        }, modifier = Modifier.align(Alignment.End)) {
                            Text(text = "Submit")
                        }
                    }
                }
            }
        }
    }
}
