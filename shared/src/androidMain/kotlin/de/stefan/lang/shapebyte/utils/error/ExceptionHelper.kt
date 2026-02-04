package de.stefan.lang.shapebyte.utils.error

// TODO: inject debug info
// TODO: test

public fun <T> throwIfDebug(
    isDebug: Boolean,
    exception: Throwable,
    fallback: T,
): T {
    if (isDebug) {
        throw exception
    } else {
        return fallback
    }
}
