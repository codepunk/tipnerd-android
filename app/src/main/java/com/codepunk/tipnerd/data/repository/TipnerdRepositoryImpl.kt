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

package com.codepunk.tipnerd.data.repository

import android.util.Log
import arrow.core.Either
import arrow.core.Ior
import arrow.core.left
import com.codepunk.tipnerd.BuildConfig
import com.codepunk.tipnerd.data.local.TipnerdDatabase
import com.codepunk.tipnerd.data.local.dao.UserDao
import com.codepunk.tipnerd.data.mapper.toDomain
import com.codepunk.tipnerd.data.mapper.toLocal
import com.codepunk.tipnerd.data.mapper.toRemote
import com.codepunk.tipnerd.data.util.networkDataResource
import com.codepunk.tipnerd.data.remote.webservice.TipnerdWebservice
import com.codepunk.tipnerd.data.util.cachedDataResource
import com.codepunk.tipnerd.domain.model.OAuthGrantType
import com.codepunk.tipnerd.domain.model.OAuthToken
import com.codepunk.tipnerd.domain.model.AuthSuccessResult
import com.codepunk.tipnerd.domain.model.User
import com.codepunk.tipnerd.util.exception.OauthException
import com.codepunk.tipnerd.domain.repository.TipnerdRepository
import com.codepunk.tipnerd.util.exception.ApiException
import com.codepunk.tipnerd.util.exception.HttpStatusException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TipnerdRepositoryImpl(
    private val database: TipnerdDatabase,
    private val webservice: TipnerdWebservice
) : TipnerdRepository {

    private val userDao: UserDao by lazy { database.userDao() }

    // region Methods

    override fun authenticate(
        userId: Long,
        oauthToken: OAuthToken
    ): Flow<Ior<Exception, User?>> = cachedDataResource(
        query = {
            userDao.getUser(userId = userId).map { it?.toDomain() }
        },
        fetch = {
            try {
                webservice.getUser(
                    authorization = "Bearer ${oauthToken.accessToken}"
                ).body.mapLeft { OauthException(it.toDomain()) }
            } catch (e: Exception) {
                // TODO If there's an error here, we might need to
                //  try getting a new auth token etc.
                e.left()
            }
        },
        saveFetchResult = { userDao.insertUser(it.toLocal()) }
    )

    @Suppress("SameParameterValue")
    private fun oauthToken(
        grantType: OAuthGrantType,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String,
        scope: String
    ): Flow<Either<Exception, OAuthToken>> = networkDataResource(
        fetch = {
            try {
                webservice.oauthToken(
                    grantType = grantType.toRemote().value,
                    clientId = clientId,
                    clientSecret = clientSecret,
                    username = username,
                    password = password,
                    scope = scope
                ).run {
                    body.mapLeft {
                        HttpStatusException(
                            code = code,
                            message = message,
                            cause = OauthException(it.toDomain())
                        )
                    }
                }
            } catch (e: Exception) { e.left() }
        }
    ) { it.toDomain() }

    /**
     * TODO Make this use blaze login endpoint first?
     */
    override fun login(
        username: String,
        password: String
    ): Flow<Either<Exception, OAuthToken>> = oauthToken(
        grantType = OAuthGrantType.PASSWORD,
        clientId = BuildConfig.TIPNERD_LOCAL_CLIENT_ID,
        clientSecret = BuildConfig.TIPNERD_LOCAL_CLIENT_SECRET,
        username = username,
        password = password,
        scope = DEFAULT_SCOPE
    )

    override fun refreshToken(
        refreshToken: String
    ): Flow<Either<Exception, OAuthToken>> = oauthToken(
        grantType = OAuthGrantType.REFRESH_TOKEN,
        clientId = BuildConfig.TIPNERD_LOCAL_CLIENT_ID,
        clientSecret = BuildConfig.TIPNERD_LOCAL_CLIENT_SECRET,
        username = "",
        password = "",
        scope = DEFAULT_SCOPE
    )

    override fun register(
        username: String,
        name: String,
        email: String,
        password: String,
        verifyPassword: String
    ): Flow<Either<Exception, AuthSuccessResult>> = networkDataResource(
        fetch = {
            try {
                webservice.register(
                    username,
                    name,
                    email,
                    password,
                    verifyPassword
                ).run {
                    body.mapLeft {
                        HttpStatusException(
                            code = code,
                            message = message,
                            cause = ApiException(it.toDomain())
                        )
                    }
                }
            } catch (e: Exception) { e.left() }
        }
    ) { it.toDomain() }

    override fun resendVerificationEmail(): Flow<Either<Exception, AuthSuccessResult>> =
        networkDataResource(
            fetch = {
                webservice.resendVerificationEmail().run {
                    body.mapLeft {
                        HttpStatusException(
                            code = code,
                            message = message,
                            cause = ApiException(it.toDomain())
                        )
                    }
                }
            }
        ) { it.toDomain() }

    // endregion Methods

    // region Companion object

    companion object {

        private const val DEFAULT_SCOPE = "*"

    }

    // endregion Companion object

}
