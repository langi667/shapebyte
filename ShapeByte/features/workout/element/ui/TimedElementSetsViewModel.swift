//
//  TimedSetViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import Combine
import SwiftUI

open class ElementSetsViewModel: ViewModel, ObservableObject {
    @Published var state: ElementSetsUIState = .idle
    @Published var sets: ElementSets = .empty
    @Published var numberOfSets: Int = 0

    let setsCoordinator: ElementSetsCoordinator
    let logger: Logging
    var cancelables: Set<AnyCancellable> = Set<AnyCancellable>()

    init(
        logger: Logging,
        setsCoordinator: ElementSetsCoordinator
    ) {
        self.logger = logger
        self.setsCoordinator = setsCoordinator
    }

    func onViewAppeared() {
        /* Override for specific handling of the UI */
    }

    func handleUIStateReceived( _ state: ElementSetsUIState ) {
        /* Override to perform specific actions for new UI state */
    }

    func startWith(_ sets: ElementSets) {
        stop()

        self.sets = sets
        self.numberOfSets = sets.count

        start()
    }

    func start() {
        stop()
        self.numberOfSets = sets.count

        setsCoordinator.start(
            sets: sets.sets
        )

        setsCoordinator.$state.sink { state in
            self.handleCoordinatorStateChanged(state)
        }.store(in: &cancelables)

    }

    func stop() {
        cancelables.removeAll()
    }

    private func handleCoordinatorStateChanged(_ state: ElementSetsUIState) {
        handleUIStateReceived(state)
        self.state = state
    }
}

class TimedElementSetsViewModel: ElementSetsViewModel {
    @Published var currentSetProgress: Progress = .zero
    @Published var currentSetElapsedTime: Int = 1

    @Published var setsProgress: Progress = Progress(0)
    @Published var currentSetIndex: Int = -1
    @Published var currentSetElapsedTimeText: String = ""
    @Published var ringProgress: CGFloat = 0

    override func handleUIStateReceived(_ state: ElementSetsUIState) {

        switch state {
        case .idle:
            break

        case .running(let setIndex, _, let currentSetProgress, let totalProgress):
            guard let setDuration = self
                .sets
                .elementSetFor(index: setIndex)?.duration else {
                return
            }

            let lastSetIndex = currentSetIndex
            if lastSetIndex != setIndex {
                self.currentSetIndex = setIndex

                ringProgress = 0
                withAnimation( .linear(duration: setDuration) ) {
                    ringProgress = 1
                }
            }

            let elapsed = ((1.0 - currentSetProgress.value) * setDuration)

            if elapsed > 0 {
                currentSetElapsedTime = Int(elapsed.rounded(.up))
                self.currentSetElapsedTimeText = DurationFormatter.secondsToString(currentSetElapsedTime)
            }

        self.currentSetProgress = currentSetProgress
        self.setsProgress = totalProgress

    case .paused:
        break // TODO: handle
    case .finished:
        break

    }
}
}
