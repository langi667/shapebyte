package de.stefan.lang.shapebyte.utils

import kotlin.test.Test
import kotlin.test.assertEquals

class LoggableTest {
    class TestLogger : Logging {
        data class Entry(
            val tag: String,
            val level: String,
            val message: String,
        )

        val currentEntry: Entry? get() = _entries.lastOrNull()
        private val _entries = mutableListOf<Entry>()

        override fun d(tag: String, message: String) {
            _entries.add(Entry(tag, "d", message))
        }

        override fun i(tag: String, message: String) {
            _entries.add(Entry(tag, "i", message))
        }

        override fun w(tag: String, message: String) {
            _entries.add(Entry(tag, "w", message))
        }

        override fun e(tag: String, message: String) {
            _entries.add(Entry(tag, "e", message))
        }
    }

    class TestLoggable : Loggable {
        override val logger: Logging = TestLogger()
        val testLogger: TestLogger get() = logger as TestLogger
        val currentEntry: TestLogger.Entry? get() = testLogger.currentEntry
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

        assertEquals(TestLogger.Entry(expectedTag, "d", message), sut.currentEntry)
    }

    @Test
    fun `logI should return correct values`() {
        val sut = TestLoggable()
        sut.logI(message)

        assertEquals(TestLogger.Entry(expectedTag, "i", message), sut.currentEntry)
    }

    @Test
    fun `logW should return correct values`() {
        val sut = TestLoggable()
        sut.logW(message)

        assertEquals(TestLogger.Entry(expectedTag, "w", message), sut.currentEntry)
    }

    @Test
    fun `logE should return correct values`() {
        val sut = TestLoggable()
        sut.logE(message)

        assertEquals(TestLogger.Entry(expectedTag, "e", message), sut.currentEntry)
    }
}
