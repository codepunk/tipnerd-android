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

plugins {
    // Supplied by New Project template
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    // Added by Codepunk
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.hilt)
    alias(libs.plugins.serialization)
    alias(libs.plugins.kotlin.parcelize)
}

android {
    namespace = "com.codepunk.tipnerd"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.codepunk.tipnerd"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "0.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField(
            type = "String",
            name = "APPLICATION_NAME",
            value = "\"Tipnerd\""
        )

        buildConfigField(
            type = "long",
            name = "OK_HTTP_CLIENT_CACHE_SIZE",
            value = "10 * 1024 * 1024"
        )

        buildConfigField(
            type = "String",
            name = "DATABASE_NAME",
            value = "\"tipnerd_db\""
        )

        buildConfigField(
            type = "String",
            name = "PREFERENCES_DATASTORE_NAME",
            value = "\"tipnerd_preferences\""
        )

        buildConfigField(
            type = "String",
            name = "USER_SETTINGS_DATASTORE_FILENAME",
            value = "\"tipnerd_user_settings.json\""
        )

        /*
         * Pull private values from gradle.properties
         * (See http://www.rainbowbreeze.it/environmental-variables-api-key-and-secret-buildconfig-and-android-studio/)
         * (Also see https://medium.com/@ericfu/securely-storing-secrets-in-an-android-application-501f030ae5a3
         *  for info about using KeyStore)
         */
        val tipnerdLocalClientIdProp = if (project.hasProperty("TipnerdLocalClientIdProp")) {
            "\"${project.property("TipnerdLocalClientIdProp")}\""
        } else {
            "/**** Define Tipnerd Local Client Id ****/ \"\""
        }
        buildConfigField(
            type = "String",
            name = "TIPNERD_LOCAL_CLIENT_ID",
            value = tipnerdLocalClientIdProp
        )

        val tipnerdLocalClientSecretProp = if (project.hasProperty("TipnerdLocalClientSecretProp")) {
            "\"${project.property("TipnerdLocalClientSecretProp")}\""
        } else {
            "/**** Define Tipnerd Local Secret Id ****/ \"\""
        }
        buildConfigField(
            type = "String",
            name = "TIPNERD_LOCAL_CLIENT_SECRET",
            value = tipnerdLocalClientSecretProp
        )

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    flavorDimensions += "version"

    productFlavors {
        create("production") {
            dimension = "version"
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"https://www.tipnerd.com/\""
            )
        }
        create("local") {
            dimension = "version"
            applicationIdSuffix = ".local"
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = "\"http://localhost/\""
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // --------------------------------------------------
    // Supplied by New Project template
    // --------------------------------------------------

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // --------------------------------------------------
    // Added by Codepunk
    // --------------------------------------------------

    // Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.navigation.compose)

    // Navigation
    implementation(libs.navigation.compose)

    // Material 3 Adaptive
    implementation(libs.material3.adaptive)
    implementation(libs.material3.adaptive.layout)
    implementation(libs.material3.adaptive.navigation)
    implementation(libs.adaptive.navigation.suite)

    // Splashscreen
    implementation(libs.splashscreen)

    // KotlinX
    implementation(libs.kotlinx.collections.immutable)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.converter)

    // Arrow
    implementation(libs.arrow.core)
    implementation(libs.arrow.core.retrofit)
    implementation(libs.arrow.fx.coroutines)

    // Okhttp / Retrofit
    implementation(libs.okhttp)
    implementation(libs.retrofit)

    // Room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.paging)

    // Accompanist
    implementation(libs.accompanist)

    // Datastore
    implementation(libs.datastore.preferences)
    implementation(libs.datastore)

    // Paging
    implementation(libs.paging)
    implementation(libs.paging.compose)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.networking)
    implementation(libs.coil.gif)

    // Credentials
    implementation(libs.credentials.play.services.auth)
    implementation(libs.credentials)

}
