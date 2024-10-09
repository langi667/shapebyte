//
//  CountdownItemSetsViewModelWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

class CountdownItemSetsViewModelWrapper: BaseViewModelWrapper<CountdownItemSetsViewModel> {
    @Published
    var state: CountdownItemSetsViewModel.UIState

    @Published
    var scale: CGFloat = 1

    @Published
    var alpha: CGFloat = 1

    init() {
        let wrapped = CommonMainModule.shared.countdownItemSetsViewModel
        self.state = wrapped.state.value

        super.init(wrapped: wrapped)
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

