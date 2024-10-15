//
//  CountdownItemSetsViewModelWrapper.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

// TODO: Test
@MainActor
class CountdownItemSetsViewModelWrapper: ViewModelWrapper {
    lazy var wrapped: some CountdownItemSetsViewModel = CommonMainModule.shared.countdownItemSetsViewModel

    @Published
    var state: UIState = .Idle.shared

    @Published
    var scale: CGFloat = 1

    @Published
    var alpha: CGFloat = 1

    @Published
    var countdownText: String = ""

    required convenience init(
        data: CountdownItemSetsViewData
    ) {
        self.init()
        handleViewData(data)
    }

    func start(itemSets: [ItemSet.Timed]) {
        wrapped.start(itemSets: itemSets)
    }

    func handleObservedState(_ state: UIState) async {
        self.state = state
        self.scale = 1
        self.alpha = 1

        guard let viewData: CountdownItemSetsViewData = state.viewData() else {
            // TODO: interface must be implemented by BaseViewModelWrapper
            wrapped.logE(message: "Unable to get CountdownItemSetsViewData from \(state)")
            return
        }

        handleViewData(viewData)
    }

    func handleViewData(_ viewData: CountdownItemSetsViewData) {
        self.countdownText = viewData.countdownText

        withAnimation(.easeInOut(duration: 1)) {
            self.scale = CGFloat(viewData.scale)
            self.alpha = CGFloat(viewData.alpha)
        }
    }
}
