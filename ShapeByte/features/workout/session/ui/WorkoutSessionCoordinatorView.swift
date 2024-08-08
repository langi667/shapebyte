//
//  WorkoutSessionCoordinatorView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 31.07.24.
//

import SwiftUI
import Combine

struct WorkoutSessionCoordinatorView: View {
    @StateObject private var coordinator = WorkoutSessionModule.workoutSessionCoordinator()
    @State private var cancellables: Set<AnyCancellable> = []

    var body: some View {
        // TODO: Navigation Stack and path
        ZStack {
            switch coordinator.state {
            case .idle:
                EmptyView() // TODO: show maybe loading or info view

            case .running(let viewType):
                RunningStateView(viewType: viewType)

            case .finished:
                WorkoutSessionFinishedScreen()
            }

        }.onAppear {
            coordinator.onViewAppeared()
        }
    }


    @ViewBuilder
    private func RunningStateView(viewType: WorkoutSessionCoordinator.ViewType) -> some View {
        switch viewType {
        case .none:
            EmptyView()

        case .timed:
            TimedItemSetsView(viewModel: coordinator.timedItemSetsViewModel)
                .onAppear {
                    coordinator.timedItemSetsViewModel.onViewAppeared()
                }
                .onDisappear {
                    coordinator.timedItemSetsViewModel.onViewDisappeared()
                }

        case .countdown:
            CountdownItemSetsView(viewModel: coordinator.countdownItemSetsViewModel)
                .onAppear {
                    coordinator.countdownItemSetsViewModel.onViewAppeared()
                }
                .onDisappear {
                    coordinator.countdownItemSetsViewModel.onViewDisappeared()
                }
        }
    }

}

#Preview {
    WorkoutSessionCoordinatorView()
}
