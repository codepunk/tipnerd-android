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

package com.codepunk.tipnerd.data.mapper

import com.codepunk.tipnerd.data.remote.entity.RemoteOauthError
import com.codepunk.tipnerd.data.remote.entity.RemoteOauthGrantType
import com.codepunk.tipnerd.data.remote.entity.RemoteOauthTokenType
import com.codepunk.tipnerd.domain.model.OAuthGrantType
import com.codepunk.tipnerd.domain.model.OAuthTokenType
import com.codepunk.tipnerd.domain.model.OauthError

// region Methods

fun OAuthGrantType.toRemote(): RemoteOauthGrantType = RemoteOauthGrantType.valueOf(this.name)

fun RemoteOauthTokenType.toDomain(): OAuthTokenType = OAuthTokenType.valueOf(this.name)

fun RemoteOauthError.toDomain(): OauthError = OauthError(
    error = error,
    errorDescription = errorDescription,
    hint = hint,
    message = message
)

// endregion Methods
