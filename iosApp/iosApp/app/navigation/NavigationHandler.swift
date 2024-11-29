//
//  NavigationHandler.swift
//  iosApp
//
//  Created by Stefan Lang on 09.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import Combine

// TODO: needs to implement protocol from shared
// TODO: must come from shared
enum NavigationDestination: Hashable {
    case quickWorkout(workoutId: Int32)
}

// TODO: Factory
// TODO: provide by injection, not singleton
class NavigationHandler: Loggable {
    static let shared = NavigationHandler()

    private(set) lazy var destinations: AsyncStream<NavigationDestination> = .init { continuation in
        self.continuation = continuation
    }

    private var continuation: AsyncStream<NavigationDestination>.Continuation?

    func navigate(to destination: NavigationDestination) {
        continuation?.yield(destination)
    }

    func navigateToQuickWorkout(workoutId: Int32) {
        if let continuation = self.continuation {
            continuation.yield(.quickWorkout(workoutId: workoutId))
        } else {
            logE(message: "Continuation in NavigationHandler is nil")
        }
    }

    private init() {}
}
