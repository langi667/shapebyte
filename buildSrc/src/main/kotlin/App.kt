import org.gradle.api.JavaVersion

// TODO: add SDK versions ...
object App {
    object Android {
        object BuildSettings {
            val javaVersion = JavaVersion.VERSION_21
            val excludedResourcesList = listOf(
                "META-INF/*",
                "META-INF/*.*",
            )
        }
    }
}