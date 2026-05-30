plugins {
    id("android-library-convention")
    id("kotlin-parcelize")
}

dependencies {
    implementation(project(":core"))
    implementation(project(":imageLoader"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    coreLibraryDesugaring(libs.android.tools.desugar)

}

android {
    namespace = "com.riso.domain"
}