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

package com.codepunk.tipnerd.ui.compose.preview

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview

private const val PIXEL_8 = "id:pixel_8_pro"
private const val PIXEL_8_PRO_LANDSCAPE = "spec:width=448dp,height=998dp,dpi=420," +
        "isRound=false,chinSize=0dp,orientation=landscape,cutout=none,navigation=gesture"
private const val PIXEL_5_LANDSCAPE = "spec:width=393dp,height=851dp,dpi=440," +
        "isRound=false,chinSize=0dp,orientation=landscape,cutout=none,navigation=gesture"
private const val PIXEL_TABLET_PORTRAIT = "spec:width=800dp,height=1200dp,dpi=276," +
        "isRound=false,chinSize=0dp,orientation=portrait,cutout=none,navigation=gesture"

// Pixel 8 Pro

@Preview(
    name = "Pixel 8 Pro",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_8,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 8 Pro Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_8_PRO_LANDSCAPE,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 8 Pro Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_8,
    group = "Dark Mode"
)
@Preview(
    name = "Pixel 8 Pro Landscape Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_8_PRO_LANDSCAPE,
    group = "Dark Mode"
)

// Pixel 5

@Preview(
    name = "Pixel 5",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_5,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 5 Landscape",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_5_LANDSCAPE,
    group = "Light Mode"
)
@Preview(
    name = "Pixel 5 Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_5,
    group = "Dark Mode"
)
@Preview(
    name = "Pixel 5 Landscape Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_5_LANDSCAPE,
    group = "Dark Mode"
)

// Pixel Tablet

@Preview(
    name = "Pixel Tablet Portrait",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = PIXEL_TABLET_PORTRAIT,
    group = "Light Mode"
)
@Preview(
    name = "Pixel Tablet",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    device = Devices.PIXEL_TABLET,
    group = "Light Mode"
)
@Preview(
    name = "Pixel Tablet Portrait Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = PIXEL_TABLET_PORTRAIT,
    group = "Dark Mode"
)
@Preview(
    name = "Pixel Tablet Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = Devices.PIXEL_TABLET,
    group = "Dark Mode"
)
annotation class ScreenPreviews
