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

package com.codepunk.tipnerd.domain.model

import android.content.Context
import androidx.annotation.StringRes
import com.codepunk.tipnerd.R

/**
 * Enum class representing authentication token types in accounts managed by Android.
 */
enum class AuthTokenType(
    /**
     * The value associated with this authentication token type.
     */
    val value: String,

    /**
     * A string resource ID pointing to a user-friendly name for the authentication token type.
     */
    @StringRes private val descriptionResId: Int
) {

    // region Values

    DEFAULT("default", R.string.auth_token_type_default),
    UNKNOWN("unknown", R.string.auth_token_type_unknown);

    // endregion Values

    // region Methods

    /**
     * Returns a user-friendly (readable) description for this authentication token type using
     * the supplied [context].
     */
    fun getDescription(context: Context) = context.getString(descriptionResId)

    // endregion Methods

    // region Companion object

    companion object {

        // region Methods

        @JvmStatic
        fun get(
            value: String,
            defaultValue: AuthTokenType = DEFAULT
        ): AuthTokenType = entries.firstOrNull { it.value == value } ?: defaultValue

        // endregion Methods

    }

    // endregion Companion object
}