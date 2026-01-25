package de.stefan.lang.foundation.core.contract.stringformatter

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateTimeStringFormatter {
    companion object {
        const val SECONDS_PER_MINUTE = 60
    }
    fun formatDateToDDMMYYYY(date: Instant): String {
        val timeZone = TimeZone.currentSystemDefault()
        val localDate = date.toLocalDateTime(timeZone).date

        val day = localDate.dayOfMonth.toString().padStart(2, '0')
        val month = localDate.monthNumber.toString().padStart(2, '0')
        val year = localDate.year.toString().padStart(2, '0')

        return "$day.$month.$year"
    }

    fun formatSecondsToString(seconds: Int): String {
        val minutes = seconds / SECONDS_PER_MINUTE
        val remainingSeconds = seconds % SECONDS_PER_MINUTE

        return "$minutes".padStart(2, '0') + ":" + "$remainingSeconds".padStart(2, '0')
    }
}
