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

package com.codepunk.tipnerd.data.remote.entity

import kotlinx.serialization.SerialName

/**
 * An enumerated class that represents how an auth token will be generated and presented for
 * OAuth calls.
 */
enum class RemoteOauthTokenType {

    // region Values

    /**
     * A security token with the property that any party in possession of the token (a "bearer")
     * can use the token in any way that any other party in possession of it can.  Using a bearer
     * token does not require a bearer to prove possession of cryptographic key material
     * (proof-of-possession).
     */
    @SerialName("Bearer")
    BEARER,

    /**
     * Message Authorization Code: A security token with the property that provides a method for
     * making authenticated HTTP requests with partial cryptographic verification of the request,
     * covering the HTTP method, request URI, host, and in some cases the request body.
     */
    @SerialName("MAC")
    MAC

    // endregion Values

}
