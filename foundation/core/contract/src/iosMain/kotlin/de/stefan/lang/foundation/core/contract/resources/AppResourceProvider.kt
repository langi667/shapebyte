package de.stefan.lang.foundation.core.contract.resources

import platform.Foundation.NSBundle

actual class AppResourceProvider actual constructor() {
    val mainBundle: NSBundle = NSBundle.mainBundle
}