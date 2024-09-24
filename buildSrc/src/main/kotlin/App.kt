import org.gradle.api.JavaVersion

object App {
    object Android {
        object BuildSettings {
            val javaVersion = JavaVersion.VERSION_11

            val excludedResourcesList = listOf(
                "META-INF/*.kotlin_module",
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/versions/9/previous-compilation-data.bin"
            )
        }
    }
}