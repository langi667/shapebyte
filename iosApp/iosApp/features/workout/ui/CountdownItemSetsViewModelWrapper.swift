//
//  CountdownItemSetsViewModelWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

// TODO: separate file
@MainActor
open class ViewModelWrapper: ObservableObject {
    func observeState() {
        Task {
            await onObserveState()
        }
    }

    func onObserveState() async {}
}

class CountdownItemSetsViewModelWrapper: ViewModelWrapper {
    private let wrapped: CountdownItemSetsViewModel

    @Published
    var state: CountdownItemSetsViewModel.UIState

    @Published
    var scale: CGFloat = 1

    @Published
    var alpha: CGFloat = 1

    override init() {
        self.wrapped = CommonMainModule.shared.countdownItemSetsViewModel
        self.state = wrapped.state.value
    }

    func start(itemSets: [ItemSet.Timed]) {
        wrapped.start(itemSets: itemSets)
    }

    override func onObserveState() async {
        for await currState in wrapped.state {
            self.state = currState
            self.scale = 1
            self.alpha = 1

            withAnimation(.easeInOut(duration: 1)) {
                self.scale = CGFloat(currState.scale)
                self.alpha = CGFloat(currState.alpha)
            }
        }
    }
}

