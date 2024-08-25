//
//  TimedItemSetHandler.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation
import Combine

// TODO: test paused
class TimedItemSetHandler: ItemSetHandling {
    let logger: Logging

    fileprivate(set) var statePublisher: PassthroughSubject<ItemSet.State, Never>
    = PassthroughSubject<ItemSet.State, Never>()

    fileprivate(set) var set: ItemSet?

    private var duration: TimeInterval = 0
    private let timer: CountdownTimer

    private var elapsedTime: TimeInterval = 0
    private var progress: Progress = .zero

    private var state: ItemSet.State = .idle {
        didSet {
            statePublisher.send(state)
        }
    }

    private let timerTick: TimeInterval = 1

    init(logger: Logging, timer: CountdownTimer) {
        self.logger = logger
        self.timer = timer
    }

    func start(set: ItemSet) {
        if !state.isStopped {
            return
        }

        if case let .timed(_, duration) = set {
            self.set = set

            stopTimer()
            resetValues()

            self.duration = duration
            let setData = currentStateData()
            self.state = .started(setData: setData)

            startTimer()
        } else {
            // TODO: log invalid type
        }
    }

    func pause() {
        if !state.isRunning {
            return
        }

        self.state = .paused(setData: currentStateData())
        self.timer.pause()
    }

    func finish() {
        self.stopTimer()
        resetValues()
        self.state = .finished
    }

    private func resetValues() {
        self.duration = 0
        self.elapsedTime = 0
        self.progress = .zero
    }

    private func startTimer() {
        _ = timer.start(interval: timerTick) { interval in
            self.handleTimerTick(interval: interval)
        }
    }

    private func stopTimer() {
        timer.stop()
    }

    private func handleTimerTick(interval: CGFloat) {
        if !state.isRunning {
            return
        }

        self.elapsedTime += interval
        let newProgress = self.elapsedTime / self.duration
        self.progress = Progress(newProgress)

        let stateData = currentStateData()
        self.state = .running(setData: stateData)

        if self.progress.value >= 1.0 {
            stopTimer()
            finish()
        }
    }

    private func currentStateData() -> ItemSet.Data {
        ItemSet.Data.timed(
            timePassed: self.elapsedTime,
            timeRemaining: self.duration - self.elapsedTime,
            progress: self.progress,
            nextProgress: Progress( min(1.0, (self.elapsedTime + timerTick) / self.duration))
        )
    }
}
