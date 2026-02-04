package de.stefan.lang.foundation.core.contract.resources

import platform.Foundation.NSBundle

public actual class AppResourceProvider public actual constructor() {
    public val mainBundle: NSBundle = NSBundle.mainBundle
}
