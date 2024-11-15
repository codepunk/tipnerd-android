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
import androidx.room.Room
import com.codepunk.tipnerd.BuildConfig
import com.codepunk.tipnerd.data.local.TipnerdDatabase
import com.codepunk.tipnerd.data.local.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {

    // region Methods

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TipnerdDatabase =
        Room.databaseBuilder(
            context = context,
            klass = TipnerdDatabase::class.java,
            name = BuildConfig.DATABASE_NAME
        ).build()

    @Provides
    @Singleton
    fun provideUserDao(database: TipnerdDatabase): UserDao = database.userDao()

    // endregion Methods

}
