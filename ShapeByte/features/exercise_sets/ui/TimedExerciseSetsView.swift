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
            AnimatedProgressRing(
                progress: $viewModel.currentSetProgress.wrappedValue
            )

            SegmentedProgressRing(
                numberOfSegments: $viewModel.numberOfSets,
                progress: $viewModel.workoutProgress,
                fillColor: Theme.Colors.accentColor,
                backgroundColor: Theme.Colors.backgroundColor
            )
            .padding(35)
        }.padding(50)
            .onAppear {
                viewModel.startWorkout()
            }
    }
}

#Preview {
    TimedExerciseSetsView(viewModel: TimedExerciseSetsViewModel(
        exerciseSets:
                .init(
                    sets: [
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5)
                    ]
                )
        )
    )
}
