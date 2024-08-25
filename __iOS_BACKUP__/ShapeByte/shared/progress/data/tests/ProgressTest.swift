//
//  ProgressTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 11.08.24.
//

import XCTest

final class ProgressTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testRelativeValue() throws {
        var sut = Progress(0)
        XCTAssertEqual(0, sut.value)

        sut = Progress(0.5)
        XCTAssertEqual(0.5, sut.value)

        sut = Progress(1)
        XCTAssertEqual(1, sut.value)

        sut = Progress(1.6)
        XCTAssertEqual(1, sut.value)
    }

    func testAbsoluteValue() throws {
        var sut = Progress(absoluteValue: 0)
        XCTAssertEqual(0, sut.value)

        sut = Progress(absoluteValue: 50)
        XCTAssertEqual(0.5, sut.value)

        sut = Progress(absoluteValue: 100)
        XCTAssertEqual(1, sut.value)

        sut = Progress(160)
        XCTAssertEqual(1, sut.value)
    }
}
