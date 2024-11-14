package com.codepunk.tipnerd.util.http

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