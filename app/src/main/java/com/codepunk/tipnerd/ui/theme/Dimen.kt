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

package com.codepunk.tipnerd.ui.theme

import android.content.res.Resources
import androidx.compose.runtime.Immutable
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Suppress("unused")
@Immutable
class FixedSizeScheme(
    val borderHairline: Dp,
    val border: Dp,
    val borderThick: Dp,
    val paddingSmall: Dp,
    val padding: Dp,
    val paddingLarge: Dp,
    val paddingXLarge: Dp,
    val padding2xLarge: Dp,
    val component2xSmall: Dp,
    val componentXSmall: Dp,
    val componentSmall: Dp,
    val component: Dp,
    val componentLarge: Dp,
    val componentXLarge: Dp,
    val component2xLarge: Dp,
    val region2xSmall: Dp,
    val regionXSmall: Dp,
    val regionSmall: Dp,
    val region: Dp,
    val regionLarge: Dp,
    val regionXLarge: Dp,
    val region2xLarge: Dp,
    val region3xLarge: Dp
)

val borderHairline: Dp = (1 / Resources.getSystem().displayMetrics.density).dp
val border: Dp = 1.dp
val borderThick: Dp = 2.dp
val paddingSmall: Dp = 4.dp
val padding: Dp = 8.dp
val paddingLarge: Dp = 16.dp
val paddingXLarge: Dp = 24.dp
val padding2xLarge: Dp = 32.dp
val component2xSmall: Dp = 16.dp
val componentXSmall: Dp = 24.dp
val componentSmall: Dp = 40.dp
val component: Dp = 48.dp
val componentLarge: Dp = 56.dp
val componentXLarge: Dp = 64.dp
val component2xLarge: Dp = 96.dp
val region2xSmall: Dp = 96.dp
val regionXSmall: Dp = 128.dp
val regionSmall: Dp = 160.dp
val region: Dp = 192.dp
val regionLarge: Dp = 224.dp
val regionXLarge: Dp = 256.dp
val region2xLarge: Dp = 384.dp
val region3xLarge: Dp = 512.dp
