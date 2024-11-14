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

package com.codepunk.tipnerd.di.module

import android.content.Context
import arrow.retrofit.adapter.either.EitherCallAdapterFactory
import com.codepunk.tipnerd.data.remote.interceptor.AuthInterceptor
import com.codepunk.tipnerd.data.remote.interceptor.NetworkConnectionInterceptor
import com.codepunk.tipnerd.data.remote.interceptor.UserAgentInterceptor
import com.codepunk.tipnerd.BuildConfig
import com.codepunk.tipnerd.data.remote.webservice.TipnerdWebservice
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    // region Methods

    @Singleton
    @Provides
    fun provideCache(@ApplicationContext context: Context): Cache =
        Cache(context.cacheDir, BuildConfig.OK_HTTP_CLIENT_CACHE_SIZE)

    @Singleton
    @Provides
    fun provideDiscogsOkHttpClient(
        cache: Cache,
        networkConnectionInterceptor: NetworkConnectionInterceptor,
        userAgentInterceptor: UserAgentInterceptor,
        authInterceptor: AuthInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .cache(cache)
        .addInterceptor(networkConnectionInterceptor)
        .addInterceptor(userAgentInterceptor)
        .addInterceptor(authInterceptor)
        .build()

    @Singleton
    @Provides
    fun provideJson(): Json = Json {
        ignoreUnknownKeys = true
        isLenient = true
        encodeDefaults = true
        prettyPrint = true
        coerceInputValues = true
    }

    @Singleton
    @Provides
    fun provideEitherCallAdapterFactory(): EitherCallAdapterFactory =
        EitherCallAdapterFactory.create()

    @Singleton
    @Provides
    fun provideConverterFactory(networkJson: Json): Converter.Factory =
        networkJson.asConverterFactory("application/json".toMediaType())

    @Singleton
    @Provides
    fun provideRetrofit(
        client: OkHttpClient,
        eitherCallAdapterFactory: EitherCallAdapterFactory,
        converterFactory: Converter.Factory
    ): Retrofit = Retrofit.Builder()
        .client(client)
        .baseUrl(BuildConfig.BASE_URL)
        .addCallAdapterFactory(eitherCallAdapterFactory)
        .addConverterFactory(converterFactory)
        .build()

    @Singleton
    @Provides
    fun provideTipnerdWebService(
        retrofit: Retrofit
    ): TipnerdWebservice = retrofit.create(TipnerdWebservice::class.java)

    // endregion Methods

}
