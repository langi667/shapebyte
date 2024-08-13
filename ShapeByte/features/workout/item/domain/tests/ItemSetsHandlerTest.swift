//
//  ItemSetsHandlerTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 11.08.24.
//

import XCTest
import Combine

final class ItemSetsHandlerTest: XCTestCase {
    var cancellables = Set<AnyCancellable>()

    override func setUpWithError() throws {
        cancellables.removeAll()
    }

    override func tearDownWithError() throws {
        cancellables.removeAll()
    }

    func testInitialStarte() throws {
        let sut = createSUT()
        XCTAssertEqual(sut.state, .idle)
    }

    func testCountdownSetsHandling() throws {
        let sut = createSUT()
        let seconds = UInt(5)
        let itemSets = Countdown(seconds: seconds).toGroup().itemSets.sets

        var states = [ItemSetsState]()
        let expectation = XCTestExpectation(description: "testCountdownSetsHandling")

        sut.$state.sink { currSetState in
            states.append(currSetState)

            if currSetState == .finished {
                expectation.fulfill()
            }

        }.store(in: &self.cancellables)

        sut.start(sets: itemSets)
        wait(for: [expectation])

        XCTAssertTrue(states.contains(.idle))
        XCTAssertTrue(states.contains(.started(totalSets: 5)))
        XCTAssertTrue(states.contains(.finished))

        for currSet in 0..<seconds {
            let startData = ItemSet.Data.timed(
                timePassed: TimeInterval(0),
                timeRemaining: TimeInterval(1),
                progress: .zero,
                nextProgress: .complete)

            let startSet = ItemSetsState.running(
                currentSet: Int(currSet),
                totalSets: Int(seconds),
                currentSetProgress: .zero,
                totalProgress: Progress(CGFloat(currSet) / CGFloat(seconds)),
                setData: startData
            )

            let contains = states.contains(startSet)
            XCTAssertTrue(contains)
        }
    }

    func testTimedSetsHandling() throws {
        let sut = createSUT()
        let seconds = UInt(5)
        let itemSets = Countdown(seconds: seconds).toGroup().itemSets.sets

        var states = [ItemSetsState]()
        let expectation = XCTestExpectation(description: "testCountdownSetsHandling")

        sut.$state.sink { currSetState in
            states.append(currSetState)

            if currSetState == .finished {
                expectation.fulfill()
            }

        }.store(in: &self.cancellables)

        sut.start(sets: itemSets)
        wait(for: [expectation])

        XCTAssertTrue(states.contains(.started(totalSets: 5)))
    }


    func createSUT() -> ItemSetsHandler {
        let retVal = ItemSetsHandler(
            logger: SharedModule.logger,
            timedSetHandler: TimedItemSetHandler(
                logger: SharedModule.logger,
                timer: TimerModule.countdownTimerMock
            ),
            defaultSetHandler: WorkoutItemModule.defaultSetHandler
        )

        return retVal
    }
}
