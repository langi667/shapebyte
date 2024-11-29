//
//  BaseViewModelWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 09.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

// TODO: remove
@MainActor
protocol ViewModelWrapper: ViewModel {
    associatedtype BVW: BaseViewModel
    var wrapped: BVW { get }

    func observeState()
    func handleObservedState(_ observedState: UIState) async
}

extension ViewModelWrapper {
    /**
      Call this function when the view appears
     */
    func observeState() {
        Task {
            for await currState in wrapped.state {
                await handleObservedState(currState)
            }
        }
    }
}
