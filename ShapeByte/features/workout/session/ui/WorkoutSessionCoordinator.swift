//
//  WorkoutSessionCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import Foundation
import Combine

class WorkoutSessionCoordinator: ViewModel, ObservableObject {
    // TODO: add loading, paused, stopped, ...

    enum ViewType {
        case none
        case timed
        case countdown
    }

    enum State {
        case idle
        case running(viewType: ViewType)
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
    @Published var currentSetsState: ItemSetsUIState = .idle

    let timedItemSetsViewModel: TimedItemSetsViewModel
    let countdownItemSetsViewModel: CountdownItemSetsViewModel

    private var cancellables = Set<AnyCancellable>()
    private var vmCancelables = Set<AnyCancellable>()
    private let logger: Logging

    init(
        timedItemSetsViewModel: TimedItemSetsViewModel,
        countdownItemSetsViewModel: CountdownItemSetsViewModel,
        workoutSessionUseCase: CurrentWorkoutSessionUseCase,
        logger: Logging
    ) {
        self.timedItemSetsViewModel = timedItemSetsViewModel
        self.countdownItemSetsViewModel = countdownItemSetsViewModel
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

    func onViewDisappeared() {}

    func onRunningSetsStateChanged(_ state: ItemSetsUIState) {
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

    private func viewTypeFor(group: ItemGroup) -> (ViewType, ItemSetsViewModel?) {
        if group.isTimedExercise {
            return (.timed, timedItemSetsViewModel)
        }
        else if group.isCountdown {
            return (.countdown, countdownItemSetsViewModel)
        }
        else {
            logger.logWarning("Unknown item group: \(group)")
            return (.none, nil)
        }
    }

    private func start() {
        if case .running = state {
            return
        }

        continueRunning()
    }

    private func continueRunning() {
        vmCancelables.forEach{$0.cancel()}
        vmCancelables.removeAll()

        guard let nextGroup = self.session.nextItemGroup() else {
            finish()
            return
        }

        let (viewType, viewModel) = viewTypeFor(group: nextGroup)

        viewModel?.$state.sink { state in
            self.onRunningSetsStateChanged(state)
        }.store(in: &self.vmCancelables)

        viewModel?.startWith(nextGroup)
        self.state = .running(viewType: viewType)
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
