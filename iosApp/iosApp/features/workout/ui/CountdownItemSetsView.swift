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
    private let logger = DPI.shared.logger

    var body: some View {
        ZStack {
            Self.contentView(
                countdownText: viewModel.countdownText,
                scale: viewModel.scale,
                alpha: viewModel.alpha
            )
        }.frame(maxWidth: .infinity, maxHeight: .infinity)
        .onAppear {
            viewModel.start(
                itemSets: [
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared),
                    ItemSet.Timed.forDuration(.seconds(1), item: None.shared)
                ]
            )
            viewModel.observeState()
        }
    }

    @ViewBuilder
    static func contentView(countdownText: String, scale: CGFloat, alpha: CGFloat) -> some View {
        ZStack {
            Text(countdownText)
                .title()
                .scaleEffect(scale)
                .opacity(alpha)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Theme.Colors.backgroundColor)
    }
}

struct CountdownItemSetsView_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
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
                    name: "State End",
                    state: .init(
                        countdownText: "1",
                        scale: 1,
                        animationDuration: 0,
                        alpha: 0.1
                    )
                )
            ],

            configure: { state in
                CountdownItemSetsView.contentView(
                    countdownText: state.countdownText,
                    scale: state.cgScale,
                    alpha: state.cgAlpha
                )
            }
        )
    }
}
