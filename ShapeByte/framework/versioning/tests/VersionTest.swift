//
//  VersionTest.swift
//  GraphQLFragmentJoinerTests
//
//  Created by Lang, Stefan [RTL Tech] on 14.05.24.
//

import XCTest

final class VersionTest: XCTest {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testDescription() throws {
        var sut = Version(major: 1, minor: 0, patch: 0)
        XCTAssertEqual(sut.description, "1.0.0")

        sut = Version(major: 3, minor: 4, patch: 5)
        XCTAssertEqual(sut.description, "3.4.5")

        sut = Version(major: 0, minor: 0, patch: 0)
        XCTAssertEqual(sut.description, "0.0.0")
    }

    func testInitWithDescriptionShouldCreateInstance() throws {
        var sut = Version(description: "1.0.0")
        XCTAssertEqual(sut?.major, 1)
        XCTAssertEqual(sut?.minor, 0)
        XCTAssertEqual(sut?.patch, 0)

        sut = Version(description: "3.4")
        XCTAssertEqual(sut?.major, 3)
        XCTAssertEqual(sut?.minor, 4)
        XCTAssertEqual(sut?.patch, 0)

        sut = Version(description: "3.4.")
        XCTAssertEqual(sut?.major, 3)
        XCTAssertEqual(sut?.minor, 4)
        XCTAssertEqual(sut?.patch, 0)

        sut = Version(description: "3")
        XCTAssertEqual(sut?.major, 3)
        XCTAssertEqual(sut?.minor, 0)
        XCTAssertEqual(sut?.patch, 0)

        sut = Version(description: "3.")
        XCTAssertEqual(sut?.major, 3)
        XCTAssertEqual(sut?.minor, 0)
        XCTAssertEqual(sut?.patch, 0)

        sut = Version(description: "3..")
        XCTAssertEqual(sut?.major, 3)
        XCTAssertEqual(sut?.minor, 0)
        XCTAssertEqual(sut?.patch, 0)
    }

    func testInitWithDescriptionShouldReturnNil() throws {
        var sut = Version(description: "")
        XCTAssertNil(sut)

        sut = Version(description: "b.1.2")
        XCTAssertNil(sut)

        sut = Version(description: "1.x.2")
        XCTAssertNil(sut)

        sut = Version(description: "1.2.p")
        XCTAssertNil(sut)

        sut = Version(description: ".1.2")
        XCTAssertNil(sut)

        sut = Version(description: ".1.2.3")
        XCTAssertNil(sut)
    }

    func testLessThanOperator() throws {
        var lhs = Version(major: 1, minor: 0, patch: 0)
        var rhs = Version(major: 2, minor: 0, patch: 0)
        XCTAssertTrue(lhs < rhs)

        lhs = Version(major: 1, minor: 0, patch: 0)
        rhs = Version(major: 1, minor: 1, patch: 0)
        XCTAssertTrue(lhs < rhs)

        lhs = Version(major: 1, minor: 0, patch: 0)
        rhs = Version(major: 1, minor: 0, patch: 1)
        XCTAssertTrue(lhs < rhs)

        lhs = Version(major: 1, minor: 0, patch: 0)
        rhs = Version(major: 1, minor: 1, patch: 1)
        XCTAssertTrue(lhs < rhs)

        lhs = Version(major: 0, minor: 2, patch: 3)
        rhs = Version(major: 0, minor: 3, patch: 0)
        XCTAssertTrue(lhs < rhs)

        lhs = Version(major: 0, minor: 2, patch: 3)
        rhs = Version(major: 0, minor: 2, patch: 4)
        XCTAssertTrue(lhs < rhs)

        lhs = Version(major: 0, minor: 0, patch: 3)
        rhs = Version(major: 0, minor: 0, patch: 4)
        XCTAssertTrue(lhs < rhs)
    }

    func testGreaterThanOperator() throws {
        var lhs = Version(major: 3, minor: 0, patch: 0)
        var rhs = Version(major: 2, minor: 0, patch: 0)
        XCTAssertTrue(lhs > rhs)

        lhs = Version(major: 3, minor: 2, patch: 0)
        rhs = Version(major: 3, minor: 1, patch: 0)
        XCTAssertTrue(lhs > rhs)

        lhs = Version(major: 3, minor: 1, patch: 2)
        rhs = Version(major: 3, minor: 1, patch: 1)
        XCTAssertTrue(lhs > rhs)

        lhs = Version(major: 0, minor: 2, patch: 0)
        rhs = Version(major: 0, minor: 1, patch: 0)
        XCTAssertTrue(lhs > rhs)

        lhs = Version(major: 0, minor: 0, patch: 2)
        rhs = Version(major: 0, minor: 0, patch: 1)
        XCTAssertTrue(lhs > rhs)
    }
}
