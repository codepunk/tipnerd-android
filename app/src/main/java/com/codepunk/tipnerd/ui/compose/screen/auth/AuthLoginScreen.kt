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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import com.codepunk.tipnerd.R
import com.codepunk.tipnerd.ui.compose.common.showErrorSnackBar
import com.codepunk.tipnerd.ui.compose.preview.ScreenPreviews
import com.codepunk.tipnerd.ui.theme.LocalSizes
import com.codepunk.tipnerd.ui.theme.TipnerdTheme
import com.codepunk.tipnerd.util.exception.HttpStatusException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthSignInScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var showRegionPicker by rememberSaveable { mutableStateOf(false) }
    val modalBottomSheetState: SheetState = rememberModalBottomSheetState()

    // Do the following when login result is "fresh"
    if (state.isLoginResultFresh) {
        onEvent(AuthEvent.ConsumeLoginResult)
        state.loginResult?.run {
            onLeft { error ->
                if (error.cause !is HttpStatusException) {
                    // HttpStatusExceptions will be handled differently
                    showErrorSnackBar(
                        cause = error.cause,
                        context = LocalContext.current,
                        snackBarHostState = snackBarHostState,
                        coroutineScope = coroutineScope
                    )
                    onEvent(AuthEvent.ClearLoginResult)
                }
            }.onRight {
                /*
                if (success) {
                    onEvent(AuthEvent.NavigateToOtp)
                }
                 */
            }
        }
    }

    Scaffold(
        modifier = modifier,
        /*
        topBar = {
            HollarHypeTopAppBar(
                onNavigateUp = { onEvent(AuthEvent.NavigateUp) }
            )
        },
         */
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        val layoutMargin = LocalSizes.current.padding2xLarge.times(2)
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(
                    start = layoutMargin,
                    end = layoutMargin
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = LocalSizes.current.region2xLarge)
                    .fillMaxWidth()
                    .padding(
                        bottom = LocalSizes.current.paddingXLarge
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement
                    .spacedBy(LocalSizes.current.paddingLarge)
            ) {
                /*
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                val phoneNumberError = state.loginResult?.leftOrNull()
                    ?.errors?.getOrNull(0) ?: ""

                PhoneNumber(
                    regionCode = state.region.regionCode,
                    countryCode = state.region.countryCode,
                    phoneNumber = state.phoneNumber,
                    phoneNumberError = phoneNumberError,
                    onCountryCodeClick = { showRegionPicker = true },
                    onPhoneNumberChange = {
                        onEvent(AuthEvent.ClearLoginResult)
                        onEvent(AuthEvent.UpdatePhoneNumber(it))
                    },
                    onSubmit = {
                        keyboardController?.hide()
                        onEvent(AuthEvent.ClearLoginResult)
                        onEvent(
                            AuthEvent.SignIn(
                                region = state.region,
                                phoneNumber = state.phoneNumber
                            )
                        )
                    }
                )

                TextButton(
                    modifier = Modifier.padding(top = sizes.paddingLarge),
                    onClick = { onEvent(AuthEvent.RegisterNewPhoneNumber) }
                ) {
                    Text(
                        text = stringResource(id = R.string.phone_number_changed),
                        color = MaterialTheme.colorScheme.onBackground,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )

                 */

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    /*
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                     */
                    maxLines = 1,
                    singleLine = true,
                    value = state.username,
                    label = {
                        Text(
                            text = stringResource(id = R.string.name)
                        )
                    },
                    onValueChange = {
                        onEvent(AuthEvent.ClearLoginResult)
                        onEvent(AuthEvent.UpdateUsername(it))
                    }
                )

                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = state.password,

                    /*
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                     */
                    maxLines = 1,
                    singleLine = true,
                    label = {
                        Text(
                            text = stringResource(id = R.string.password),
                        )
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        val image = if (passwordVisible)
                            painterResource(R.drawable.ic_visibility_black_24)
                        else painterResource(R.drawable.ic_visibility_off_black_24)

                        // Please provide localized description for accessibility services
                        val description = if (passwordVisible) "Hide password" else "Show password"

                        IconButton(onClick = {passwordVisible = !passwordVisible}){
                            Icon(painter  = image, description)
                        }
                    },
                    onValueChange = {
                        onEvent(AuthEvent.ClearLoginResult)
                        onEvent(AuthEvent.UpdatePassword(it))
                    }
                )

                Button(
                    modifier = Modifier
                        .width(LocalSizes.current.region)
                        .height(LocalSizes.current.component),
                    enabled = (!state.isLoading),
                    onClick = {
                        onEvent(AuthEvent.ClearLoginResult)
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
                            text = stringResource(id = R.string.log_in),
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
            }
        }
    }

    /*
    if (showRegionPicker) {
        ModalBottomSheet(
            onDismissRequest = { showRegionPicker = false },
            sheetState = modalBottomSheetState
        ) {
            CountryCodePicker(
                modifier = Modifier.padding(sizes.paddingXLarge),
                onItemSelected = {
                    coroutineScope.launch(Dispatchers.Main) {
                        modalBottomSheetState.hide()
                        showRegionPicker = false
                    }
                    onEvent(AuthEvent.UpdateRegion(it))
                }
            )
        }
    }
     */
}

@ScreenPreviews
@Composable
fun AuthSignInPreviews() {
    TipnerdTheme {
        Scaffold { padding ->
            AuthSignInScreen(
                modifier = Modifier.padding(padding),
                state = AuthState()
            )
        }
    }
}
