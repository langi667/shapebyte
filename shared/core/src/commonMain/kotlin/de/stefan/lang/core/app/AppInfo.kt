package de.stefan.lang.core.app

data class AppInfo(
    val packageName: String,
    val versionName: String,
    val versionCode: Int,
    var debugMode: Boolean,
)
