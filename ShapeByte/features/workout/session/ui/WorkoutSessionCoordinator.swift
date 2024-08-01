//
//  WorkoutSessionCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import Foundation
import Combine

class WorkoutSessionCoordinator: ViewModel, ObservableObject {
    // TODO: add loading, paused, stopped, ...
    enum State {
        case idle
        case running(group: ElementGroup)
        case finished

        var isRunning: Bool {
            switch self {
            case .running:
                return true
            default:
                return false
            }
        }
    }

    let workoutSessionUseCase: CurrentWorkoutSessionUseCase

    @Published var state: State = .idle
    @Published var session: WorkoutSession = .empty
    @Published var currentSetsState: ElementSetsUIState = .idle

    private var cancellables = Set<AnyCancellable>()
    private let logger: Logging

    init(
        workoutSessionUseCase: CurrentWorkoutSessionUseCase,
        logger: Logging
    ) {
        self.workoutSessionUseCase = workoutSessionUseCase
        self.logger = logger

        $state.sink { newState in
            self.handleStateChanged(newState)
        }.store(in: &self.cancellables)
    }

    func onViewAppeared() {
        updateData()
        start()
    }

    func onRunningSetsStateChanged(_ state: ElementSetsUIState) {
        if state == .finished {
            self.continueRunning()
        }
    }

    private func updateData() {
        if let workoutSession = workoutSessionUseCase.invoke() {
            self.session = workoutSession
        } else {
            self.session = .empty // TODO: clear
        }
    }

    private func start() {
        if case .running = state {
            return
        }

        continueRunning()
    }

    private func continueRunning() {
        guard let nextGroup = self.session.nextElementGroup() else {
            finish()
            return
        }

        self.state = .running(group: nextGroup)
    }

    private func finish() {
        if case .finished = state {
            return
        }

        self.state = .finished
    }

    private func handleStateChanged(_ newState: State) {
        switch newState {
        case .idle:
            break
        case .running:
           break
        case .finished:
            break // TODO: implement
        }
    }

}
