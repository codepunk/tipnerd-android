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

package com.codepunk.tipnerd.identity

import android.accounts.AbstractAccountAuthenticator
import android.accounts.Account
import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.codepunk.tipnerd.BuildConfig
import com.codepunk.tipnerd.domain.model.AuthTokenType
import com.codepunk.tipnerd.domain.repository.TipnerdRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [AbstractAccountAuthenticator] that authenticates Tipnerd accounts.
 */
@Singleton
class AccountAuthenticator @Inject constructor(

    /**
     * The application [Context] associated with this account authenticator.
     */
    @ApplicationContext private val context: Context,

    /**
     * The Android [AccountManager].
     */
    private val accountManager: AccountManager,

    /**
     * The Tipnerd webservice.
     */
    private val tipnerdRepository: TipnerdRepository

) : AbstractAccountAuthenticator(context) {

    /**
     * Adds an account of the specified accountType.
     */
    override fun addAccount(
        response: AccountAuthenticatorResponse,
        accountType: String,
        authTokenType: String,
        requiredFeatures: Array<out String>,
        options: Bundle
    ): Bundle = Bundle().apply {
        putParcelable(
            BuildConfig.KEY_INTENT,
            Intent(BuildConfig.ACTION_AUTHENTICATION).apply {
                addCategory(BuildConfig.CATEGORY_REGISTER)
                putExtra(
                    BuildConfig.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
                    response
                )
            }
        )
    }

    /**
     * Checks that the user knows the credentials of an account.
     * TODO: Incomplete
     */
    override fun confirmCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        options: Bundle
    ): Bundle = Bundle()

    /**
     * Returns a [Bundle] that contains the [Intent] of the activity that can be used to edit the
     * properties. In order to indicate success the activity should call
     * [AccountAuthenticatorResponse.onResult] with a non-null Bundle.
     * TODO: Incomplete
     */
    override fun editProperties(
        response: AccountAuthenticatorResponse,
        accountType: String
    ): Bundle = Bundle()

    /**
     * Gets an auth token for an account. If not null, the resultant [Bundle] will contain
     * different sets of keys depending on whether a token was successfully issued and, if not,
     * whether one could be issued via some Activity.
     */
    override fun getAuthToken(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle = Bundle().apply {
        var authTokenString = accountManager.peekAuthToken(
            account,
            AuthTokenType.DEFAULT.value
        )

        // TODO Check when auth token expires?

        // TODO Is this common? Or should I add refresh token as user data?
        var refreshToken = accountManager.getPassword(account)

        if (authTokenString.isEmpty() && refreshToken.isNotEmpty()) {
            try {
                // TODO Is runBlocking ok?
                runBlocking {
                    tipnerdRepository.refreshToken(refreshToken).first()
                }.onLeft {
                    putInt(
                        AccountManager.KEY_ERROR_CODE,
                        AccountManager.ERROR_CODE_INVALID_RESPONSE
                    )
                    putString(
                        AccountManager.KEY_ERROR_MESSAGE,
                        "Unable to refresh the token"
                    )
                }.onRight { oauthToken ->
                    authTokenString = oauthToken.accessToken
                    refreshToken = oauthToken.refreshToken
                    accountManager.setAuthToken(
                        account,
                        AuthTokenType.DEFAULT.value,
                        authTokenString
                    )
                    accountManager.setPassword(account, refreshToken)
                }
            } catch (e: Exception) {
                putInt(
                    AccountManager.KEY_ERROR_CODE,
                    AccountManager.ERROR_CODE_NETWORK_ERROR
                )
                putString(AccountManager.KEY_ERROR_MESSAGE, e.message)
            }
        }
    }

    /**
     * Ask the authenticator for a localized label for the given authTokenType.
     */
    override fun getAuthTokenLabel(authTokenType: String): String =
        AuthTokenType.get(authTokenType).getDescription(context)

    /**
     * Checks if the account supports all the specified authenticator specific features.
     * TODO: Incomplete
     */
    override fun hasFeatures(
        response: AccountAuthenticatorResponse,
        account: Account,
        features: Array<out String>
    ): Bundle = Bundle()

    /**
     * Update the locally stored credentials for an account.
     * TODO: Incomplete
     */
    override fun updateCredentials(
        response: AccountAuthenticatorResponse,
        account: Account,
        authTokenType: String,
        options: Bundle
    ): Bundle = Bundle()
}
