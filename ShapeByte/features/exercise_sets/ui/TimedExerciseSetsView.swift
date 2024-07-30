//
//  TimedSetView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 27.07.24.
//

import SwiftUI

struct TimedExerciseSetsView: View {
    @ObservedObject var viewModel: TimedExerciseSetsViewModel

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
                .h1PrimaryColor()

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
            .timedExerciseSetsViewModel(
                with: ExerciseSets(
                    sets: [
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5)
                    ]
                )
            )
    )
}
