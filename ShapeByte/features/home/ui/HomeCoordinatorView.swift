//
//  HomeCoordinatorView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import SwiftUI

struct HomeCoordinatorView: View {
    @StateObject var coordinator: HomeCoordinator = HomeModule.homeCoordinator()
    @StateObject var homeRootViewModel: HomeRootViewModel = HomeModule.homeRootViewModel()

    var body: some View {
        ZStack {
            switch coordinator.state {
            case .root, .workout:
                HomeRootView(viewModel: homeRootViewModel)
                    .onReceive(homeRootViewModel.eventPublisher) { event in
                        coordinator.onHomeRootEventreceived(event: event)
                    }
                    .fullScreenCover(isPresented: $coordinator.presentWorkoutModal) {
                        ModalViewWrapper(
                            isPresented: $coordinator.presentWorkoutModal,
                            title: "Workout",
                            content: WorkoutSessionCoordinatorView()
                        )
                    }
            }
        }.onAppear {
            coordinator.onViewAppeared()
        }
    }
}

#Preview {
    HomeCoordinatorView()
}
