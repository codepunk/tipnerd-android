package com.codepunk.tipnerd.data.remote.interceptor

import com.codepunk.tipnerd.manager.UserSession
import com.codepunk.tipnerd.manager.UserSessionManager
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(
    private val userSessionManager: UserSessionManager
) : Interceptor {

    // TODO Maybe only apply this in the authenticated (user) repository
    //   and then I can remove the "if" statement below

    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = chain.request()
            .newBuilder()
            .apply {
                val userSession = userSessionManager.userSession.value
                if (userSession is UserSession.Authenticated) {
                    addHeader(AUTHORIZATION, userSession.oauthToken.accessToken)
                }
            }
            .build()
        return chain.proceed(newRequest)
    }

    companion object {
        private const val AUTHORIZATION: String = "Authorization"
    }
}