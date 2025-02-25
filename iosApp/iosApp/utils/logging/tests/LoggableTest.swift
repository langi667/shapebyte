import Testing
import shared

struct LoggableTest {
    class TestClass: Loggable {
        var logger: any Logging = RecordingLogger()
        var recLogger: RecordingLogger? { logger as? RecordingLogger }
    }

    private let sut = TestClass()

    @Test func testConditionTest() async throws {
        #expect(sut.recLogger != nil)
    }

    @Test func tagTest() async throws {
        #expect("TestClass" == sut.tag)
    }

    @Test func logDTest() async throws {
        let msg = "Log Debug"
        sut.logD(message: msg)

        #expect(sut.tag == sut.recLogger?.latestRecord?.tag)
        #expect(msg == sut.recLogger?.latestRecord?.message)
        #expect("d" == sut.recLogger?.latestRecord?.level)
    }

    @Test func logITest() async throws {
        let msg = "Log Info"
        sut.logI(message: msg)

        #expect(sut.tag == sut.recLogger?.latestRecord?.tag)
        #expect(msg == sut.recLogger?.latestRecord?.message)
        #expect("i" == sut.recLogger?.latestRecord?.level)
    }

    @Test func logWTest() async throws {
        let msg = "Log Warning"
        sut.logW(message: msg)

        #expect(sut.tag == sut.recLogger?.latestRecord?.tag)
        #expect(msg == sut.recLogger?.latestRecord?.message)
        #expect("w" == sut.recLogger?.latestRecord?.level)
    }

    @Test func logETest() async throws {
        let msg = "Log Error"
        sut.logE(message: msg)

        #expect(sut.tag == sut.recLogger?.latestRecord?.tag)
        #expect(msg == sut.recLogger?.latestRecord?.message)
        #expect("e" == sut.recLogger?.latestRecord?.level)
    }
}
