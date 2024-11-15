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

import com.codepunk.tipnerd.util.http.HttpStatus

class HttpStatusException : RuntimeException {

    // region Properties

    /**
     * The [HttpStatus] that resulted in this exception.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    val httpStatus: HttpStatus

    // endregion Properties

    // region Constructors

    /**
     * Constructs a new HTTP status exception with using the specified HTTP status [code] and with
     * null as its detail message.
     */
    @Suppress("unused")
    constructor(code: Int) : this(HttpStatus.lookup(code))

    /**
     * Constructs a new HTTP status exception with the specified [httpStatus] and with null as its
     * detail message.
     */
    constructor(httpStatus: HttpStatus) : super() {
        this.httpStatus = httpStatus
    }

    /**
     * Constructs a new HTTP status exception with using the specified HTTP status [code] and with
     * the specified detail [message].
     */
    @Suppress("Unused")
    constructor(code: Int, message: String?) : this(HttpStatus.lookup(code), message)

    /**
     * Constructs a new HTTP status exception with the specified [httpStatus] and detail [message].
     */
    constructor(httpStatus: HttpStatus, message: String?) : super(message) {
        this.httpStatus = httpStatus
    }

    /**
     * Constructs a new runtime exception using the specified HTTP status [code] and with the
     * specified detail [message] and [cause].
     */
    @Suppress("Unused")
    constructor(code: Int, message: String?, cause: Throwable?) : this(
        HttpStatus.lookup(code),
        message,
        cause
    )

    /**
     * Constructs a new runtime exception with the specified [httpStatus], detail [message] and
     * [cause].
     */
    constructor(httpStatus: HttpStatus, message: String?, cause: Throwable?) : super(
        message,
        cause
    ) {
        this.httpStatus = httpStatus
    }

    /**
     * Constructs a new runtime exception using the specified HTTP status [code] and with the
     * specified [cause] and a detail message of (cause==null ? null : cause.toString()) (which
     * typically contains the class and detail message of cause).
     */
    @Suppress("Unused")
    constructor(
        code: Int,
        cause: Throwable?
    ) : this(HttpStatus.lookup(code), cause)

    /**
     * Constructs a new runtime exception with the specified [httpStatus] and [cause] and a detail
     * message of (cause==null ? null : cause.toString()) (which typically contains the class and
     * detail message of cause).
     */
    constructor(httpStatus: HttpStatus, cause: Throwable?) : super(cause) {
        this.httpStatus = httpStatus
    }

    /**
     * Constructs a new runtime exception using the specified HTTP status [code] and with the
     * specified detail [message], [cause], suppression enabled or disabled, and writable stack
     * trace enabled or disabled.
     */
    @Suppress("unused")
    constructor(
        code: Int,
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : this(HttpStatus.lookup(code), message, cause, enableSuppression, writableStackTrace)

    /**
     * Constructs a new runtime exception with the specified [httpStatus], detail [message],
     * [cause], suppression enabled or disabled, and writable stack trace enabled or disabled.
     */
    constructor(
        httpStatus: HttpStatus,
        message: String?,
        cause: Throwable?,
        enableSuppression: Boolean,
        writableStackTrace: Boolean
    ) : super(message, cause, enableSuppression, writableStackTrace) {
        this.httpStatus = httpStatus
    }

    // endregion Constructors

    // region Overridden methods

    override fun toString(): String {
        return "${javaClass.simpleName}(httpStatus=$httpStatus, message=$message)"
    }

    // endregion Overridden methods

    // region Companion object

    companion object {

        @JvmStatic
        fun of(
            code: Int,
            message: String? = HttpStatus.lookup(code).reasonPhrase
        ): HttpStatusException = HttpStatusException(
            httpStatus = HttpStatus.lookup(code),
            message = message
        )

    }

    // endregion Companion object

}