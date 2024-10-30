package com.codepunk.tipnerd.data

import arrow.core.Either
import arrow.core.Ior
import arrow.core.rightIor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow

// region Methods

inline fun <Remote, Domain, Error> networkDataResource(
    crossinline fetch: suspend () -> Either<Error, Remote>,
    crossinline onFetchFailed: (Error) -> Unit = {},
    crossinline transform: (Remote) -> Domain
): Flow<Either<Error, Domain>> = flow {
    emit(
        fetch().onLeft {
            onFetchFailed(it)
        }.map {
            transform(it)
        }
    )
}

inline fun <Remote, Domain, Error> cachedDataResource(
    crossinline query: suspend () -> Flow<Domain>,
    crossinline fetch: suspend () -> Either<Error, Remote>,
    crossinline onFetchFailed: (Error) -> Unit = {},
    crossinline shouldFetch: (Domain?) -> Boolean = { true },
    crossinline shouldEmitCachedWhileFetching: () -> Boolean = { true },
    crossinline saveFetchResult: suspend (Remote) -> Unit = {}
): Flow<Ior<Error, Domain>> = flow {
    val cached = query().first()
    if (shouldFetch(cached)) {
        if (shouldEmitCachedWhileFetching()) {
            emit(cached.rightIor())
        }
        emit(
            fetch().onLeft {
                onFetchFailed(it)
            }.onRight {
                saveFetchResult(it)
            }.map {
                query().first()
            }.fold(
                ifLeft = { Ior.Both(it, cached) },
                ifRight = { it.rightIor() }
            )
        )
    } else {
        emit(cached.rightIor())
    }
}

// endregion Methods
