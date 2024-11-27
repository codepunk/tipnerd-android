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

import com.codepunk.tipnerd.domain.model.OauthError

class OauthException : Exception {

    // region Variables

    val oauthError: OauthError

    // endregion Variables

    // region Constructors

    constructor(
        message: String,
        oauthError: OauthError,
    ) : super(message) {
        this.oauthError = oauthError
    }

    constructor(
        message: String,
        oauthError: OauthError,
        cause: Throwable
    ) : super(
        message,
        cause
    ) {
        this.oauthError = oauthError
    }

    constructor(oauthError: OauthError) : this(
        message = oauthError.message,
        oauthError = oauthError
    )

    constructor(oauthError: OauthError, cause: Throwable) : this(
        message = oauthError.message,
        oauthError = oauthError,
        cause = cause
    )

    // endregion Constructors

}
