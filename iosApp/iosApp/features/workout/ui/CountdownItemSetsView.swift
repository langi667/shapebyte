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
            Text(viewModel.state.countdownText)
                .scaleEffect(viewModel.scale)
                .opacity(viewModel.alpha)

        }.onAppear {
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
