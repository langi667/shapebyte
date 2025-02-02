package de.stefan.lang.core.device.safearea
import de.stefan.lang.core.logging.Loggable
import de.stefan.lang.core.logging.Logging
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.useContents
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import platform.UIKit.UIApplication
import platform.UIKit.UIScene
import platform.UIKit.UISceneActivationStateForegroundActive
import platform.UIKit.UIWindow
import platform.UIKit.UIWindowScene

@OptIn(ExperimentalForeignApi::class)
actual class SafeAreaDetector actual constructor(logger: Logging) : Loggable {
    actual override val logger: Logging = logger

    actual fun detectSafeArea(): Flow<SafeArea> = flow {
        repeat(10) {
            readSafeAreaSize()?.let { safeArea ->
                emit(safeArea)
                return@flow
            }

            delay(200)
        }

        logW("SafeArea could not be detected, emitting empty SafeArea")
        emit(SafeArea())
    }

    private fun readSafeAreaSize(): SafeArea? {
        var safeArea: SafeArea? = null
        try {
            val insets = UIApplication
                .sharedApplication
                .connectedScenes
                .filter {
                    val scene = it as? UIScene
                    scene?.activationState == UISceneActivationStateForegroundActive
                }
                .firstNotNullOf { it as? UIWindowScene }
                .windows
                .mapNotNull { it as? UIWindow }
                .first { it.isKeyWindow() }.safeAreaInsets

            safeArea = insets.useContents {
                SafeArea(
                    top = top,
                    leading = left,
                    bottom = bottom,
                    trailing = right
                )
            }

        } finally {
            logD("SafeArea: $safeArea")
            return safeArea
        }
    }
}