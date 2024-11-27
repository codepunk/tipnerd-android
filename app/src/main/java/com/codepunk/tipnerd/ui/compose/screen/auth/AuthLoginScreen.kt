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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.window.core.layout.WindowWidthSizeClass
import com.codepunk.tipnerd.R
import com.codepunk.tipnerd.ui.compose.common.showErrorSnackBar
import com.codepunk.tipnerd.ui.compose.preview.ScreenPreviews
import com.codepunk.tipnerd.ui.theme.LocalSizes
import com.codepunk.tipnerd.ui.theme.TipnerdTheme
import com.codepunk.tipnerd.ui.theme.util.currentWindowAdaptiveInfoCustom
import com.codepunk.tipnerd.util.exception.HttpStatusException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthLoginScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    // Do the following when login result is "fresh"
    if (state.isLoginResultFresh) {
        onEvent(AuthEvent.ConsumeLoginResult)
        state.loginResult?.run {
            onLeft { error ->
                val cause = error.cause ?: error
                if (cause !is HttpStatusException) {
                    // HttpStatusExceptions will be handled differently
                    showErrorSnackBar(
                        throwable = cause,
                        context = LocalContext.current,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope
                    )
                    onEvent(AuthEvent.ClearLoginResult)
                }
            }.onRight {
                val success = true // TODO TEMP
                if (success) {
                    onEvent(AuthEvent.NavigateToMain)
                }
            }
        }
    }

    val windowWidthSizeClass = currentWindowAdaptiveInfoCustom().windowSizeClass.windowWidthSizeClass
    val outerPadding = when (windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> LocalSizes.current.paddingLarge
        WindowWidthSizeClass.MEDIUM -> LocalSizes.current.paddingXLarge
        WindowWidthSizeClass.EXPANDED -> LocalSizes.current.padding2xLarge
        else -> LocalSizes.current.paddingLarge
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.log_in))
                },
                navigationIcon = {
                    IconButton(
                        onClick = { onEvent(AuthEvent.NavigateUp) }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(id = R.string.content_back)
                        )
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = outerPadding,
                    end = outerPadding
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = LocalSizes.current.region2xLarge)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = LocalSizes.current.paddingLarge,
                    alignment = Alignment.CenterVertically
                )
            ) {
                AuthTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.username,
                    label = { Text(text = stringResource(id = R.string.username)) },
                    onValueChange = { onEvent(AuthEvent.UpdateUsername(it)) }
                )

                AuthPasswordTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,
                    label = { Text(text = stringResource(id = R.string.password)) },
                    onValueChange = { onEvent(AuthEvent.UpdatePassword(it)) }
                )

                // Increase the padding between text fields & button
                Spacer(modifier = Modifier)

                Button(
                    modifier = modifier
                        .width(LocalSizes.current.region)
                        .height(LocalSizes.current.component),
                    onClick = {
                        onEvent(
                            AuthEvent.Login(
                                username = state.username,
                                password = state.password
                            )
                        )
                    }
                ) {
                    if (state.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                            color = MaterialTheme.colorScheme.primary
                        )
                    } else {
                        Text(
                            text = stringResource(id = R.string.log_in)
                        )
                    }

                }
            }
        }
    }
}

@ScreenPreviews
@Composable
fun AuthLoginPreviews() {
    TipnerdTheme {
        Scaffold { padding ->
            AuthLoginScreen(
                modifier = Modifier.padding(padding),
                state = AuthState()
            )
        }
    }
}
