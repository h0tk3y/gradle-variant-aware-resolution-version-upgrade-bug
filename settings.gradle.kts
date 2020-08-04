pluginManagement {
    fun RepositoryHandler.setup() {
        jcenter()
        maven("https://kotlin.bintray.com/kotlinx")
        if (this == pluginManagement.repositories) {
            gradlePluginPortal()
        }
    }
    repositories.setup()
    gradle.allprojects { repositories.setup() }
}