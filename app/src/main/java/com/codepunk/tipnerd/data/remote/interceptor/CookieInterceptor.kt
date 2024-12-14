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

package com.codepunk.tipnerd.data.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Response
import java.net.CookieManager
import java.net.HttpCookie
import javax.inject.Inject

/**
 * TODO:
 * 1. Can this logic exist in a CookieJar set in OkHttpClient?
 * 2. Currently setting cookies by url/uri, but getting ALL cookies in cookie store.
 *    Is this ok/correct?
 */
class CookieInterceptor @Inject constructor(
    private val cookieManager: CookieManager
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        // Get stored cookies
        val cookieString = cookieManager.cookieStore.cookies.joinToString(
            "; "
        ) { "${it.name}=${it.value}" }

        // Add stored cookies to request
        val newRequest = chain.request().newBuilder()
            .header("Cookie", cookieString)
            .build()

        return chain.proceed(newRequest).apply {
            // Store new cookies from response
            headers.filter { it.first == "Set-Cookie" }.forEach { (_, header) ->
                HttpCookie.parse(header).forEach {
                    cookieManager.cookieStore.add(request.url.toUri(), it)
                }
            }
        }
    }
}
