/*
 * Copyright (c) 2024 Codepunk, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.codepunk.tipnerd.ui.compose.screen.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.tipnerd.di.qualifier.IoDispatcher
import com.codepunk.tipnerd.domain.repository.TipnerdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val repository: TipnerdRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

// region Variables

    // We use StateFlow here instead of State/mutableStateOf to keep
    // Compose-related constructs out of ViewModel
    private val _stateFlow: MutableStateFlow<AuthState> = MutableStateFlow(AuthState())
    val stateFlow = _stateFlow.asStateFlow()

    private var state: AuthState
        get() = _stateFlow.value
        set(value) { _stateFlow.value = value }

    // endregion Variables

    // region Methods

    // Data changes

    private fun updateEmail(email: String) {
        state = state.copy(
            email = email
        )
    }

    private fun updateName(name: String) {
        state = state.copy(
            name = name
        )
    }

    private fun updatePassword(password: String) {
        state = state.copy(
            password = password
        )
    }

    private fun updateUsername(username: String) {
        state = state.copy(
            username = username
        )
    }

    private fun updateVerifyPassword(verifyPassword: String) {
        state = state.copy(
            verifyPassword = verifyPassword
        )
    }

    // User actions

    private fun register(
        name: String,
        username: String,
        email: String,
        password: String,
        verifyPassword: String
    ) {
        state = state.copy(isLoading = true)
        viewModelScope.launch(ioDispatcher) {
            repository.register(
                username = username,
                name = name,
                email = email,
                password = password,
                verifyPassword = verifyPassword
            ).collect { result ->
                state = state.copy(
                    isLoading = false,
                    isRegisterResultFresh = true,
                    registerResult = result
                )
            }
        }
    }

    private fun login(username: String, password: String) {
        state = state.copy(isLoading = true)
        viewModelScope.launch(ioDispatcher) {
            repository.login(
                username = username,
                password = password
            ).collect { result ->
                state = state.copy(
                    isLoading = false,
                    isLoginResultFresh = true,
                    loginResult = result
                )
            }
        }
    }

    private fun resendVerificationEmail() {
        state = state.copy(isLoading = true)
        viewModelScope.launch(ioDispatcher) {
            repository.resendVerificationEmail().collect { result ->
                state = state.copy(
                    isLoading = false,
                    isResendResultFresh = true,
                    resendResult = result
                )
            }
        }
    }

    // Events/results

    private fun consumeLoginResult() {
        state = state.copy(
            isLoginResultFresh = false
        )
    }

    private fun clearLoginResult() {
        state = state.copy(
            isLoginResultFresh = true,
            loginResult = null
        )
    }

    private fun consumeRegisterResult() {
        state = state.copy(
            isRegisterResultFresh = false
        )
    }

    private fun clearRegisterResult() {
        state = state.copy(
            isRegisterResultFresh = true,
            registerResult = null
        )
    }

    private fun consumeResendResult() {
        state = state.copy(
            isResendResultFresh = false
        )
    }

    private fun clearResendResult() {
        state = state.copy(
            isResendResultFresh = true,
            resendResult = null
        )
    }

    // Event delegate

    fun onEvent(event: AuthEvent) {
        when (event) {
            // Update data
            is AuthEvent.UpdateEmail -> updateEmail(event.value)
            is AuthEvent.UpdateName -> updateName(event.value)
            is AuthEvent.UpdatePassword -> updatePassword(event.value)
            is AuthEvent.UpdateUsername -> updateUsername(event.value)
            is AuthEvent.UpdateVerifyPassword -> updateVerifyPassword(event.value)

            // Navigation
            // AuthNavigationEvents are propagated up to AuthNavigation
            // rather than being handled here
            AuthEvent.NavigateToMain -> { /* No op */ }
            AuthEvent.NavigateToLogin -> { /* No op */ }
            AuthEvent.NavigateToRegister -> { /* No op */ }
            AuthEvent.NavigateToEmailVerification -> { /* No op */ }
            AuthEvent.NavigateUp -> { /* No op */ }

            // Events/results
            AuthEvent.ConsumeLoginResult -> consumeLoginResult()
            AuthEvent.ClearLoginResult -> clearLoginResult()
            AuthEvent.ConsumeRegisterResult -> consumeRegisterResult()
            AuthEvent.ClearRegisterResult -> clearRegisterResult()
            AuthEvent.ConsumeResendResult -> consumeResendResult()
            AuthEvent.ClearResendResult -> clearResendResult()

            // User actions
            AuthEvent.EditAvatar -> TODO("Not yet implemented")
            is AuthEvent.Login -> login(
                username = event.username,
                password = event.password
            )
            AuthEvent.Logout -> { /* TODO */ }
            is AuthEvent.Register -> register(
                name = event.name,
                username = event.username,
                email = event.email,
                password = event.password,
                verifyPassword = event.verifyPassword
            )
            AuthEvent.ResendVerificationEmail -> {
                resendVerificationEmail()
            }
        }
    }

    // endregion Methods

}
