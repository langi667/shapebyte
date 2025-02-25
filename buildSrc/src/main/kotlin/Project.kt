import org.gradle.api.JavaVersion

// TODO: add SDK versions ...
object Project {
    object Android {
        object BuildSettings {
            val javaVersion = JavaVersion.VERSION_21
            val minSdk = 24
            val targetSdk = 34
            val excludedResourcesList = listOf(
                "META-INF/*",
                "META-INF/*.*",
            )
        }
    }
}