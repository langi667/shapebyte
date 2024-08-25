//
//  ItemSet+StateTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 05.08.24.
//

import XCTest

final class ItemSet_StateTest: XCTestCase {

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testIsRunning() throws {
        var sut: ItemSet.State = .idle
        XCTAssertFalse(sut.isRunning)

        sut = .finished
        XCTAssertFalse(sut.isRunning)

        let data: ItemSet.Data = .timed(timePassed: 30, timeRemaining: 30, progress: Progress(0.5), nextProgress: .complete)
        sut = .paused(setData: data)
        XCTAssertFalse(sut.isRunning)

        sut = .started(setData: data)
        XCTAssertTrue(sut.isRunning)

        sut = .running(setData: data)
        XCTAssertTrue(sut.isRunning)
    }

    func testIsStopped() throws {
        var sut: ItemSet.State = .idle
        XCTAssertTrue(sut.isStopped)

        sut = .finished
        XCTAssertTrue(sut.isStopped)

        let data: ItemSet.Data = .timed(timePassed: 30, timeRemaining: 30, progress: Progress(0.5), nextProgress: .complete)
        sut = .paused(setData: data)
        XCTAssertFalse(sut.isStopped)

        sut = .running(setData: data)
        XCTAssertFalse(sut.isStopped)
    }
}
