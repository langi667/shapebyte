package de.stefan.lang.coreutils.contract.date

import kotlinx.datetime.Instant
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

public fun NSDate.toInstant(): Instant = Instant.fromEpochSeconds(this.timeIntervalSince1970.toLong(), 0)
