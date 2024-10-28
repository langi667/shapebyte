//
//  CountdownItemSetsViewModelWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

@MainActor
class CountdownItemSetsViewModelWrapper: ViewModelWrapper {
    var wrapped: CountdownItemSetsViewModel

    @Published
    var state: UIState = UIState.Idle.shared

    @Published
    var scale: CGFloat = 1

    @Published
    var alpha: CGFloat = 1

    @Published
    var countdownText: String = ""

    init(wrapped: CountdownItemSetsViewModel = DPI.shared.countdownItemSetsViewModel()) {
        self.wrapped = wrapped
    }

    func start(itemSets: [ItemSetTimedSeconds]) {
        wrapped.start(itemSets: itemSets)
    }

    func handleObservedState(_ state: UIState) async {
        self.state = state
        self.scale = 1
        self.alpha = 1

        guard let viewData: CountdownItemSetsViewData = state.viewData() else {
            logE(message: "Unable to get CountdownItemSetsViewData from \(state)")
            return
        }

        handleViewData(viewData)
    }

    func handleViewData(_ viewData: CountdownItemSetsViewData) {
        self.countdownText = viewData.countdownText

        withAnimation(.easeInOut(duration: 1)) {
            self.scale = viewData.cgScale
            self.alpha = viewData.cgAlpha
        }
    }

    func onViewAppeared() { }

    func onViewDisappeared() { }
}

extension CountdownItemSetsViewData {
    var cgScale: CGFloat { CGFloat(self.scale) }
    var cgAlpha: CGFloat { CGFloat(self.alpha) }
}
