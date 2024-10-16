//
//  BaseViewModelWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 09.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

@MainActor
protocol ViewModelWrapper: ObservableObject, Loggable {
    associatedtype BVW: BaseViewModel
    associatedtype DATA
    var wrapped: BVW { get }

    init(data: DATA)

    func observeState()
    func handleObservedState(_ observedState: UIState) async
    func handleViewData(_ viewData: CountdownItemSetsViewData)
}

extension ViewModelWrapper {
    func observeState() {
        Task {
            for await currState in wrapped.state {
                await handleObservedState(currState)
            }
        }
    }
}
