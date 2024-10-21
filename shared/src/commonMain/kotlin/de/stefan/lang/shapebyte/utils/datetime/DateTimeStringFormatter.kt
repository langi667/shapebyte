package de.stefan.lang.shapebyte.utils.datetime

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

class DateTimeStringFormatter {
    fun formatDateToDDMMYYYY(date: Instant): String {
        val timeZone = TimeZone.currentSystemDefault()
        val localDate = date.toLocalDateTime(timeZone).date

        val day = localDate.dayOfMonth.toString().padStart(2, '0')
        val month = localDate.monthNumber.toString().padStart(2, '0')
        val year = localDate.year.toString().padStart(2, '0')

        return "$day.$month.$year"
    }
}
