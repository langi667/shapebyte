package de.stefan.lang.shapebyte.utils.app.appresources

import platform.Foundation.NSBundle

actual class AppResourceProvider actual constructor() {
    val mainBundle: NSBundle = NSBundle.mainBundle
}