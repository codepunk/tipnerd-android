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

import arrow.core.Either
import com.codepunk.tipnerd.domain.model.OAuthToken
import com.codepunk.tipnerd.domain.model.SuccessResult

data class AuthState(

    // Flags

    val isLoading: Boolean = false,

    // Data

    val email: String = "",
    val name: String = "",
    val password: String = "",
    val username: String = "",
    val verifyPassword: String = "",

    // Events/results

    val isLoginResultFresh: Boolean = false,
    val loginResult: Either<Exception, OAuthToken>? = null,

    val isRegisterResultFresh: Boolean = false,
    val registerResult: Either<Exception, SuccessResult>? = null

)
