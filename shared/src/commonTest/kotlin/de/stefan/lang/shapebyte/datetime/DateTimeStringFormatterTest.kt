package de.stefan.lang.shapebyte.datetime

import de.stefan.lang.shapebyte.utils.BaseTest
import de.stefan.lang.shapebyte.utils.datetime.DateTimeStringFormatter
import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals

class DateTimeStringFormatterTest : BaseTest() {

    private val sut = DateTimeStringFormatter()

    @Test
    fun `formatDateToDDMMYYYY should return formatted date`() {
        var date = Instant.parse("2021-01-01T00:00:00Z")
        var result = sut.formatDateToDDMMYYYY(date)
        assertEquals("01.01.2021", result)

        date = Instant.parse("2023-12-24T11:05:00Z")
        result = sut.formatDateToDDMMYYYY(date)
        assertEquals("24.12.2023", result)
    }

    @Test
    fun `formatSecondsToString should return formatted seconds`() {
        var result = sut.formatSecondsToString(30)
        assertEquals("00:30", result)

        result = sut.formatSecondsToString(75)
        assertEquals("01:15", result)

        result = sut.formatSecondsToString(630)
        assertEquals("10:30", result)

        result = sut.formatSecondsToString(660)
        assertEquals("11:00", result)
        result = sut.formatSecondsToString(6015)
        assertEquals("100:15", result)
    }
}
