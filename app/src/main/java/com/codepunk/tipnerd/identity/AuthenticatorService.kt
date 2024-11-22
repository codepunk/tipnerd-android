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

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A [Service] that handles account authentication using [AccountAuthenticator].
 */
@AndroidEntryPoint
class AuthenticatorService : Service() {

    // region Properties

    /**
     * The [AccountAuthenticator] that handles account authentication and management.
     */
    @Inject
    lateinit var accountAuthenticator: AccountAuthenticator

    // endregion Properties

    init {
        Log.d(javaClass.simpleName, "init!")
    }

    // region Lifecycle methods

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(javaClass.simpleName, "onStartCommand!")
        return super.onStartCommand(intent, flags, startId)
    }

    // endregion Lifecycle methods

    // region Methods

    /**
     * Binds this service to the [AccountAuthenticator].
     */
    override fun onBind(intent: Intent): IBinder {
        return accountAuthenticator.iBinder
    }

    // endregion Methods

}
