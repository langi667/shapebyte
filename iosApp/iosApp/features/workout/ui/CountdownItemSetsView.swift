//
//  CountdownItemSetsView.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct CountdownItemSetsView: View {
    @ObservedObject
    var viewModel: CountdownItemSetsViewModelWrapper
    private let logger = CommonMainModule.shared.logger

    var body: some View {
        ZStack {
            Text(viewModel.countdownText)
                .title()
                .scaleEffect(viewModel.scale)
                .opacity(viewModel.alpha)

        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Theme.Colors.backgroundColor)
        .onAppear {
            viewModel.start(itemSets: [
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
                ]
            )
            viewModel.observeState()
        }
    }
}

#Preview {
    CountdownItemSetsView(viewModel: CountdownItemSetsViewModelWrapper())
}
