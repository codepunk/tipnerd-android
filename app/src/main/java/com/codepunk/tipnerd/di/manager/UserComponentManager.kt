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

// See https://medium.com/androiddevelopers/hilt-adding-components-to-the-hierarchy-96f207d6d92d
// See https://medium.com/@myungpyo/using-hilt-custom-component-c8227d89aed6

package com.codepunk.tipnerd.di.manager

import android.util.Log
import com.codepunk.tipnerd.di.component.UserComponent
import com.codepunk.tipnerd.di.qualifier.ApplicationScope
import com.codepunk.tipnerd.di.qualifier.IoDispatcher
import com.codepunk.tipnerd.manager.UserSession
import com.codepunk.tipnerd.manager.UserSession.Authenticated
import com.codepunk.tipnerd.manager.UserSession.Unauthenticated
import com.codepunk.tipnerd.manager.UserSessionManager
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

@Singleton
class UserComponentManager @Inject constructor(
    @ApplicationScope applicationScope: CoroutineScope,
    @IoDispatcher ioDispatcher: CoroutineDispatcher,
    private val userSessionManager: UserSessionManager,
    private val userComponentProvider: Provider<UserComponent.Builder>
) {

    // region Variables

    /**
     *  UserComponent is specific to a logged in user. Holds an instance of
     *  UserComponent. This determines if the user is logged in or not, when the
     *  user logs in, a new Component will be created.
     *  When the user logs out, this will be null.
     */
    var userComponent: UserComponent? = null
        private set

    private var currentUserSession: UserSession = userSessionManager.userSession.value

    // endregion Variables

    // region Constructors

    init {
        Log.d(javaClass.simpleName, "init")
        applicationScope.launch(ioDispatcher) {
            userSessionManager.userSession.collect { userSession ->
                when (userSession) {
                    currentUserSession -> return@collect
                    Unauthenticated -> userComponent = null
                    is Authenticated -> userComponent = userComponentProvider.get()
                        .setUserSession(userSession)
                        .build()
                }
                currentUserSession = userSession
            }
        }
    }

    // endregion Constructors

}
