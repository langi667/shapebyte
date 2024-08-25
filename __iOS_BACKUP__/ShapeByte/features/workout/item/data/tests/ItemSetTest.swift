//
//  ItemSetTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 05.08.24.
//

import XCTest

final class ItemSetTest: XCTestCase {

    private struct TestItem: Item, Equatable {
        var name = "TestItem"

        func isEqualTo(_ other: Any) -> Bool {
            return (other as? TestItem) != nil
        }

    }

    private struct TestItemTwo: Item, Equatable {
        var name = "TestItemTwo"

        func isEqualTo(_ other: Any) -> Bool {
            return (other as? TestItemTwo) != nil
        }

    }

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testDurationShouldReturnValue() throws {
        let duration = 5.0
        let sut = ItemSet.timed(item: TestItemTwo(), duration: duration)

        XCTAssertEqual(duration, sut.duration)
    }

    func testIsTimedShouldReturnTrue() throws {
        let duration = 5.0
        let sut = ItemSet.timed(item: TestItemTwo(), duration: duration)

        XCTAssertTrue(sut.isTimed)
    }

    func testItem() throws {
        let item = TestItemTwo()
        let sut = ItemSet.timed(item: TestItemTwo(), duration: 5.0)

        XCTAssertEqual(item, sut.item as! ItemSetTest.TestItemTwo)
    }
}
