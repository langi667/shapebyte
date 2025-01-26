package de.stefan.lang.shapebyte.utils.app.appinfo

data class AppInfo(
    val packageName: String,
    val versionName: String,
    val versionCode: Int,
    var debugMode: Boolean,
)
