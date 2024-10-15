//
//  CountdownItemSetsView.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 08.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import PreviewSnapshots
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
            if !isInPreview {
                viewModel.start(itemSets: [
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared)
                ]
                )
                viewModel.observeState()
            }
        }
    }
}

struct CountdownItemSetsView_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.sizeThatFits)
    }

    static var snapshots: PreviewSnapshots<CountdownItemSetsViewData> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "State Start",
                    state: .init(
                        countdownText: "3",
                        scale: 3,
                        animationDuration: 0, alpha: 1.0
                    )
                ),

                .init(
                    name: "State in Between",
                    state: .init(
                        countdownText: "2",
                        scale: 2,
                        animationDuration: 0,
                        alpha: 0.5
                    )
                ),

                .init(
                    name: "State end",
                    state: .init(
                        countdownText: "1",
                        scale: 1,
                        animationDuration: 0,
                        alpha: 0.1
                    )
                )
            ],

            configure: { state in
                CountdownItemSetsView(
                    viewModel: CountdownItemSetsViewModelWrapper(
                        data: state
                    )
                )
            }
        )
    }
}
