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

import arrow.core.Either
import arrow.core.left
import com.codepunk.tipnerd.BuildConfig
import com.codepunk.tipnerd.data.mapper.toDomain
import com.codepunk.tipnerd.data.mapper.toException
import com.codepunk.tipnerd.data.mapper.toRemote
import com.codepunk.tipnerd.data.util.networkDataResource
import com.codepunk.tipnerd.data.remote.webservice.TipnerdWebservice
import com.codepunk.tipnerd.domain.model.OauthGrantType
import com.codepunk.tipnerd.domain.model.OauthToken
import com.codepunk.tipnerd.domain.repository.OauthException
import com.codepunk.tipnerd.domain.repository.TipnerdRepository
import kotlinx.coroutines.flow.Flow

class TipnerdRepositoryImpl(
    private val webservice: TipnerdWebservice
) : TipnerdRepository {

    // region Methods
    override fun oauthToken(
        grantType: OauthGrantType,
        clientId: String,
        clientSecret: String,
        username: String,
        password: String,
        scope: String
    ): Flow<Either<OauthException, OauthToken>> =
        networkDataResource(
            fetch = {
                try {
                    webservice.oauthToken(
                        grantType = grantType.toRemote(),
                        clientId = clientId,
                        clientSecret = clientSecret,
                        username = username,
                        password = password,
                        scope = scope
                    ).run {
                        body.mapLeft { it.toException() }
                    }
                } catch (cause: Throwable) {
                    OauthException(cause = cause).left()
                }
            }
        ) {
            it.toDomain()
        }

    override fun login(
        username: String,
        password: String
    ): Flow<Either<OauthException, OauthToken>> = oauthToken(
        grantType = OauthGrantType.PASSWORD,
        clientId = BuildConfig.TIPNERD_LOCAL_CLIENT_ID,
        clientSecret = BuildConfig.TIPNERD_LOCAL_CLIENT_SECRET,
        username = username,
        password = password,
        scope = DEFAULT_SCOPE
    )

    // endregion Methods

    // region Companion object

    companion object {

        private const val DEFAULT_SCOPE = "*"

    }

    // endregion Companion object

}
