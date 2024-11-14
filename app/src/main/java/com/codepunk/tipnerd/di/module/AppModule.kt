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
import android.net.ConnectivityManager
import androidx.credentials.CredentialManager
import com.codepunk.tipnerd.di.qualifier.ApplicationScope
import com.codepunk.tipnerd.di.qualifier.DefaultDispatcher
import com.codepunk.tipnerd.di.qualifier.IoDispatcher
import com.codepunk.tipnerd.di.qualifier.MainDispatcher
import com.codepunk.tipnerd.di.qualifier.MainImmediateDispatcher
import com.codepunk.tipnerd.di.qualifier.UnconfinedDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    // region Methods

    @Singleton
    @Provides
    fun provideCredentialManager(@ApplicationContext context: Context): CredentialManager =
        CredentialManager.create(context)

    @Singleton
    @Provides
    fun provideConnectivityManager(@ApplicationContext context: Context): ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    @Singleton
    @ApplicationScope
    @Provides
    fun provideApplicationCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ) : CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @UnconfinedDispatcher
    @Provides
    fun provideUnconfinedDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

    // endregion Methods

}
