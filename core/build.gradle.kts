plugins {
    id("android-library-convention")
}

dependencies {

    implementation(libs.kotlin.coroutines.core)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    coreLibraryDesugaring(libs.android.tools.desugar)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

}

android {
    namespace = "com.riso.core"
}
