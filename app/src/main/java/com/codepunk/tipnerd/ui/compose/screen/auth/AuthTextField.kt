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

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.codepunk.tipnerd.R

@Composable
fun AuthTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(),
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        modifier = modifier,
        maxLines = 1,
        singleLine = true,
        value = value,
        label = label,
        supportingText = supportingText,
        keyboardOptions = keyboardOptions,
        onValueChange = onValueChange
    )
}

@Composable
fun AuthPasswordTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: @Composable (() -> Unit)? = null,
    supportingText: @Composable (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
    onValueChange: (String) -> Unit = {}
) {
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        modifier = modifier,
        maxLines = 1,
        singleLine = true,
        value = value,
        label = label,
        supportingText = supportingText,
        visualTransformation = if (passwordVisible) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        keyboardOptions = keyboardOptions,
        trailingIcon = {
            val image = if (passwordVisible) {
                painterResource(R.drawable.ic_visibility_black_24)
            }
            else {
                painterResource(R.drawable.ic_visibility_off_black_24)
            }
            val description = if (passwordVisible) {
                stringResource(R.string.content_hide_password)
            } else {
                stringResource(R.string.content_show_password)
            }
            IconButton(onClick = {passwordVisible = !passwordVisible}){
                Icon(
                    painter  = image,
                    contentDescription = description
                )
            }
        },
        onValueChange = onValueChange
    )
}
