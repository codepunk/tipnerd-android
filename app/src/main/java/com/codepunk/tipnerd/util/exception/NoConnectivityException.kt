package com.codepunk.tipnerd.util.exception

import java.io.IOException

class NoConnectivityException @JvmOverloads constructor(
    message: String? = null,
    cause: Throwable? = null
) : IOException(message, cause)
