//
//  Array+ExtensionsTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 05.08.24.
//

import XCTest

final class Array_ExtensionsTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testGetOrNull() throws {
        let count = 3
        let sut: [String] = ["Test1", "Test2", "Test3"]

        var result: String? = sut.getOrNull(0)
        XCTAssertEqual("Test1", result)

        result = sut.getOrNull(count)
        XCTAssertNil(result)

        result = sut.getOrNull(-1)
        XCTAssertNil(result)
    }
}
