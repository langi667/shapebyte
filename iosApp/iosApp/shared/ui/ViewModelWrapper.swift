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
protocol ViewModelWrapper: ViewModel {
    associatedtype BVW: BaseViewModel
    var wrapped: BVW { get }

    func observeState()
    func handleObservedState(_ observedState: UIState) async
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
