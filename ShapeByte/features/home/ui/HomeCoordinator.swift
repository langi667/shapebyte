//
//  HomeCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import Foundation

class HomeCoordinator: ViewModel, ObservableObject {
    enum State {
        case root
        case workout
    }

    @Published var state: State = .root
    @Published var presentWorkoutModal: Bool = false

    func onViewAppeared() { /* No op */}

    func onHomeRootEventreceived(event: HomeRootViewModel.Event) {
        switch event {
        case .currWorkoutScheduleEntrySelected:
            self.state = .workout
        }

        self.presentWorkoutModal = state == .workout
    }

}
