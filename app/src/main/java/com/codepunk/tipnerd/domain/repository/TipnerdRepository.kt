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

package com.codepunk.tipnerd.domain.repository

import arrow.core.Either
import arrow.core.Ior
import com.codepunk.tipnerd.domain.model.OAuthToken
import com.codepunk.tipnerd.domain.model.SuccessResult
import com.codepunk.tipnerd.domain.model.User
import kotlinx.coroutines.flow.Flow

interface TipnerdRepository {

    fun register(
        username: String,
        name: String,
        email: String,
        password: String,
        verifyPassword: String
    ): Flow<Either<Exception, SuccessResult>>

    fun login(
        username: String,
        password: String
    ): Flow<Either<Exception, OAuthToken>>

    fun refreshToken(
        refreshToken: String
    ): Flow<Either<Exception, OAuthToken>>

    fun authenticate(
        userId: Long,
        oauthToken: OAuthToken
    ): Flow<Ior<Exception, User?>>

}
