package de.stefan.lang.shapebyte.utils.logging

// TODO: extract
interface Logging {
    fun d(tag: String, message: String)
    fun i(tag: String, message: String)
    fun w(tag: String, message: String)
    fun e(tag: String, message: String)
}