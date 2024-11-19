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

import kotlinx.serialization.Serializable

/**
 * An [AuthRoute] is a "sub-route" of sorts that describes a pathway through [AuthScreen].
 */
@Serializable
sealed class AuthRoute {

    @Serializable
    data object AuthOptions : AuthRoute()

    @Serializable
    data object AuthRegister : AuthRoute()

    @Serializable
    data object AuthLogin : AuthRoute()

}
