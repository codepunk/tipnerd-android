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

package com.codepunk.tipnerd.data.remote.webservice

import arrow.retrofit.adapter.either.ResponseE
import com.codepunk.tipnerd.data.remote.entity.RemoteApiError
import com.codepunk.tipnerd.data.remote.entity.RemoteOauthError
import com.codepunk.tipnerd.data.remote.entity.RemoteOauthToken
import com.codepunk.tipnerd.data.remote.entity.RemoteAuthSuccessResult
import com.codepunk.tipnerd.data.remote.entity.RemoteUser
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface TipnerdWebservice {

    // region Methods

    @GET("/")
    suspend fun home(): ResponseE<RemoteApiError, Any>

    @FormUrlEncoded
    @GET("api/user")
    suspend fun getUser(
        @Header("Authorization") authorization: String
    ): ResponseE<RemoteOauthError, RemoteUser>

    @POST("email/verification-notification")
    suspend fun resendVerificationEmail(): ResponseE<RemoteApiError, RemoteAuthSuccessResult>

    @POST("logout")
    suspend fun logout(): ResponseE<RemoteApiError, RemoteAuthSuccessResult>

    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun oauthToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String,
        @Field("username") username: String,
        @Field("password") password: String,
        @Field("scope") scope: String
    ): ResponseE<RemoteOauthError, RemoteOauthToken>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("password_confirmation") verifyPassword: String
    ): ResponseE<RemoteApiError, RemoteAuthSuccessResult>

    // endregion Methods

}