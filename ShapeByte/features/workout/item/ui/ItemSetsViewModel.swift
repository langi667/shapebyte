//
//  ItemSetsViewModel.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Combine
import SwiftUI

open class ItemSetsViewModel: ViewModel, ObservableObject {
    @Published var state: ItemSetsUIState = .idle
    @Published var group: ItemGroup = .empty
    @Published var numberOfSets: Int = 0
    @Published var currentItem: any Item = Exercise.none

    let setsHandler: ItemSetsHandler
    let logger: Logging
    var cancelables: Set<AnyCancellable> = Set<AnyCancellable>()

    init(
        logger: Logging,
        setsHandler: ItemSetsHandler
    ) {
        self.logger = logger
        self.setsHandler = setsHandler
    }

    func onViewAppeared() {
        /* Override for specific handling of the UI */
    }

    func handleUIStateReceived( _ state: ItemSetsUIState ) {
        /* Override to perform specific actions for new UI state */
    }

    func startWith(_ group: ItemGroup) {
        stop()

        self.group = group
        self.currentItem = group.item
        self.numberOfSets = group.count

        start()
    }

    func start() {
        stop()
        self.numberOfSets = group.count

        setsHandler.start(
            sets: group.itemSets.sets
        )

        setsHandler.$state.sink { state in
            self.handleCoordinatorStateChanged(state)
        }.store(in: &cancelables)
    }

    func stop() {
        cancelables.removeAll()
    }

    private func handleCoordinatorStateChanged(_ state: ItemSetsUIState) {
        handleUIStateReceived(state)
        self.state = state
    }
}
