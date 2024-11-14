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

package com.codepunk.tipnerd.ui.compose.screen.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codepunk.tipnerd.di.qualifier.IoDispatcher
import com.codepunk.tipnerd.domain.repository.TipnerdRepository
import com.codepunk.tipnerd.manager.UserSession.Unauthenticated
import com.codepunk.tipnerd.manager.UserSessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: TipnerdRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val userSessionManager: UserSessionManager
) : ViewModel() {

    // region Variables

    private val isDelaying: MutableStateFlow<Boolean> = MutableStateFlow(true)
    private val isAuthenticating: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = isDelaying.combine(isAuthenticating) { isDelaying, isAuthenticating ->
        isDelaying || isAuthenticating
    }.stateIn(viewModelScope, SharingStarted.Eagerly, true)

    // endregion Variables

    // region Constructor

    init {
        viewModelScope.launch(ioDispatcher) {
            delay(750)
            isDelaying.value = false
        }
        authenticate()
    }

    // endregion Constructor

    // region Methods

    private fun authenticate() {
        viewModelScope.launch(ioDispatcher) {
            val userSession = runBlocking {
                /* TODO dataStore.data.firstOrNull()?.userSession?.toDomain() ?: */ Unauthenticated
            }
            userSessionManager.update(userSession)
            // TODO repository.authenticate().collect { _ -> isAuthenticating.value = false }
            isAuthenticating.value = false
        }
    }

    // endregion Methods

}