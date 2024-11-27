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

import com.codepunk.tipnerd.domain.model.ApiError

class ApiException : Exception {

    // region Variables

    val apiError: ApiError

    // endregion Variables

    // region Constructors

    constructor(
        message: String,
        apiError: ApiError,
    ) : super(message) {
        this.apiError = apiError
    }

    constructor(
        message: String,
        apiError: ApiError,
        cause: Throwable
    ) : super(
        message,
        cause
    ) {
        this.apiError = apiError
    }

    constructor(apiError: ApiError) : this(
        message = apiError.message,
        apiError = apiError
    )

    constructor(apiError: ApiError, cause: Throwable) : this(
        message = apiError.message,
        apiError = apiError,
        cause = cause
    )

    // endregion Constructors

}
