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

import com.codepunk.tipnerd.data.local.entity.LocalUser
import com.codepunk.tipnerd.data.remote.entity.RemoteUser
import com.codepunk.tipnerd.domain.model.User

fun RemoteUser.toLocal(): LocalUser = LocalUser(
    id = id,
    name = name,
    username = username,
    createdAt = createdAt,
    updatedAt = updatedAt
)

fun LocalUser.toDomain(): User = User(
    id = id,
    name = name,
    username = username,
    createdAt = createdAt,
    updatedAt = updatedAt
)
