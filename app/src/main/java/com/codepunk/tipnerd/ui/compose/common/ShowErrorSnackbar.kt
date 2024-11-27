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

package com.codepunk.tipnerd.ui.compose.common

import android.content.Context
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.codepunk.tipnerd.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

@Composable
fun showErrorSnackBar(
    throwable: Throwable?,
    context: Context,
    snackBarHostState: SnackbarHostState,
    coroutineScope: CoroutineScope,
    message: () -> String = {
        when (throwable) {
            is IOException -> context.getString(R.string.error_no_internet_try_again)
            else -> throwable?.localizedMessage ?: context.getString(R.string.error_unknown)
        }
    }
): Boolean {
    LaunchedEffect(snackBarHostState) {
        coroutineScope.launch(Dispatchers.Main) {
            snackBarHostState.showSnackbar(
                message = message()
            )
        }
    }
    return true
}
