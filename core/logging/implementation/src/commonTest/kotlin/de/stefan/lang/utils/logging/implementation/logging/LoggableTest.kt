package de.stefan.lang.utils.logging.implementation.logging

import de.stefan.lang.utils.logging.contract.Loggable
import de.stefan.lang.utils.logging.contract.RecordLog
import de.stefan.lang.utils.logging.contract.RecordingLogging
import de.stefan.lang.utils.logging.implementation.recording.RecordingLogger
import de.stefan.lang.utils.logging.implementation.silent.SilentLogger
import kotlin.test.Test
import kotlin.test.assertEquals

class LoggableTest {
    class TestLoggable : Loggable {
        override val logger: RecordingLogging = RecordingLogger(SilentLogger())
        val currentRecordLog: RecordLog? get() = logger.latestRecordLog
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

        assertEquals(RecordLog(expectedTag, "d", message), sut.currentRecordLog)
    }

    @Test
    fun `logI should return correct values`() {
        val sut = TestLoggable()
        sut.logI(message)

        assertEquals(RecordLog(expectedTag, "i", message), sut.currentRecordLog)
    }

    @Test
    fun `logW should return correct values`() {
        val sut = TestLoggable()
        sut.logW(message)

        assertEquals(RecordLog(expectedTag, "w", message), sut.currentRecordLog)
    }

    @Test
    fun `logE should return correct values`() {
        val sut = TestLoggable()
        sut.logE(message)

        assertEquals(RecordLog(expectedTag, "e", message), sut.currentRecordLog)
    }
}
