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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.codepunk.tipnerd.ui.compose.preview.ScreenPreviews
import com.codepunk.tipnerd.ui.theme.LocalAppColors
import com.codepunk.tipnerd.ui.theme.TipnerdTheme

@Composable
fun AuthScreen(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier,
            color = LocalAppColors.current.onBackground,
            text = "Auth"
        )
    }
}

@ScreenPreviews
@Composable
fun AuthPreviews() {
    TipnerdTheme {
        Scaffold { padding ->
            AuthScreen(
                modifier = Modifier.padding(padding),
                state = AuthState()
            )
        }
    }
}
