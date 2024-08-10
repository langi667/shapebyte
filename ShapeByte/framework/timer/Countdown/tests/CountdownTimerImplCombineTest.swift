//
//  CountdownTimerImplCombineTest.swift
//  ShapeByteTests
//
//  Created by Lang, Stefan [RTL Tech] on 10.08.24.
//

import XCTest

final class CountdownTimerImplCombineTest: XCTestCase {
    var sut: CountdownTimer? = nil

    override func setUpWithError() throws {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDownWithError() throws {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testInitialState() throws {
        let sut = CountdownTimerImplCombine()
        XCTAssertEqual(sut.state, .idle)
    }

    func testTimerRun() throws {
        let interval: TimeInterval = 0.1
        var currInterval: TimeInterval = 0

        let expectation = XCTestExpectation(description: "Timer")
        sut = CountdownTimerImplCombine()
        XCTAssertEqual(sut?.state, .idle)

        _ = sut?.start(interval: interval) { interval in
            XCTAssertEqual(self.sut?.state, .running)
            currInterval += interval

                if currInterval >= 0.5 {
                    self.sut?.stop()
                    expectation.fulfill()
                }
            }

        wait(for: [expectation], timeout: 1)
        XCTAssertEqual(self.sut?.state, .stopped)
    }

    func testTimerPauseAndResume() throws {
        let interval: TimeInterval = 0.1
        var currInterval: TimeInterval = 0

        let expectation = XCTestExpectation(description: "TimerPause")

        sut = CountdownTimerImplCombine()
        XCTAssertEqual(sut?.state, .idle)

        _ = sut?.start(interval: interval) { interval in
            XCTAssertEqual(self.sut?.state, .running)
            currInterval += interval

                if currInterval >= 0.2 {
                    self.sut?.pause()
                    expectation.fulfill()
                }
            }

        wait(for: [expectation], timeout: 0.5)
        XCTAssertEqual(self.sut?.state, .paused)

        let expectationStop = XCTestExpectation(description: "TimerStop")
        _ = sut?.start(interval: interval) { interval in
            currInterval += interval

                if currInterval >= 0.5 {
                    self.sut?.stop()
                    expectationStop.fulfill()
                }
            }
        
        wait(for: [expectationStop], timeout: 0.5)
        XCTAssertEqual(self.sut?.state, .stopped)

    }

    func testTimerStartMultipleTimes() throws {
        let interval: TimeInterval = 0.1
        var currInterval: TimeInterval = 0

        var expectation = XCTestExpectation(description: "Timer")
        sut = CountdownTimerImplCombine()
        XCTAssertEqual(sut?.state, .idle)

        _ = sut?.start(interval: interval) { interval in
            XCTAssertEqual(self.sut?.state, .running)
            currInterval += interval

                if currInterval >= 0.1 {
                    self.sut?.stop()
                    expectation.fulfill()
                }
            }

        wait(for: [expectation], timeout: 0.5)
        XCTAssertEqual(self.sut?.state, .stopped)

        expectation = XCTestExpectation(description: "Timer")
        
        _ = sut?.start(interval: interval) { interval in
            XCTAssertEqual(self.sut?.state, .running)
            currInterval += interval

                if currInterval >= 0.1 {
                    self.sut?.stop()
                    expectation.fulfill()
                }
            }

        wait(for: [expectation], timeout: 0.5)
        XCTAssertEqual(self.sut?.state, .stopped)
    }
}
