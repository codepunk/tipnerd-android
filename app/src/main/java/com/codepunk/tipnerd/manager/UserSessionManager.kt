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

package com.codepunk.tipnerd.manager

import com.codepunk.tipnerd.domain.model.OauthToken
import com.codepunk.tipnerd.manager.UserSession.Authenticated
import com.codepunk.tipnerd.manager.UserSession.Unauthenticated
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSessionManager @Inject constructor() {

    // region Variables

    private val _userSession = MutableStateFlow<UserSession>(Unauthenticated)
    val userSession: StateFlow<UserSession> = _userSession.asStateFlow()

    val isLoggedIn: Boolean
        get() = userSession.value is Authenticated

    // endregion Variables

    // region Methods

    fun onLogin(userId: Long, oauthToken: OauthToken) {
        _userSession.tryEmit(Authenticated(userId, oauthToken))
    }

    fun update(userSession: UserSession) {
        _userSession.tryEmit(userSession)
    }

    fun onLogout() {
        _userSession.tryEmit(Unauthenticated)
    }

    // endregion Methods

}
