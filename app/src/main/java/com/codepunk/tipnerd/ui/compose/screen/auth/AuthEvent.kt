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

sealed interface AuthEvent {

    // Update data

    data class UpdateEmail(val value: String): AuthEvent
    data class UpdateName(val value: String): AuthEvent
    data class UpdatePassword(val value: String): AuthEvent
    data class UpdateUsername(val value: String): AuthEvent
    data class UpdateVerifyPassword(val value: String): AuthEvent

    // Navigation

    data object NavigateUp : AuthEvent
    data object NavigateToLogin : AuthEvent
    data object NavigateToOptions : AuthEvent
    data object NavigateToRegister : AuthEvent
    data object NavigateToEmailVerification : AuthEvent
    data object NavigateToMain : AuthEvent

    // Events/results

    data object ConsumeRegisterResult: AuthEvent
    data object ClearRegisterResult: AuthEvent
    data object ConsumeLoginResult: AuthEvent
    data object ConsumeLogoutResult: AuthEvent
    data object ClearLogoutResult: AuthEvent
    data object ClearLoginResult: AuthEvent
    data object ConsumeResendResult: AuthEvent
    data object ClearResendResult: AuthEvent

    // User actions

    data object EditAvatar: AuthEvent

    data class Login(
        val username: String,
        val password: String
    ): AuthEvent

    data object Logout: AuthEvent

    data class Register(
        val name: String,
        val username: String,
        val email: String,
        val password: String,
        val verifyPassword: String
    ): AuthEvent

    data object ResendVerificationEmail: AuthEvent

}
