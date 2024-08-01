//
//  WorkoutSessionCoordinatorView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 31.07.24.
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

            case .finished:
                EmptyView() // TODO: show finished view

            case .running(let group):
                handleStateRunning(group: group)
            }

        }.onAppear {
            coordinator.onViewAppeared()
        }
    }

    @ViewBuilder
    private func handleStateRunning(group: ElementGroup) -> some View {
        if group.isTimedExercise {
            timedElementSetsView(group: group)
        } else if group.isCountdown {
            countdownView(group: group)
        }
    }

    @ViewBuilder
    private func timedElementSetsView(group: ElementGroup) -> some View {
        TimedElementSetsView(
            viewModel: timedElementSetsViewModel
        )
        .onReceive(timedElementSetsViewModel.$state) { state in
            coordinator.onRunningSetsStateChanged(state)
        }
        .onReceive(coordinator.$state.filter { $0.isRunning }) { state in
            if case let .running(group) = state {
                timedElementSetsViewModel.startWith(group.sets)
            }
        }
    }

    @ViewBuilder
    private func countdownView(group: ElementGroup) -> some View {
         CountdownElementSetsView(
            viewModel: countdownElementSetsViewModel
        )
         .onReceive(countdownElementSetsViewModel.$state) { state in
             coordinator.onRunningSetsStateChanged(state)
         }
         .onReceive(coordinator.$state.filter { $0.isRunning }) { state in
             if case let .running(group) = state {
                 countdownElementSetsViewModel.startWith(group.sets)
             }
         }
    }
}

#Preview {
    WorkoutSessionCoordinatorView()
}
