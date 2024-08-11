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

        XCTAssertTrue(states.contains(.started(totalSets: 5)))

//        let pushUps = Exercise(name: "Push Up")
//        let pullUps = Exercise(name: "Pull Up")
//
//        let session = WorkoutSession(
//            items: [
//                Countdown(seconds: 3).toGroup(),
//
//                ItemGroup(
//                    item: pushUps,
//                    itemSets: ItemSets(
//                        sets: [
//                            ItemSet.timed(item: pushUps, duration: 3),
//                            ItemSet.timed(item: pushUps, duration: 4),
//                            ItemSet.timed(item: pushUps, duration: 5)
//
//                        ]
//                    )
//                ),
//
//                ItemGroup(
//                    item: pullUps,
//                    itemSets: ItemSets(
//                        sets: [
//                            ItemSet.timed(item: pullUps, duration: 20),
//                            ItemSet.timed(item: pullUps, duration: 40),
//                            ItemSet.timed(item: pullUps, duration: 60)
//                        ]
//                    )
//                )
//            ]
//        )

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
