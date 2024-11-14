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

package com.codepunk.tipnerd.ui.compose.screen.main

import androidx.lifecycle.ViewModel
import com.codepunk.tipnerd.domain.repository.TipnerdRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: TipnerdRepository
) : ViewModel() {

// region Variables

    // We use StateFlow here instead of State/mutableStateOf to keep
    // Compose-related constructs out of ViewModel
    private val _stateFlow: MutableStateFlow<MainState> = MutableStateFlow(MainState())
    val stateFlow = _stateFlow.asStateFlow()

    private var state: MainState
        get() = _stateFlow.value
        set(value) { _stateFlow.value = value }

    // endregion Variables

    // region Methods

    fun onDummy() {
        state = state.copy(
            // TODO
        )
    }

    fun onEvent(event: MainEvent) {
        when (event) {
            MainEvent.DummyEvent -> onDummy()
        }
    }

    // endregion Methods

}
