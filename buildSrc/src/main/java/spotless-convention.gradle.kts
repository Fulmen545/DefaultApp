import com.diffplug.gradle.spotless.SpotlessExtension

plugins {
    id("com.diffplug.spotless")
}

configure<SpotlessExtension> {

    kotlin {
        // by default the target is every '.kt' and '.kts` file in the java sourcesets
        target("**/*.kt")
        targetExclude("**/test/**") // exclude formatting in tests
        ktlint().userData(getRules())
    }

    kotlinGradle {
        target("*.gradle.kts") // default target for kotlinGradle
        ktlint().userData(getRules())
    }
}

tasks.register("analyzeAndFixCode") {
    dependsOn("spotlessApply") // format the code
    doLast {
        tasks.getByName("spotlessCheck") // after the formatting, run a check to verify that everything is fixed
    }
}

fun getRules() = mapOf(
    "end_of_line" to "lf",
    "insert_final_newline" to "false",
    "trim_trailing_whitespace" to "true",
    "charset" to "utf-8",
    "indent_style" to "space",
    "indent_size" to "4",
    "disabled_rules" to "no-wildcard-imports"
)
