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

package com.codepunk.tipnerd.util.exception

import com.codepunk.tipnerd.domain.model.DataError

class DataException : Exception {

    // region Variables

    val dataError: DataError

    // endregion Variables

    // region Constructors

    constructor(
        message: String,
        dataError: DataError,
    ) : super(message) {
        this.dataError = dataError
    }

    constructor(
        message: String,
        dataError: DataError,
        cause: Throwable
    ) : super(
        message,
        cause
    ) {
        this.dataError = dataError
    }

    constructor(dataError: DataError) : this(
        message = dataError.message,
        dataError = dataError
    )

    constructor(dataError: DataError, cause: Throwable) : this(
        message = dataError.message,
        dataError = dataError,
        cause = cause
    )

    // endregion Constructors

}
