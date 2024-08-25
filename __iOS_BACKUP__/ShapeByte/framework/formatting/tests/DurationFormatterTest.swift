//
//  DurationFormatter.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 05.08.24.
//

import XCTest

final class DurationFormatterTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testSecondsWithInt() throws {
        var result = DurationFormatter.secondsToString(59)
        XCTAssertEqual("59", result)

        result = DurationFormatter.secondsToString(0)
        XCTAssertEqual("0", result)

        result = DurationFormatter.secondsToString(30)
        XCTAssertEqual("30", result)
    }

    func testSecondsWithTimeInterval() throws {
        var result = DurationFormatter.secondsToString(59.0)
        XCTAssertEqual("59", result)

        result = DurationFormatter.secondsToString(59.3)
        XCTAssertEqual("59", result)

        result = DurationFormatter.secondsToString(59.5)
        XCTAssertEqual("60", result)

        result = DurationFormatter.secondsToString(59.7)
        XCTAssertEqual("60", result)

    }
}
