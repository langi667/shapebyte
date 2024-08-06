//
//  ItemSetTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 05.08.24.
//

import XCTest

final class ItemSetTest: XCTestCase {

    private struct TestItem: Item {
        var name = "TestItem"

        func isEqualTo(_ other: Any) -> Bool {
            return (other as? TestItem) != nil
        }

    }

    private struct TestItemTwo: Item {
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

    func testCastItem() throws {
        let duration = 30.0
        let testItem = TestItem()
        let sut: ItemSet = .timed(item: testItem, duration: duration)

        let result: TestItem? = sut.castItem()
        XCTAssertNotNil(result)
        XCTAssertEqual(testItem, result)

        let resultTwo: TestItemTwo? = sut.castItem()
        XCTAssertNil(resultTwo)
    }

    func testTimedItemSet() throws {
        let duration = 30.0
        let exercise = Exercise.none
        let sut: ItemSet = .timed(item: exercise, duration: duration)

        XCTAssertTrue(sut.isTimed)
        XCTAssertEqual(duration, sut.duration)
        XCTAssertEqual(exercise, sut.castItem())
    }
}
