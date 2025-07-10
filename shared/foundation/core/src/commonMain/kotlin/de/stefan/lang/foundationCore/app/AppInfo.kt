package de.stefan.lang.foundationCore.app

data class AppInfo(
    val packageName: String,
    val versionName: String,
    val versionCode: Int,
    var debugMode: Boolean,
)
