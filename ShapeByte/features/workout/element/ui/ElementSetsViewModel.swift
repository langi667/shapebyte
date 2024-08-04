//
//  ElementSetsViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 04.08.24.
//

import Combine
import SwiftUI

open class ElementSetsViewModel: ViewModel, ObservableObject {
    @Published var state: ElementSetsUIState = .idle
    @Published var group: ElementGroup = .empty
    @Published var numberOfSets: Int = 0
    @Published var currentElement: any Element = Exercise.none

    let setsHandler: ElementSetsHandler
    let logger: Logging
    var cancelables: Set<AnyCancellable> = Set<AnyCancellable>()

    init(
        logger: Logging,
        setsHandler: ElementSetsHandler
    ) {
        self.logger = logger
        self.setsHandler = setsHandler
    }

    func onViewAppeared() {
        /* Override for specific handling of the UI */
    }

    func handleUIStateReceived( _ state: ElementSetsUIState ) {
        /* Override to perform specific actions for new UI state */
    }

    func startWith(_ group: ElementGroup) {
        stop()

        self.group = group
        self.currentElement = group.element

        self.numberOfSets = group.count

        start()
    }

    func start() {
        stop()
        self.numberOfSets = group.count

        setsHandler.start(
            sets: group.elementSets.sets
        )

        setsHandler.$state.sink { state in
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
