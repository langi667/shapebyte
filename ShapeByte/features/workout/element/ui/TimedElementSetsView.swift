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

    private let padding = Theme.Dimenstions.M

    var body: some View {
        VStack(alignment: .center ) {

            HStack {
                Spacer()
                Text(viewModel.setCountProgress)
                    .h1().foregroundStyle(Color.gray.opacity(0.7))
            }.frame(minHeight: Theme.Dimenstions.L)

            Spacer()
            ZStack {
                RotatingProgressRing(
                    progress: $viewModel.ringProgress,
                    background: Color.gray.opacity(0.3)
                )

                if viewModel.numberOfSets > 1 {
                    SegmentedProgressRing(
                        numberOfSegments: $viewModel.numberOfSets,
                        progress: $viewModel.setsProgress,
                        fillColor: Theme.Colors.accentColor,
                        backgroundColor: Theme.Colors.backgroundColor
                    )
                    .padding(padding + Theme.Dimenstions.XXS)
                }

                Text(viewModel.currentSetElapsedTimeText)
                    .titlePrimaryColor()
            }

            Spacer().frame(height: Theme.Dimenstions.M)
            Text(viewModel.currentElement.name).h1PrimaryColor()
            Spacer()
        }
        .padding( padding )
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
