package de.stefan.lang.foundation.core.contract.app

public data class AppInfo public constructor(
    public val packageName: String,
    public val versionName: String,
    public val versionCode: Int,
    public var debugMode: Boolean,
)
