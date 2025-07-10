package de.stefan.lang.foundationCore.api.resources

import platform.Foundation.NSBundle

actual class AppResourceProvider actual constructor() {
    val mainBundle: NSBundle = NSBundle.mainBundle
}