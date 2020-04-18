rootProject.name = "maw"

pluginManagement {
    val springBootVersion: String by settings
    val kotlinVersion: String by settings
    val flywayVersion: String by settings

    repositories {
        gradlePluginPortal()
        jcenter()
        mavenCentral()
    }

    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.springframework.boot" -> useModule("org.springframework.boot:spring-boot-gradle-plugin:$springBootVersion")
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.flywaydb.flyway" -> useVersion(flywayVersion)
            }
        }
    }
}
