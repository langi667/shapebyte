//
//  TimedItemSetHandlerTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 06.08.24.
//

import XCTest
import Combine

final class TimedItemSetHandlerTest: XCTestCase {
    var cancellables = Set<AnyCancellable>()

    override func setUpWithError() throws {
        cancellables.removeAll()
    }

    override func tearDownWithError() throws {
        cancellables.removeAll()
    }

    func testShouldNotPublsihAnyStateInitially() throws {
        let sut = createSUT()
        let expectation = XCTestExpectation(description: "Inital state shoud not publish")

        expectation.isInverted = true
        evaluatePublisher(sut.statePublisher) { _ in
            expectation.fulfill()

        }

        wait(for: [expectation], timeout: 1)
    }

    func testStartShouldPublishStatesInCorrectOrder() throws {
        let sut = createSUT()
        var emittedStates: [ItemSet.State] = []
        let duration = 1.0

        let expectedState: [ItemSet.State] = [
            .started,
            .running(setData: ItemSet.Data.timed(timePassed: duration, timeRemaining: 0, progress: .complete)),
            .finished
        ]

        let expectation = XCTestExpectation(description: "Receive all states in correct order")
        evaluatePublisher(sut.statePublisher) { state in
            emittedStates.append(state)
            if state == .finished {
                expectation.fulfill()
            }
        }

        sut.start(set: .timed(item: Exercise(name: "Test"), duration: duration))
        wait(for: [expectation], timeout: duration * 2)

        XCTAssertEqual(emittedStates, expectedState)
    }

    func testShouldTickMultipleTimes() throws {
        let sut = createSUT()
        var emittedStates: [ItemSet.State] = []
        let duration: TimeInterval = 5.0

        var expectedState: [ItemSet.State] = [
            .started
        ]

        for i in 1...Int(duration) {
            let currSecond = Double(i)

            expectedState.append(
                .running(setData: ItemSet.Data.timed(timePassed: currSecond, timeRemaining: duration - currSecond, progress: Progress(currSecond / duration)))
            )
        }

        expectedState.append(.finished)

        let expectation = XCTestExpectation(description: "Receive all states in correct order")

        evaluatePublisher(sut.statePublisher) { state in
            emittedStates.append(state)
            if state == .finished {
                expectation.fulfill()
            }
        }

        sut.start(set: .timed(item: Exercise(name: "Test"), duration: duration))
        wait(for: [expectation], timeout: duration * 2)

        XCTAssertEqual(emittedStates, expectedState)
    }

    private func evaluatePublisher(
        _ publisher: PassthroughSubject<ItemSet.State, Never>,
        test: @escaping (ItemSet.State) -> Void) {
            publisher.sink { state in
                test(state)
            }.store(in: &cancellables)

        }

    private func createSUT() -> TimedItemSetHandler {
        return TimedItemSetHandler(logger: DefaultLogger(), timer: TimerModule.countdownTimerMock)
    }
}
