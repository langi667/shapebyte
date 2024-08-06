//
//  ItemSetDataTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 05.08.24.
//

import XCTest

final class ItemSetDataTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testTimedItemData() throws {
        let progress = Progress(0.5)
        let sut: ItemSet.Data = .timed(timePassed: 30, timeRemaining: 30, progress: progress)

        XCTAssertEqual(progress, sut.progress)
    }
}
