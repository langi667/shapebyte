//
//  WorkoutSessionCoordinatorView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import SwiftUI

struct WorkoutSessionCoordinatorView: View {
    @StateObject private var coordinator = WorkoutSessionModule.workoutSessionCoordinator()
    @StateObject private var timedItemSetsViewModel = ItemModule.timedItemSetsViewModel()
    @StateObject private var countdownItemSetsViewModel = ItemModule.countdownItemSetsViewModel()

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
    private func RunningStateView(group: ItemGroup) -> some View {
        if group.isTimedExercise {
            TimedView(group: group)
        } else if group.isCountdown {
            CountdownView(group: group)
        }
    }

    @ViewBuilder
    private func TimedView(group: ItemGroup) -> some View {
        TimedItemSetsView(
            viewModel: timedItemSetsViewModel
        )
        .onReceive(timedItemSetsViewModel.$state) { state in
            coordinator.onRunningSetsStateChanged(state)
        }
        .onReceive(coordinator.$state.filter { $0.isRunning }) { state in
            if case let .running(group) = state {
                timedItemSetsViewModel.startWith(group)
            }
        }
    }

    @ViewBuilder
    private func CountdownView(group: ItemGroup) -> some View {
         CountdownItemSetsView(
            viewModel: countdownItemSetsViewModel
        )
         .onReceive(countdownItemSetsViewModel.$state) { state in
             coordinator.onRunningSetsStateChanged(state)
         }
         .onReceive(coordinator.$state.filter { $0.isRunning }) { state in
             if case let .running(group) = state {
                 countdownItemSetsViewModel.startWith(group)
             }
         }
    }
}

#Preview {
    WorkoutSessionCoordinatorView()
}
