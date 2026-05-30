plugins {
    id("android-library-convention")
    id("kotlin-kapt")
    id("com.google.devtools.ksp")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    api(libs.koin.android)
    api(libs.squareup.okhttp)
    api(libs.squareup.okhttp.logging.interceptor)
    api(libs.squareup.converter.moshi)
    api(libs.squareup.converter.scalars)
    api(libs.squareup.moshi.kotlin)
    api(libs.squareup.moshi.kotlin.adapters)
    ksp(libs.squareup.moshi.kotlin.codegen)

    coreLibraryDesugaring(libs.android.tools.desugar)

}

android {
    namespace = "com.riso.data"
}