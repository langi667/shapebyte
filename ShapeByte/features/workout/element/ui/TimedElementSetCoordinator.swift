//
//  TimedSetCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 28.07.24.
//

import Foundation
import Combine

class TimedElementSetCoordinator: ElementSetCoordinating {
    fileprivate(set) var statePublisher: PassthroughSubject<ElementSet.State, Never>
    = PassthroughSubject<ElementSet.State, Never>()

    fileprivate(set) var set: ElementSet?

    private var duration: TimeInterval = 0
    private var timer: AnyCancellable?
    private var elapsedTime: TimeInterval = 0
    private var progress: Progress = .zero

    private var state: ElementSet.State = .idle

    func start(set: ElementSet) {
        if !state.isStopped {
            return
        }

        if case let .timed(duration) = set {
            self.set = set

            stopTimer()
            resetValues()

            self.duration = duration
            self.state = .running(setData: currentStateData())

            startTimer()
        } else {
            // TODO: log invalid type
        }
    }

    func finish() {
        self.state = .finished
        self.stopTimer()

        resetValues()
        self.statePublisher.send(.finished)
    }

    private func resetValues() {
        self.duration = 0
        self.elapsedTime = 0
        self.progress = .zero
    }

    private func startTimer() {
        let interval: CGFloat = 0.1

        timer = Timer
            .publish(every: interval, on: .main, in: .common)
            .autoconnect()
            .sink { _ in
                self.handleTimerTick(interval: interval)
            }
    }

    private func stopTimer() {
        timer?.cancel()
        timer = nil
    }

    private func handleTimerTick(interval: CGFloat) {
        if !state.isRunning {
            return
        }

        self.elapsedTime += 0.1
        let newProgress = self.elapsedTime / self.duration
        self.progress = Progress(newProgress)

        let stateData = currentStateData()

        if self.progress.value < 1.0 {
            self.statePublisher.send(.running(setData: stateData))
        } else {
            self.statePublisher.send(.running(setData: stateData))
            stopTimer()
            finish()
        }
    }

    private func currentStateData() -> ElementSet.Data {
        ElementSet.Data.timed(
            timePassed: self.elapsedTime,
            timeRemaining: self.duration - self.elapsedTime,
            progress: self.progress
        )
    }
}