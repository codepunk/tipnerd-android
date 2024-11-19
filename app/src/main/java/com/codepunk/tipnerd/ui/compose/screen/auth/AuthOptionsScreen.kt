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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.codepunk.tipnerd.R
import com.codepunk.tipnerd.ui.compose.preview.ScreenPreviews
import com.codepunk.tipnerd.ui.theme.LocalSizes
import com.codepunk.tipnerd.ui.theme.TipnerdTheme

@Composable
fun AuthOptionsScreen(
    modifier: Modifier = Modifier,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val sizes = LocalSizes.current

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(all = sizes.paddingXLarge)
    ) {
        Column(
            modifier = Modifier
                .widthIn(max = sizes.region2xLarge)
                .fillMaxSize()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(
                space = sizes.paddingLarge,
                alignment = Alignment.CenterVertically
            )
        ) {
            Image(
                modifier = Modifier.fillMaxWidth(0.45f),
                painter = painterResource(R.drawable.img_splash_icon_240),
                contentDescription = stringResource(id = R.string.app_name)
            )

            Button(
                modifier = Modifier
                    .width(sizes.region)
                    .height(sizes.component),
                onClick = { onEvent(AuthEvent.NavigateToRegister) }
            ) {
                Text(
                    text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.labelLarge
                )
            }

            Button(
                modifier = Modifier
                    .width(sizes.region)
                    .height(sizes.component),
                onClick = { onEvent(AuthEvent.NavigateToLogin) }
            ) {
                Text(
                    text = stringResource(id = R.string.log_in),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@ScreenPreviews
@Composable
fun AuthOptionsPreviews() {
    TipnerdTheme {
        Scaffold { padding ->
            AuthOptionsScreen(
                modifier = Modifier.padding(padding)
            )
        }
    }
}