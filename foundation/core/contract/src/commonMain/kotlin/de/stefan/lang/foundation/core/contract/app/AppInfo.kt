package de.stefan.lang.foundation.core.contract.app

data class AppInfo(
    val packageName: String,
    val versionName: String,
    val versionCode: Int,
    var debugMode: Boolean,
)
