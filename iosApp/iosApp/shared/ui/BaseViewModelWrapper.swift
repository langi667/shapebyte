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
open class BaseViewModelWrapper<T: BaseViewModel>: ObservableObject {
    let wrapped: T

    @Published
    var state: UIState

    init(wrapped: T) {
        self.wrapped = wrapped
        self.state = wrapped.state.value
    }

    /**
      Call from UI on onAppear to start observing the UI state
     */
    func observeState() {
        Task {
            for await currState in wrapped.state {
                await handleObservedState(currState)
            }
        }
    }

    /**
      Override to perform state observing
     */
    func handleObservedState(_ observedState: UIState) async {}
}
