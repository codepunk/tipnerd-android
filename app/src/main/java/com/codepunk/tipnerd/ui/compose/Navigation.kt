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

package com.codepunk.tipnerd.ui.compose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.codepunk.tipnerd.ui.compose.screen.auth.AuthScreen
import com.codepunk.tipnerd.ui.compose.screen.auth.AuthViewModel
import com.codepunk.tipnerd.ui.compose.screen.main.MainScreen
import com.codepunk.tipnerd.ui.compose.screen.main.MainViewModel

@Composable
fun Navigation(
    modifier: Modifier = Modifier,
    startDestination: Route = Route.Main
) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable<Route.Auth> {
            val viewModel: AuthViewModel = hiltViewModel()
            val state = viewModel.stateFlow.collectAsState()
            AuthScreen(
                modifier = modifier,
                state = state.value
            ) { event ->
                // Consume navigation events here as appropriate,
                // everything else falls through to AuthViewModel
                /*
                when (event) {
                    is AuthEvent.DummyEvent -> navController.navigate(Route.Main) {
                        popUpTo(navController.graph.id) {
                            saveState = true
                        }
                    }
                    else -> viewModel.onEvent(event)
                }
                 */
            }
        }

        composable<Route.Main> {
            val viewModel: MainViewModel = hiltViewModel()
            val state = viewModel.stateFlow.collectAsState()
            MainScreen(
                modifier = modifier,
                state = state.value
            ) { event ->
                // Consume navigation events here as appropriate,
                // everything else falls through to LandingViewModel
                viewModel.onEvent(event)
            }
        }
    }
}