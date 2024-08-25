//
//  HomeCoordinatorView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
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
                        coordinator.onHomeRootEventReceived(event: event)
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
