package de.stefan.lang.foundationCore.resources.impl

import platform.Foundation.NSBundle

actual class AppResourceProvider actual constructor() {
    val mainBundle: NSBundle = NSBundle.mainBundle
}