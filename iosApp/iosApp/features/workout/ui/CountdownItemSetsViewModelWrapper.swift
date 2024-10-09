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
    var scale: CGFloat = 1

    @Published
    var alpha: CGFloat = 1

    @Published
    var countdownText: String = ""

    init() {
        let wrapped = CommonMainModule.shared.countdownItemSetsViewModel
        super.init(wrapped: wrapped)
    }

    func start(itemSets: [ItemSet.Timed]) {
        wrapped.start(itemSets: itemSets)
    }

    override func handleObservedState(_ state: UIState) async {
        self.state = state
        self.scale = 1
        self.alpha = 1

        guard let viewData: CountdownItemSetsViewData = state.viewData() else {
            wrapped.logE(message: "Unable to get CountdownItemSetsViewData from \(state)") // TODO: interface must be implemented by BaseViewModelWrapper
            return
        }

        handleViewData(viewData)
    }

    private func handleViewData(_ stateData: CountdownItemSetsViewData) {
        self.countdownText = stateData.countdownText

        withAnimation(.easeInOut(duration: 1)) {
            self.scale = CGFloat(stateData.scale)
            self.alpha = CGFloat(stateData.alpha)
        }
    }
}
