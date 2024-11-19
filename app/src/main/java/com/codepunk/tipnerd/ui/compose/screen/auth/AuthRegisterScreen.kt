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

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.codepunk.tipnerd.R
import com.codepunk.tipnerd.ui.compose.preview.ScreenPreviews
import com.codepunk.tipnerd.ui.theme.LocalSizes
import com.codepunk.tipnerd.ui.theme.TipnerdTheme
import com.codepunk.tipnerd.ui.theme.util.currentWindowAdaptiveInfoCustom
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthSignUpScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val sizes = LocalSizes.current
    val keyboardController = LocalSoftwareKeyboardController.current

    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    var showRegionPicker by rememberSaveable { mutableStateOf(false) }
    val modalBottomSheetState: SheetState = rememberModalBottomSheetState()

    // Do the following when signup result is "fresh"
    // ...

    Scaffold(
        modifier = modifier,
        /*
        topBar = {
            TopAppBar(
                title = { stringResource(R.string.app_name) }
                // onNavigateUp = { onEvent(AuthEvent.NavigateUp) }
            )
        },
         */
        snackbarHost = { SnackbarHost(snackBarHostState) }
    ) { innerPadding ->
        val layoutMargin = sizes.padding2xLarge
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
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                SignUpLandscape(
                    state = state,
                    onEvent = onEvent,
                    onShowRegionPicker = { showRegionPicker = true }
                )
            } else {
                SignUpNonLandscape(
                    state = state,
                    onEvent = onEvent,
                    onShowRegionPicker = { showRegionPicker = true }
                )
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

@Composable
fun SignUpNonLandscape(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    onShowRegionPicker: () -> Unit
) {
    val sizes = LocalSizes.current

    // Avatar size is based on width
    val windowSizeClass = currentWindowAdaptiveInfoCustom().windowSizeClass.windowWidthSizeClass
    val avatarSize = when (windowSizeClass) {
        WindowWidthSizeClass.COMPACT -> sizes.component2xLarge
        else -> sizes.region
    }

    Column(
        modifier = Modifier
            .widthIn(max = sizes.region2xLarge)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(
            space = sizes.padding2xLarge,
            alignment = Alignment.CenterVertically
        )
    ) {
        UserAvatar(
            modifier = Modifier.width(avatarSize),
            onClick = { onEvent(AuthEvent.EditAvatar) }
        )

        SignUpForm(
            modifier = Modifier.fillMaxWidth(),
            name = state.name,
            username = state.username,
            email = state.email,
            password = state.password,
            verifyPassword = state.verifyPassword,
            onNameChange = { onEvent(AuthEvent.UpdateName(it)) },
            onUsernameChange = { onEvent(AuthEvent.UpdateUsername(it)) },
            onEmailChange = { onEvent(AuthEvent.UpdateEmail(it)) },
            onPasswordChange = { onEvent(AuthEvent.UpdatePassword(it)) },
            onVerifyPasswordChange = { onEvent(AuthEvent.UpdateVerifyPassword(it)) }
        )

        SignUpSubmit(
            modifier = Modifier.fillMaxWidth(),
            onSubmit = {
                onEvent(
                    AuthEvent.Register(
                        name = state.name,
                        username = state.username,
                        email = state.email,
                        password = state.password,
                        verifyPassword = state.verifyPassword
                    )
                )
            }
        )
    }
}

@Composable
fun SignUpLandscape(
    state: AuthState,
    onEvent: (AuthEvent) -> Unit,
    onShowRegionPicker: () -> Unit
) {
    val sizes = LocalSizes.current

    // Avatar size is based on height
    val windowSizeClass = currentWindowAdaptiveInfoCustom().windowSizeClass.windowHeightSizeClass
    val avatarSize = when (windowSizeClass) {
        WindowHeightSizeClass.COMPACT -> sizes.component2xLarge
        else -> sizes.region
    }

    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterEnd
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = sizes.region3xLarge)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(
                    space = sizes.padding2xLarge,
                    alignment = Alignment.CenterVertically
                )
            ) {
                UserAvatar(
                    modifier = Modifier.width(avatarSize),
                    onClick = { onEvent(AuthEvent.EditAvatar) }
                )

                SignUpSubmit(
                    modifier = Modifier.fillMaxWidth(),
                    onSubmit = {
                        onEvent(
                            AuthEvent.Register(
                                name = state.name,
                                username = state.username,
                                email = state.email,
                                password = state.password,
                                verifyPassword = state.verifyPassword
                            )
                        )
                    }
                )
            }
        }

        Spacer(
            modifier = Modifier
                .width(sizes.padding2xLarge)
                .fillMaxHeight()
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            contentAlignment = Alignment.CenterStart
        ) {
            SignUpForm(
                modifier = Modifier
                    .widthIn(max = sizes.region3xLarge)
                    .fillMaxWidth(),
                name = state.name,
                username = state.username,
                email = state.email,
                password = state.password,
                verifyPassword = state.verifyPassword,
                onNameChange = { onEvent(AuthEvent.UpdateName(it)) },
                onUsernameChange = { onEvent(AuthEvent.UpdateUsername(it)) },
                onEmailChange = { onEvent(AuthEvent.UpdateEmail(it)) },
                onPasswordChange = { onEvent(AuthEvent.UpdatePassword(it)) },
                onVerifyPasswordChange = { onEvent(AuthEvent.UpdateVerifyPassword(it)) }
            )
        }
    }
}

@Composable
fun UserAvatar(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier.aspectRatio(1f)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(CircleShape)
                .clickable(onClick = onClick),
            painter = painterResource(R.drawable.img_default_user_96),
            contentDescription = stringResource(id = R.string.app_name)
        )

        // Determine the size of a camera button whose center will rest
        // on the edge of the avatar circle
        val sqrt2 = sqrt(2f)
        val iconFraction = 1f - ((sqrt2 - 1f) / sqrt2)

        Row(
            modifier = Modifier.fillMaxSize()
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth(iconFraction)
                    .fillMaxSize()
            )

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxSize(iconFraction)
                )

                FilledIconButton(
                    modifier = Modifier.fillMaxSize(),
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.inverseSurface
                    ),
                    onClick = onClick
                ) {
                    Icon(
                        modifier = Modifier.fillMaxSize(1 / sqrt2),
                        painter = painterResource(id = R.drawable.ic_camera_black_24),
                        tint = MaterialTheme.colorScheme.inverseOnSurface,
                        contentDescription = ""
                    )
                }
            }
        }
    }
}

@Composable
fun SignUpForm(
    modifier: Modifier = Modifier,
    name: String,
    username: String,
    email: String,
    password: String,
    verifyPassword: String,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onVerifyPasswordChange: (String) -> Unit
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }
    var verifyPasswordVisible by rememberSaveable { mutableStateOf(false) }

    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(LocalSizes.current.padding)
    ) {
        item {
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
                value = name,
                label = {
                    Text(
                        text = stringResource(id = R.string.name)
                    )
                },
                onValueChange = onNameChange
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = username,
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
                        text = stringResource(id = R.string.username),
                    )
                },
                onValueChange = onUsernameChange
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = email,
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
                        text = stringResource(id = R.string.email),
                    )
                },
                onValueChange = onEmailChange
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,

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
                onValueChange = onPasswordChange
            )
        }

        item {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = verifyPassword,
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
                        text = stringResource(id = R.string.verifyPassword),
                    )
                },
                visualTransformation = if (verifyPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    val image = if (verifyPasswordVisible)
                        painterResource(R.drawable.ic_visibility_black_24)
                    else painterResource(R.drawable.ic_visibility_off_black_24)

                    // Please provide localized description for accessibility services
                    val description = if (verifyPasswordVisible) "Hide password" else "Show password"

                    IconButton(onClick = {verifyPasswordVisible = !verifyPasswordVisible}){
                        Icon(painter  = image, description)
                    }
                },
                onValueChange = onVerifyPasswordChange
            )
        }

        /*
        item {
            PhoneNumber(
                modifier = modifier.fillMaxWidth(),
                regionCode = region.regionCode,
                countryCode = region.countryCode,
                phoneNumber = phoneNumber,
                onCountryCodeClick = { onShowRegionPicker() },
                onPhoneNumberChange = onPhoneNumberChange
            )
        }
         */
    }
}

@Composable
fun SignUpSubmit(
    modifier: Modifier = Modifier,
    onSubmit: () -> Unit
) {
    val sizes = LocalSizes.current

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(sizes.paddingXLarge)
    ) {
        Text(
            text = "Disclaimer",
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center
        )

        Button(
            modifier = Modifier
                .width(sizes.region)
                .height(sizes.component),
            onClick = { onSubmit() }
        ) {
            Text(
                text = stringResource(id = R.string.register),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@ScreenPreviews
@Composable
fun AuthRegisterPreviews() {
    TipnerdTheme {
        Scaffold { padding ->
            AuthSignUpScreen(
                modifier = Modifier.padding(padding),
                state = AuthState()
            )
        }
    }
}
