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

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import arrow.core.right
import com.codepunk.tipnerd.R
import com.codepunk.tipnerd.domain.model.AuthStatus
import com.codepunk.tipnerd.domain.model.AuthSuccessResult
import com.codepunk.tipnerd.ui.compose.common.showErrorSnackBar
import com.codepunk.tipnerd.ui.compose.preview.ScreenPreviews
import com.codepunk.tipnerd.ui.theme.LocalAppColors
import com.codepunk.tipnerd.ui.theme.LocalSizes
import com.codepunk.tipnerd.ui.theme.TipnerdTheme
import com.codepunk.tipnerd.util.exception.HttpStatusException

@Composable
fun AuthVerificationEmailScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    // Do the following when resend result is "fresh"
    if (state.isResendResultFresh) {
        onEvent(AuthEvent.ConsumeResendResult)
        state.registerResult?.run {
            onLeft {
                if (it !is HttpStatusException) {
                    // HttpStatusExceptions will be handled as "supportingText" below
                    showErrorSnackBar(
                        throwable = it,
                        context = LocalContext.current,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope
                    )
                    onEvent(AuthEvent.ClearResendResult)
                }
            }.onRight {
                // Do we need to do anything here?
            }
        }
    }

    Scaffold(
        modifier = modifier
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(
                    start = LocalSizes.current.padding2xLarge,
                    end = LocalSizes.current.padding2xLarge
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = LocalSizes.current.region2xLarge)
                    .fillMaxWidth()
                    .animateContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = LocalSizes.current.paddingLarge,
                    alignment = Alignment.CenterVertically
                )
            ) {
                Text(
                    modifier = Modifier,
                    text = stringResource(R.string.thanks_for_signing_up)
                )

                state.resendResult?.onRight { result ->
                    Text(
                        modifier = Modifier,
                        color = LocalAppColors.current.primary,
                        fontWeight = FontWeight.Bold,
                        text = result.status.toString(LocalContext.current)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Button(
                        modifier = modifier
                            .width(LocalSizes.current.regionLarge)
                            .height(LocalSizes.current.component),
                        onClick = {
                            onEvent(AuthEvent.ResendVerificationEmail)
                        }
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        } else {
                            Text(
                                text = stringResource(id = R.string.resend_verification_email)
                            )
                        }
                    }

                    Spacer(
                        modifier = Modifier.weight(1f)
                    )

                    TextButton(
                        onClick = {
                            onEvent(AuthEvent.Logout)
                        }
                    ) {
                        Text(
                            text = stringResource(id = R.string.log_out)
                        )
                    }
                }
            }
        }
    }
}

@ScreenPreviews
@Composable
fun ActivityPreviews() {
    TipnerdTheme {
        Scaffold { padding ->
            AuthVerificationEmailScreen(
                modifier = Modifier.padding(padding),
                state = AuthState(
                    resendResult = AuthSuccessResult(
                        status = AuthStatus.VERIFICATION_LINK_SET
                    ).right()
                )
            )
        }
    }
}
