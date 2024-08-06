//
//  TimedItemSetsView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import SwiftUI
import Combine

struct TimedItemSetsView: View {
    @ObservedObject
    var viewModel: TimedItemSetsViewModel

    private let padding = Theme.Spacing.M

    var body: some View {
        ZStack {
            BackgroundView()

            VStack(alignment: .center, spacing: padding) {
                ZStack {
                    RotatingProgressRing(
                        progress: $viewModel.ringProgress,
                        background: Theme.Colors.secondaryColor
                    )

                    if viewModel.numberOfSets > 1 {
                        SegmentedProgressRing(
                            numberOfSegments: $viewModel.numberOfSets,
                            progress: $viewModel.setsProgress,
                            fillColor: Theme.Colors.successColor,
                            backgroundColor: Theme.Colors.backgroundColor
                        )
                        .padding(padding + Theme.Spacing.XXS)
                    }

                    Text(viewModel.currentSetElapsedTimeText)
                        .title()
                }.padding(.top, padding * 2)

                Text(viewModel.currentItem.name).h1()
                Text(viewModel.descriptionText)
                    .body()
                    .multilineTextAlignment(.center)
                    .frame(minHeight: Theme.Spacing.XL)
                // TODO: should be dimension
            }
            .padding(padding)
        }
    }

    init(viewModel: TimedItemSetsViewModel) {
        self.viewModel = viewModel
    }
}

struct TimedItemSetsView_Previews: PreviewProvider {
    static let viewModel = ItemModule.timedItemSetsViewModel()

    static var previews: some View {
        TimedItemSetsView(
            viewModel: viewModel
        ).onAppear {
            viewModel.startWith(
                ItemGroup(
                    item: Exercise(name: "Push Up"),
                    itemSets: ItemSets(
                        sets: [
                            ItemSet.timed(item: Exercise(name: "Push Up"), duration: 8),
                            ItemSet.timed(item: Exercise(name: "Push Up"), duration: 5),
                            ItemSet.timed(item: Exercise(name: "Push Up"), duration: 6)
                        ]
                    )
                )
            )
        }
    }
}
