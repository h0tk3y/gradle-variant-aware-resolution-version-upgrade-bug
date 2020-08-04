plugins {
    java
}

dependencies {
    // transitively depends on kotlinx-coroutines-core version 1.3.7, which is a JVM-only module
    implementation(kotlin("scripting-common", "1.4.0-rc")) 

    // Uncomment this to fix the issue and ensure that `kotlinx-coroutines-core-jvm` JAR is on the classpath
    // implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.8-1.4.0-rc")
}

configurations.all { 
    resolutionStrategy {
        eachDependency {
            if (requested.group == "org.jetbrains.kotlinx" && 
                requested.name == "kotlinx-coroutines-core"
            ) {
                // Upgrade the dependency to the version where it contains Gradle module metadata which
                // points to the JVM module using 'available-at'
                useVersion("1.3.8-1.4.0-rc") // Commenting this returns the 1.3.7 version JAR to the classpath
            }
        }
    }
}

tasks.register("checkClasspath") {
    doFirst {
        val compileClasspath by configurations.getting
        val classpathFiles = compileClasspath.toList()
        println("compileClasspath files:\n${classpathFiles.joinToString("\n")}\n")
        println("compileClasspath resolution result:\n${compileClasspath.incoming.resolutionResult.allComponents.joinToString("\n")}")
        check(classpathFiles.any { "kotlinx-coroutines-core" in it.path })
    }
}