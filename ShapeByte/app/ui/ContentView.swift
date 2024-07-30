//
//  ContentView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 23.07.24.
//

import SwiftUI

struct ContentView: View {
   @State
    var viewModel: TimedExerciseSetsViewModel = ExerciseSetsModule
        .timedExerciseSetsViewModel(
            with:
                ExerciseSets(
                    sets: [
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 2),
                        ExerciseSet.timed(duration: 3),
                        ExerciseSet.timed(duration: 5),
                        ExerciseSet.timed(duration: 3),
                        ExerciseSet.timed(duration: 10),
                        ExerciseSet.timed(duration: 8)
                    ]
                )
        )

    var body: some View {
        ZStack {
            Theme.Colors.backgroundColor
            TimedExerciseSetsView(viewModel: viewModel)
        }
    }
}

#Preview {
    ContentView()
}
