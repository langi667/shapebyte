//
//  ItemSetsStateTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 11.08.24.
//

import XCTest

final class ItemSetsStateTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testIsRunning() throws {
        var sut: ItemSetsState = .idle
        XCTAssertFalse(sut.isRunning)

        sut = .started(totalSets: 5)
        XCTAssertTrue(sut.isRunning)

        sut = .running(
            currentSet: 1,
            totalSets: 5,
            currentSetProgress: .complete,
            totalProgress: Progress(1.0 / 5.0),
            setData: ItemSet.Data.timed(
                timePassed: 1,
                timeRemaining: 5, progress: Progress(1.0 / 5.0),
                nextProgress: Progress(2.0 / 5.0)
            )
        )

        XCTAssertTrue(sut.isRunning)

        sut = .paused
        XCTAssertFalse(sut.isRunning)

        sut = .finished
        XCTAssertFalse(sut.isRunning)

    }

}
