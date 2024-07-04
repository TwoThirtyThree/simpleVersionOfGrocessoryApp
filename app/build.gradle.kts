//import org.apache.commons.io.function.Uncheck.test
//
//
//plugins {
//    alias(libs.plugins.android.application)
//    alias(libs.plugins.jetbrains.kotlin.android)
//    alias(libs.plugins.google.gms.google.services)
//
//}
//
//android {
//    namespace = "com.example.task1"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "com.example.task1"
//        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//    kotlinOptions {
//        jvmTarget = "1.8"
//    }
//
//
//    packaging {
//        resources.excludes.addAll(
//            listOf(
//                "META-INF/LICENSE.md",
//                "META-INF/LICENSE-notice.md",
//            )
//        )
//    }
//    buildFeatures {
//        viewBinding = true
//    }
//    sourceSets {
//        getByName("main") {
//            assets {
//                srcDirs("src\\main\\assets", "src\\main\\assets\\2",
//                    "src\\main\\assets",
//                    "src\\main\\assets\\2"
//                )
//
//            }
//        }
//    }
//    testOptions {
//       unitTests.isReturnDefaultValues = true
//       unitTests.isIncludeAndroidResources = true
//    }
//}
//
//
//
//dependencies {
//    testImplementation ("io.mockk:mockk:1.13.11")
//    testImplementation ("org.mockito:mockito-core:4.0.0")
////    testImplementation ("org.powermock:powermock-module-junit4:2.0.9")
////    testImplementation ("org.powermock:powermock-api-mockito2:2.0.9")
//
//
//
//
//
//
//    testImplementation ("androidx.test:core:1.4.0")
//    testImplementation ("androidx.test.ext:junit:1.1.3")
//    testImplementation ("androidx.test.ext:truth:1.4.0")
//    testImplementation ("androidx.test:runner:1.4.0")
//    testImplementation ("androidx.test:rules:1.4.0")
//    testImplementation ("androidx.arch.core:core-testing:2.1.0")
//
//
//    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.37")
//    implementation(libs.core.ktx)
//    implementation(libs.androidx.junit.ktx)
//
//    testImplementation ("junit:junit:4.13.2")
//    testImplementation ("org.mockito:mockito-core:3.9.0")
//
//    // Kotlin dependencies for test source set
//    testImplementation ("org.jetbrains.kotlin:kotlin-stdlib")
//
//    testImplementation ("org.mockito:mockito-inline:4.9.0")
//    testImplementation ("org.robolectric:robolectric:4.9")
//
//    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
//    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
//    implementation("com.google.firebase:firebase-bom:33.0.0")
//    implementation ("androidx.browser:browser:1.3.0")
//    implementation ("com.android.support:multidex:1.0.3")
//    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
//    implementation ("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
//    implementation ("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
//    implementation("com.google.firebase:firebase-auth")
//
//    implementation ("com.google.firebase:firebase-analytics:17.5.0")
//    implementation ("com.squareup.picasso:picasso:2.8")
//    implementation ("com.github.bumptech.glide:glide:4.12.0")
//    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
//
//    implementation ("androidx.browser:browser:1.2.0")
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.appcompat)
//    implementation(libs.material)
//    implementation(libs.androidx.activity)
//    implementation(libs.androidx.constraintlayout)
//    implementation(libs.firebase.auth)
//    implementation(libs.androidx.navigation.fragment.ktx)
//    implementation(libs.androidx.navigation.ui.ktx)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.androidx.junit)
//    androidTestImplementation(libs.androidx.espresso.core)
//
//
//
//
//}
//
//
//
//
//
plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.task1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.task1"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    packaging {
        resources.excludes.addAll(
            listOf(
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md",
            )
        )
    }
    buildFeatures {
        viewBinding = true
    }
    sourceSets {
        getByName("main") {
            assets {
                srcDirs("src/main/assets", "src/main/assets/2")
            }
        }
    }
    testOptions {
        unitTests.isReturnDefaultValues = true
        unitTests.isIncludeAndroidResources = true
    }
}

dependencies {
    // Mockito and Mockito-Kotlin for Kotlin-specific extensions

    testImplementation ("org.mockito.kotlin:mockito-kotlin:4.0.0")
    implementation("com.googlecode.libphonenumber:libphonenumber:8.13.37")
    implementation(libs.core.ktx)
    implementation(libs.androidx.junit.ktx)
    implementation("com.google.firebase:firebase-bom:33.0.0")
    implementation("androidx.browser:browser:1.3.0")
    implementation("com.android.support:multidex:1.0.3")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.4.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.0")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.firebase:firebase-analytics:17.5.0")
    implementation("com.squareup.picasso:picasso:2.8")
    implementation("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.12.0")
    implementation("androidx.browser:browser:1.2.0")
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation ("com.squareup.okhttp3:logging-interceptor:5.0.0-alpha.11")
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:4.0.0")
    testImplementation("io.mockk:mockk:1.13.11")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test.ext:truth:1.4.0")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test:rules:1.4.0")
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    testImplementation("org.mockito:mockito-inline:4.0.0")
    testImplementation("org.robolectric:robolectric:4.9")
    testImplementation("org.jetbrains.kotlin:kotlin-stdlib")
    testImplementation("org.mockito.kotlin:mockito-kotlin:4.0.0")
    androidTestImplementation("org.junit.jupiter:junit-jupiter:5.8.1")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.6.0")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

