package de.stefan.lang.core.logging

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

interface Logging {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
    fun e(tag: String, message: String)
}
