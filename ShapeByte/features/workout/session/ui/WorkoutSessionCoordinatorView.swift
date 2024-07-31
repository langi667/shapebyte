//
//  WorkoutSessionCoordinatorView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
//

import SwiftUI

struct WorkoutSessionCoordinatorView: View {
    @StateObject private var coordinator = WorkoutSessionModule.workoutSessionCoordinator()

    var body: some View {
        // TODO: Navigation Stack and path
        ZStack {
            switch coordinator.state {
            
            case .runningTimedExercises:
                TimedExerciseSetsView(
                    viewModel: coordinator.timedExerciseSetsViewModel
                )
            }

        }.onAppear {
            coordinator.onViewAppeared()
        }
    }
}

#Preview {
    WorkoutSessionCoordinatorView()
}
