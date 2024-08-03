//
//  MainCoordinator.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import Foundation

class AppCoordinator: ViewModel, ObservableObject {
    enum State {
        case home
    }

    @Published var state: State = .home

    func onViewAppeared() {

    }
}
