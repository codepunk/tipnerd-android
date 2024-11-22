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

package com.codepunk.tipnerd

import android.accounts.AccountAuthenticatorResponse
import android.accounts.AccountManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.core.content.IntentCompat

/**
 * Helper class that adds the functionality found in AccountAuthenticatorActivity to
 * [ComponentActivity].
 *
 * AccountAuthenticatorAppCompatActivity is a base class for implementing an Activity that is
 * used to help implement an AbstractAccountAuthenticator. If the AbstractAccountAuthenticator
 * needs to use an activity to handle the request then it can have the activity extend
 * AccountAuthenticatorAppCompatActivity. The AbstractAccountAuthenticator passes in the response
 * to the intent using the following:
 *
 *     intent.putExtra({@link AccountManager#KEY_ACCOUNT_AUTHENTICATOR_RESPONSE}, response);
 *
 * The activity then sets the result that is to be handed to the response via
 * [accountAuthenticatorResult].
 * This result will be sent as the result of the request when the activity finishes. If this
 * is never set or if it is set to null then error [AccountManager.ERROR_CODE_CANCELED]
 * will be called on the response.
 */
open class AccountAuthenticatorComponentActivity : ComponentActivity() {

    // region Properties

    private var accountAuthenticatorResponse: AccountAuthenticatorResponse? = null

    /**
     * The result that is to be sent as the result of the request that caused this
     * Activity to be launched. If result is null or this method is never called then
     * the request will be canceled.
     */
    @Suppress("MemberVisibilityCanBePrivate")
    var accountAuthenticatorResult: Bundle? = null
        protected set

    // endregion Properties

    // region Lifecycle methods

    /**
     * Retrieves the [AccountAuthenticatorResponse] from the intent.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        accountAuthenticatorResponse =
            IntentCompat.getParcelableExtra(
                intent,
                AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE,
                AccountAuthenticatorResponse::class.java
            )
        accountAuthenticatorResponse?.onRequestContinued()
    }

    // endregion Lifecycle methods

    // region Methods

    /**
     * Sends the result or an [AccountManager.ERROR_CODE_CANCELED] error if a result
     * isn't present.
     */
    override fun finish() {
        accountAuthenticatorResponse?.apply {
            // send the result bundle back if set, otherwise send an error.
            when (accountAuthenticatorResult) {
                null -> onError(AccountManager.ERROR_CODE_CANCELED, "Canceled")
                else -> onResult(accountAuthenticatorResult)
            }
            accountAuthenticatorResponse = null
        }
        super.finish()
    }

    // endregion Methods

}