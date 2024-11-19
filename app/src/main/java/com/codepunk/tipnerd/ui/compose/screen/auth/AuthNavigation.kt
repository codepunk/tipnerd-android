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

package com.codepunk.tipnerd.ui.compose.screen.auth

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
    state: AuthState,
    onEvent: (AuthEvent) -> Unit = {}
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = AuthRoute.AuthOptions
    ) {
        composable<AuthRoute.AuthOptions> {
            AuthOptionsScreen { event ->
                // Consume navigation events here as appropriate,
                // everything else gets passed up the chain
                when (event) {
                    is AuthEvent.NavigateToRegister ->
                        navController.navigate(AuthRoute.AuthRegister)
                    is AuthEvent.NavigateToLogin ->
                        navController.navigate(AuthRoute.AuthLogin)
                    else -> onEvent(event)
                }
            }
        }

        composable<AuthRoute.AuthRegister> {
            AuthRegisterScreen(
                state = state
            ) { event ->
                // Consume navigation events here as appropriate,
                // everything else gets passed up the chain
                when (event) {
                    is AuthEvent.NavigateUp -> navController.navigateUp()
                    else -> onEvent(event)
                }
            }
        }

        composable<AuthRoute.AuthLogin> {
            AuthSignInScreen(
                state = state
            ) { event ->
                // Consume navigation events here as appropriate,
                // everything else gets passed up the chain
                when (event) {
                    is AuthEvent.NavigateUp -> navController.navigateUp()
                    else -> onEvent(event)
                }
            }
        }
    }
}
