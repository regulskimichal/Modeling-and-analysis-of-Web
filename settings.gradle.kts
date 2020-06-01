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
                "io.spring.dependency-management" -> useModule("io.spring.gradle:dependency-management-plugin:1.0.9.RELEASE")
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.spring" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.jpa" -> useVersion(kotlinVersion)
                "org.hibernate.orm" -> useModule("org.hibernate:hibernate-gradle-plugin:5.4.15.Final")
            }
        }
    }
}
