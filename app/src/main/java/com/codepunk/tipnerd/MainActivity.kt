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

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.codepunk.tipnerd.manager.UserSessionManager
import com.codepunk.tipnerd.ui.compose.Navigation
import com.codepunk.tipnerd.ui.compose.Route
import com.codepunk.tipnerd.ui.compose.screen.splash.SplashViewModel
import com.codepunk.tipnerd.ui.theme.TipnerdTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    // region Variables

    @Inject
    lateinit var userSessionManager: UserSessionManager

    private val splashViewModel: SplashViewModel by viewModels()

    // endregion Variables

    // Region Methods

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { splashViewModel.isLoading.value }

        enableEdgeToEdge()
        setContent {
            TipnerdTheme {
                val authenticated = false // TODO userSession.value is Authenticated
                Navigation(
                    startDestination = if (authenticated) {
                        Route.Main
                    } else {
                        Route.Auth
                    }
                )
            }
        }
    }

    // endregion Methods

}
