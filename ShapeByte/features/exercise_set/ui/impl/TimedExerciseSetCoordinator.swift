//
//  TimedSetCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 28.07.24.
//

import Foundation
import Combine

class TimedExerciseSetCoordinator: ExerciseSetCoordinating {
    fileprivate(set) var statePublisher: PassthroughSubject<ExerciseSetState, Never>
    = PassthroughSubject<ExerciseSetState, Never>()

    fileprivate(set) var set: ExerciseSet?

    private var duration: TimeInterval = 5
    private var timer: AnyCancellable?
    private var elapsedTime: TimeInterval = 0
    private var progress: Progress = .zero

    private var state: ExerciseSetState = .idle

    func start(set: ExerciseSet) {
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

    private func currentStateData() -> ExerciseSetStateData {
        ExerciseSetStateData.timed(
            timePassed: self.elapsedTime,
            timeRemaining: self.duration - self.elapsedTime,
            progress: self.progress
        )
    }
}
