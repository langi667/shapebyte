//
//  CountdownItemSetsViewModelWrapperTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 16.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Testing
import Combine
import shared
import XCTest

struct CountdownItemSetsViewModelWrapperTest {
    @Test
    func initialStateTest() async throws {
        let sut = await CountdownItemSetsViewModelWrapper()

        await #expect(sut.countdownText == "")
        await #expect(sut.alpha == 1)
        await #expect(sut.scale == 1)
    }

    @Test
    func handleObservedState_IdleTest() async throws {
        let sut = await CountdownItemSetsViewModelWrapper()
        await sut.handleObservedState(UIState.Idle.shared)

        await #expect(sut.countdownText == "") // should not change current values
        await #expect(sut.alpha == 1) // should not change current values
        await #expect(sut.scale == 1) // should not change current values
    }

    @Test
    func handleObservedState_LoadingTest() async throws {
        let sut = await CountdownItemSetsViewModelWrapper()
        await sut.handleObservedState(UIState.Loading.shared)

        await #expect(sut.countdownText == "") // should not change current values
        await #expect(sut.alpha == 1) // should not change current values
        await #expect(sut.scale == 1) // should not change current values
    }

    @Test
    func handleObservedState_DataTest() async throws {
        let sut = await CountdownItemSetsViewModelWrapper()
        let data = CountdownItemSetsViewData(countdownText: "Test", scale: 3, animationDuration: 1, alpha: 1)

        await sut.handleObservedState(UIStateData(data: data))

        await #expect(sut.countdownText == data.countdownText)
        await #expect(sut.alpha == data.cgAlpha)
        await #expect(sut.scale == data.cgScale)
    }
}

class CountdownItemSetsViewModelWrapperStateTest: XCTestCase {
    private var cancellables: Set<AnyCancellable> = []

    @MainActor
    func test_observeState() {
        let sut = CountdownItemSetsViewModelWrapper()
        let expectation = XCTestExpectation(description: "Value should be updated")

        let itemSets = [
            ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
            ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
            ItemSet.Timed.forDuration(.seconds(1), item: None.shared)
        ]

        var texts = [String]()

        sut.$state.sink { state in
            if let data: CountdownItemSetsViewData = state.viewData(), !data.countdownText.isEmpty {
                texts.append(data.countdownText)

                if texts.count == itemSets.count {
                    expectation.fulfill()
                }

            }
        }.store(in: &cancellables)

        sut.start(itemSets: itemSets)
        sut.observeState()

        wait(for: [expectation], timeout: 5.0)

        for index in 0..<texts.count { // Backward counting 3 .. 1. Check
            let text = (texts.count - index).description
            XCTAssertEqual(texts[index], text )
        }
    }
}
