package de.stefan.lang.coreutils.contract.logging

public interface Logging {
    public fun d(tag: String, message: String)
    public fun i(tag: String, message: String)
    public fun w(tag: String, message: String)
    public fun e(tag: String, message: String)
}
