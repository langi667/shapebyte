package de.stefan.lang.core.resources.impl

import platform.Foundation.NSBundle

actual class AppResourceProvider actual constructor() {
    val mainBundle: NSBundle = NSBundle.mainBundle
}