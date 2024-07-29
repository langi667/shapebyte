//
//  DefaultLoggerTest.swift
//  GraphQLFragmentJoinerTests
//
//  Created by Lang, Stefan [RTL Tech] on 13.05.24.
//

import XCTest

final class DefaultLoggerTest: XCTest {
    let sut = DefaultLogger()

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        sut.isEnabled = true
        sut.logLevel = .debug
    }

    func testDefaultValues() throws {
        XCTAssertEqual(sut.isEnabled, true)
        XCTAssertEqual(sut.logLevel, .debug)
    }

    func testShouldNotLogIfDisabled() throws {
        sut.isEnabled = false

        let result = sut.log("Test", level: .debug)
        XCTAssertTrue(result.isEmpty)
    }

    func testShouldNotLogIfLevelIsBelow() throws {
        sut.logLevel = .info
        var result = sut.logDebug("Test")
        XCTAssertTrue(result.isEmpty)

        sut.logLevel = .warning
        result = sut.logInfo("Test")
        XCTAssertTrue(result.isEmpty)

        sut.logLevel = .error
        result = sut.logWarning("Test")
        XCTAssertTrue(result.isEmpty)

        sut.logLevel = .fatal
        result = sut.logError("Test")
        XCTAssertTrue(result.isEmpty)
    }

    func testShouldLog() throws {
        let message = "Test"

        for level in LogLevel.allCases {
            let result = sut.log(message, level: level)
            XCTAssertTrue(matchesLogString(result, level: level, message: message))
        }
    }

    private func matchesLogString(_ logString: String, level: LogLevel, message: String) -> Bool {
        let pattern = "\\[\(level.printableName)\\] \\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}: \(message)"
        let regex = try? NSRegularExpression(pattern: pattern, options: [])
        let range = NSRange(location: 0, length: logString.utf16.count)

        let retVal = regex?.firstMatch(in: logString, options: [], range: range) != nil
        return retVal
    }

}
