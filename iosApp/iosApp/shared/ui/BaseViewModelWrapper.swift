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

    init(wrapped: T) {
        self.wrapped = wrapped
    }

    /**
      Call from UI on onAppear to start observing the UI state
     */
    func observeState() {
        Task {
            await onObserveState()
        }
    }

    /**
      Override to perform state observing
     */
    func onObserveState() async {}
}
