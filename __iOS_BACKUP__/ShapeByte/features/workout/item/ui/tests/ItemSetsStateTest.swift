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

    func testEqualsIdle() throws {
        let sut: ItemSetsState = .idle
        XCTAssertEqual(.idle, sut)
        XCTAssertNotEqual(.started(totalSets: 5), sut)

    }

    func testEqualsStart() throws {
        let totalSets = 5
        let sut: ItemSetsState = .started(totalSets: totalSets)

        XCTAssertEqual(.started(totalSets: totalSets), sut)
        XCTAssertNotEqual(.started(totalSets: totalSets + 1), sut)
        XCTAssertNotEqual(.idle, sut)
    }

    func testEqualsRunning() throws {
        let totalSets = 5
        let currentSet = 1
        let currentSetProgress: Progress = .zero
        let totalSetProgress: Progress = Progress(0.5)
        let setData: ItemSet.Data = ItemSet.Data.timed(
            timePassed: 0,
            timeRemaining: 5,
            progress: .zero,
            nextProgress: .complete
        )

        let sut: ItemSetsState = .running(
            currentSet: currentSet,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: setData
        )

        XCTAssertEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: setData
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet + 1,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: setData
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets + 1,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: setData
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets + 1,
            currentSetProgress: Progress(0.3333),
            totalProgress: totalSetProgress,
            setData: setData
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: Progress(0.3333),
            setData: setData
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: ItemSet.Data.timed(
                timePassed: 1,
                timeRemaining: 5,
                progress: .zero,
                nextProgress: .complete)
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: ItemSet.Data.timed(
                timePassed: 0,
                timeRemaining: 4,
                progress: .zero,
                nextProgress: .complete)
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: ItemSet.Data.timed(
                timePassed: 0,
                timeRemaining: 5,
                progress: Progress(0.3),
                nextProgress: .complete)
        ), sut )

        XCTAssertNotEqual(.running(
            currentSet: currentSet,
            totalSets: totalSets,
            currentSetProgress: currentSetProgress,
            totalProgress: totalSetProgress,
            setData: ItemSet.Data.timed(
                timePassed: 0,
                timeRemaining: 5,
                progress: .zero,
                nextProgress: Progress(0.3))
        ), sut )

        XCTAssertNotEqual(.started(totalSets: totalSets + 1), sut)
        XCTAssertNotEqual(.idle, sut)
    }

    func testEqualsFinish() throws {
        let totalSets = 5
        let sut: ItemSetsState = .finished

        XCTAssertEqual(.finished, sut)
        XCTAssertNotEqual(.started(totalSets: totalSets), sut)
        XCTAssertNotEqual(.idle, sut)

        let running: ItemSetsState = .running(
            currentSet: 1,
            totalSets: 2,
            currentSetProgress: .zero,
            totalProgress: .complete,
            setData: ItemSet.Data.timed(
                timePassed: 0,
                timeRemaining: 5,
                progress: .zero,
                nextProgress: .complete
            )
        )

        XCTAssertNotEqual(running, sut)

    }

}
