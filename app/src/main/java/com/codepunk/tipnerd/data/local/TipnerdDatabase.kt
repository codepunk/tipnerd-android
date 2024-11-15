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

package com.codepunk.tipnerd.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.codepunk.tipnerd.data.local.dao.UserDao
import com.codepunk.tipnerd.data.local.entity.LocalUser
import com.codepunk.tipnerd.data.local.typeconverter.BigDecimalTypeConverter
import com.codepunk.tipnerd.data.local.typeconverter.InstantTypeConverter
import com.codepunk.tipnerd.data.local.typeconverter.LocalDateTimeTypeConverter
import com.codepunk.tipnerd.data.local.typeconverter.LocalDateTypeConverter

@Database(
    version = 1,
    entities = [
        LocalUser::class
    ]
)
@TypeConverters(
    value = [
        BigDecimalTypeConverter::class,
        InstantTypeConverter::class,
        LocalDateTypeConverter::class,
        LocalDateTimeTypeConverter::class,
    ]
)
abstract class TipnerdDatabase : RoomDatabase() {

    // region Methods

    abstract fun userDao(): UserDao

    // endregion Methods

}
