package de.stefan.lang.shapebyte.utils

import de.stefan.lang.shapebyte.utils.logging.Loggable
import de.stefan.lang.shapebyte.utils.logging.Logging
import de.stefan.lang.shapebyte.utils.mocks.RecordingLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class LoggableTest {
    class TestLoggable : Loggable {
        override val logger: Logging = RecordingLogger()
        val currentRecord: RecordingLogger.Record? get() = (logger as RecordingLogger).latestRecord
    }

    private val expectedTag = "TestLoggable"
    private val message = "Test Message"

    @Test
    fun `correct Tag should be returned`() {
        val sut = TestLoggable()
        assertEquals("TestLoggable", sut.tag)
    }

    @Test
    fun `logD should return correct values`() {
        val sut = TestLoggable()
        sut.logD(message)

        assertEquals(RecordingLogger.Record(expectedTag, "d", message), sut.currentRecord)
    }

    @Test
    fun `logI should return correct values`() {
        val sut = TestLoggable()
        sut.logI(message)

        assertEquals(RecordingLogger.Record(expectedTag, "i", message), sut.currentRecord)
    }

    @Test
    fun `logW should return correct values`() {
        val sut = TestLoggable()
        sut.logW(message)

        assertEquals(RecordingLogger.Record(expectedTag, "w", message), sut.currentRecord)
    }

    @Test
    fun `logE should return correct values`() {
        val sut = TestLoggable()
        sut.logE(message)

        assertEquals(RecordingLogger.Record(expectedTag, "e", message), sut.currentRecord)
    }
}
