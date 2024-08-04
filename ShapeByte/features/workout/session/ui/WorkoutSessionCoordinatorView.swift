//
//  WorkoutSessionCoordinatorView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import SwiftUI

struct WorkoutSessionCoordinatorView: View {
    @StateObject private var coordinator = WorkoutSessionModule.workoutSessionCoordinator()
    @StateObject private var timedElementSetsViewModel = ElementModule.timedElementSetsViewModel()
    @StateObject private var countdownElementSetsViewModel = ElementModule.countdownElementSetsViewModel()

    var body: some View {
        // TODO: Navigation Stack and path
        ZStack {
            switch coordinator.state {
            case .idle:
                EmptyView() // TODO: show maybe loading or info view

            case .running(let group):
                RunningStateView(group: group)

            case .finished:
                WorkoutSessionFinishedScreen()
            }

        }.onAppear {
            coordinator.onViewAppeared()
        }
    }

    @ViewBuilder
    private func RunningStateView(group: ElementGroup) -> some View {
        if group.isTimedExercise {
            TimedView(group: group)
        } else if group.isCountdown {
            CountdownView(group: group)
        }
    }

    @ViewBuilder
    private func TimedView(group: ElementGroup) -> some View {
        TimedElementSetsView(
            viewModel: timedElementSetsViewModel
        )
        .onReceive(timedElementSetsViewModel.$state) { state in
            coordinator.onRunningSetsStateChanged(state)
        }
        .onReceive(coordinator.$state.filter { $0.isRunning }) { state in
            if case let .running(group) = state {
                timedElementSetsViewModel.startWith(group)
            }
        }
    }

    @ViewBuilder
    private func CountdownView(group: ElementGroup) -> some View {
         CountdownElementSetsView(
            viewModel: countdownElementSetsViewModel
        )
         .onReceive(countdownElementSetsViewModel.$state) { state in
             coordinator.onRunningSetsStateChanged(state)
         }
         .onReceive(coordinator.$state.filter { $0.isRunning }) { state in
             if case let .running(group) = state {
                 countdownElementSetsViewModel.startWith(group)
             }
         }
    }
}

#Preview {
    WorkoutSessionCoordinatorView()
}
