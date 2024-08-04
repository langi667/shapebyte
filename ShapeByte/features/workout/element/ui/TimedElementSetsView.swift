//
//  TimedSetView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import SwiftUI
import Combine

struct TimedElementSetsView: View {
    @ObservedObject
    var viewModel: TimedElementSetsViewModel

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

                Text(viewModel.currentElement.name).h1()
                Text(viewModel.descriptionText)
                    .body()
                    .multilineTextAlignment(.center)
                    .frame(minHeight: Theme.Spacing.XL)
                // TODO: should be dimension
            }
            .padding(padding)
        }
    }

    init(viewModel: TimedElementSetsViewModel) {
        self.viewModel = viewModel
    }
}

struct TimedElementSetsView_Previews: PreviewProvider {
    static let viewModel = ElementModule.timedElementSetsViewModel()

    static var previews: some View {
        TimedElementSetsView(
            viewModel: viewModel
        ).onAppear {
            viewModel.startWith(
                ElementGroup(
                    element: Exercise(name: "Push Up"),
                    elementSets: ElementSets(
                        sets: [
                            ElementSet.timed(duration: 8),
                            ElementSet.timed(duration: 5),
                            ElementSet.timed(duration: 6)
                        ]
                    )
                )
            )
        }
    }
}
