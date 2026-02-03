package de.stefan.lang.foundation.core.contract.stringformatter

import kotlinx.datetime.Instant

interface DateTimeStringFormatter {
    fun formatDateToDDMMYYYY(date: Instant): String
    fun formatSecondsToString(seconds: Int): String
}
