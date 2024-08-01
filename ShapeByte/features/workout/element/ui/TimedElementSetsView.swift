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
        ZStack {
            RotatingProgressRing(
                progress: $viewModel.ringProgress,
                background: Color.gray.opacity(0.3)
            )

            SegmentedProgressRing(
                numberOfSegments: $viewModel.numberOfSets,
                progress: $viewModel.setsProgress,
                fillColor: Theme.Colors.accentColor,
                backgroundColor: Theme.Colors.backgroundColor
            )
            .padding(padding + Theme.Dimenstions.XXS)
            Text(viewModel.currentSetElapsedTimeText)
                .titlePrimaryColor()
        }
        .padding( padding )
    }

    init(viewModel: TimedElementSetsViewModel) {
        self.viewModel = viewModel
    }
}

#Preview {
    TimedElementSetsView(
        viewModel: ElementModule
            .timedElementSetsViewModel()
    )
}
