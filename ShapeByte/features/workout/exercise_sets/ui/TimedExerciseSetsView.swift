//
//  TimedSetView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import SwiftUI

struct TimedExerciseSetsView: View {
    
    @StateObject
    var viewModel = ExerciseSetsModule.timedExerciseSetsViewModel()

    var body: some View {
        ZStack {

            RotatingProgressRing(duration: $viewModel.currentSetDuration)

            SegmentedProgressRing(
                numberOfSegments: $viewModel.numberOfSets,
                progress: $viewModel.workoutProgress,
                fillColor: Theme.Colors.accentColor,
                backgroundColor: Theme.Colors.backgroundColor
            )
            .padding(35)
            Text("\(Int(viewModel.currentSetElapsedTime))")
                .titlePrimaryColor()

        }
        .padding(50)
        .onAppear {
            viewModel.startWorkout()
        }
    }
}

#Preview {
    TimedExerciseSetsView(
        viewModel: ExerciseSetsModule
            .timedExerciseSetsViewModel()
    )
}
