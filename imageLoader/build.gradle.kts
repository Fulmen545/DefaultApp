plugins {
    id("android-library-convention")
    id("kotlin-android")
}

dependencies {

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.kotlin.coroutines.android)
    implementation(libs.squareup.okhttp)
    implementation(libs.koin.android)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    coreLibraryDesugaring(libs.android.tools.desugar)

}

android {
    namespace = "com.riso.imageloader"
}