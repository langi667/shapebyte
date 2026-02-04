package de.stefan.lang.foundation.core.contract.stringformatter

import kotlinx.datetime.Instant

public interface DateTimeStringFormatter {
    public fun formatDateToDDMMYYYY(date: Instant): String
    public fun formatSecondsToString(seconds: Int): String
}
