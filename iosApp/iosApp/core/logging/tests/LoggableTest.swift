import Testing
import shared

struct LoggableTest {
    class TestClass: Loggable {
        let logger: any Logging = shared.CoreUtilsModule.shared.recordingLogger()
        var recLogger: RecordingLogging? { logger as? RecordingLogging }
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

        #expect(sut.tag == sut.recLogger?.latestRecordLog?.tag)
        #expect(msg == sut.recLogger?.latestRecordLog?.message)
        #expect("d" == sut.recLogger?.latestRecordLog?.level)
    }

    @Test func logITest() async throws {
        let msg = "Log Info"
        sut.logI(message: msg)

        #expect(sut.tag == sut.recLogger?.latestRecordLog?.tag)
        #expect(msg == sut.recLogger?.latestRecordLog?.message)
        #expect("i" == sut.recLogger?.latestRecordLog?.level)
    }

    @Test func logWTest() async throws {
        let msg = "Log Warning"
        sut.logW(message: msg)

        #expect(sut.tag == sut.recLogger?.latestRecordLog?.tag)
        #expect(msg == sut.recLogger?.latestRecordLog?.message)
        #expect("w" == sut.recLogger?.latestRecordLog?.level)
    }

    @Test func logETest() async throws {
        let msg = "Log Error"
        sut.logE(message: msg)

        #expect(sut.tag == sut.recLogger?.latestRecordLog?.tag)
        #expect(msg == sut.recLogger?.latestRecordLog?.message)
        #expect("e" == sut.recLogger?.latestRecordLog?.level)
    }
}
