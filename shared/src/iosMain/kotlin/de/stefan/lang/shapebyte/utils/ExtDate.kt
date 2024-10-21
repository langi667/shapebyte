package de.stefan.lang.shapebyte.utils

import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

fun NSDate.toInstant(): Instant = Instant.fromEpochSeconds(this.timeIntervalSince1970.toLong(), 0)
