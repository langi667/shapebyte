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
    private var viewAppeared: Bool = false

    init(
        logger: Logging,
        setsHandler: ItemSetsHandler
    ) {
        self.logger = logger
        self.setsHandler = setsHandler
    }

    func onViewAppeared() {
        viewAppeared = true
        start()
    }

    func onViewDisappeared() {
        viewAppeared = false
        stop()
    }

    /**
     Override to perform specific actions for new UI state 
     return true, if the state was handled. In case the state is set. If false is returned,, the specific implementation has to take over and set the state when it's required
     */

    func handleUIStateReceived( _ state: ItemSetsUIState ) -> Bool {
        return true
    }

    func startWith(_ group: ItemGroup) {
        if group == self.group {
            return
        }

        stop()

        self.group = group
        self.currentItem = group.item
        self.numberOfSets = group.count

        if viewAppeared {
            start()
        }
    }

    func start() {
        if self.group.isEmpty {
            return
        }

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
        if handleUIStateReceived(state) {
            self.state = state
        }

    }
}
