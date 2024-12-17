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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.window.core.layout.WindowWidthSizeClass
import com.codepunk.tipnerd.R
import com.codepunk.tipnerd.ui.compose.common.showErrorSnackBar
import com.codepunk.tipnerd.ui.compose.preview.ScreenPreviews
import com.codepunk.tipnerd.ui.theme.LocalSizes
import com.codepunk.tipnerd.ui.theme.TipnerdTheme
import com.codepunk.tipnerd.ui.theme.util.currentWindowAdaptiveInfoCustom
import com.codepunk.tipnerd.util.exception.ApiException
import com.codepunk.tipnerd.util.exception.HttpStatusException
import kotlin.math.sqrt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthRegisterScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    // Do the following when register result is "fresh"
    if (state.isRegisterResultFresh) {
        onEvent(AuthEvent.ConsumeRegisterResult)
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
                    onEvent(AuthEvent.ClearRegisterResult)
                }
            }.onRight {
                onEvent(AuthEvent.NavigateToEmailVerification)
            }
        }
    }

    val windowWidthSizeClass =
        currentWindowAdaptiveInfoCustom().windowSizeClass.windowWidthSizeClass
    val outerPadding = when (windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> LocalSizes.current.paddingLarge
        WindowWidthSizeClass.MEDIUM -> LocalSizes.current.paddingXLarge
        WindowWidthSizeClass.EXPANDED -> LocalSizes.current.padding2xLarge
        else -> LocalSizes.current.paddingLarge
    }
    val avatarSize = LocalSizes.current.region2xSmall

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.register))
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
            if (LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                // Landscape
                val innerPadding = when (windowWidthSizeClass) {
                    WindowWidthSizeClass.COMPACT -> LocalSizes.current.paddingXLarge
                    WindowWidthSizeClass.MEDIUM -> LocalSizes.current.padding2xLarge
                    WindowWidthSizeClass.EXPANDED -> LocalSizes.current.padding3xLarge
                    else -> LocalSizes.current.paddingLarge
                }

                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        space = innerPadding,
                        alignment = Alignment.CenterHorizontally
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = LocalSizes.current.region2xLarge)
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(
                            space = LocalSizes.current.padding2xLarge,
                            alignment = Alignment.CenterVertically
                        )
                    ) {
                        UserAvatar(
                            modifier = Modifier.width(avatarSize),
                            onClick = { onEvent(AuthEvent.EditAvatar) }
                        )

                        RegisterSubmit(
                            isLoading = state.isLoading,
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

                    RegisterForm(
                        modifier = Modifier
                            .widthIn(max = LocalSizes.current.region2xLarge)
                            .fillMaxWidth(),
                        state = state,
                        onEvent = onEvent
                    )

                    // Increase the padding between text fields & button
                    Spacer(modifier = Modifier)

                    RegisterSubmit(
                        isLoading = state.isLoading,
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
            } else {
                // Portrait (or more precisely, "non-landscape")
                Column(
                    modifier = Modifier
                        .widthIn(max = LocalSizes.current.region2xLarge)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(
                        space = LocalSizes.current.padding2xLarge,
                        alignment = Alignment.CenterVertically
                    )
                ) {
                    UserAvatar(
                        modifier = Modifier.width(avatarSize),
                        onClick = { onEvent(AuthEvent.EditAvatar) }
                    )

                    RegisterForm(
                        modifier = Modifier.fillMaxWidth(),
                        state = state,
                        onEvent = onEvent
                    )

                    RegisterSubmit(
                        isLoading = state.isLoading,
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
        }
    }
}

@Composable
fun RegisterForm(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val lazyListState: LazyListState = rememberLazyListState()

    // Extract any errors for display
    val apiError = (state.registerResult?.leftOrNull()?.cause as? ApiException)?.apiError
    val errorMap = apiError?.errors?.mapValues { (_, errors) ->
        errors.firstOrNull()
    } ?: emptyMap()

    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(
            space = LocalSizes.current.paddingLarge
        ),
        state = lazyListState
    ) {
        item {
            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.name,
                label = { Text(text = stringResource(id = R.string.name)) },
                supportingText = errorMap["name"]?.let {
                    {
                        Text(
                            maxLines = 1,
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                onValueChange = { onEvent(AuthEvent.UpdateName(it)) }
            )
        }

        item {
            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.username,
                label = { Text(text = stringResource(id = R.string.username)) },
                supportingText = errorMap["username"]?.let {
                    {
                        Text(
                            maxLines = 1,
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                onValueChange = { onEvent(AuthEvent.UpdateUsername(it)) }
            )
        }

        item {
            AuthTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.email,
                label = { Text(text = stringResource(id = R.string.email)) },
                supportingText = errorMap["email"]?.let {
                    {
                        Text(
                            maxLines = 1,
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                onValueChange = { onEvent(AuthEvent.UpdateEmail(it)) }
            )
        }

        item {
            AuthPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.password,
                label = { Text(text = stringResource(id = R.string.password)) },
                supportingText = errorMap["password"]?.let {
                    {
                        Text(
                            maxLines = 1,
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                onValueChange = { onEvent(AuthEvent.UpdatePassword(it)) }
            )
        }

        item {
            AuthPasswordTextField(
                modifier = Modifier.fillMaxWidth(),
                value = state.verifyPassword,
                label = { Text(text = stringResource(id = R.string.verify_password)) },
                supportingText = errorMap["password_confirmation"]?.let {
                    {
                        Text(
                            maxLines = 1,
                            text = it,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                },
                onValueChange = { onEvent(AuthEvent.UpdateVerifyPassword(it)) }
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
fun RegisterSubmit(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onSubmit: () -> Unit
) {
    Button(
        modifier = modifier
            .width(LocalSizes.current.region)
            .height(LocalSizes.current.component),
        enabled = !isLoading,
        onClick = { onSubmit() }
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text(
                text = stringResource(id = R.string.register)
            )
        }
    }
}

@ScreenPreviews
@Composable
fun AuthRegisterPreviews() {
    TipnerdTheme {
        Scaffold { padding ->
            AuthRegisterScreen(
                modifier = Modifier.padding(padding),
                state = AuthState()
            )
        }
    }
}
