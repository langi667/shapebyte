import Foundation
import Combine
import shared

// TODO: must come from shared
enum NavigationDestination: Hashable {
    case back
    case quickWorkout(workoutId: Int32)
}

protocol NavigationDestinationProviding {
    var destinations: AsyncStream<NavigationDestination> { get }
}

class NavigationHandlerImpl: Loggable, NavigationHandling, NavigationDestinationProviding {
    private(set) lazy var destinations: AsyncStream<NavigationDestination> = .init { continuation in
        self.continuation = continuation
    }

    private var continuation: AsyncStream<NavigationDestination>.Continuation?

    func navigate(to destination: NavigationDestination) {
        continuation?.yield(destination)
    }

    func handleNavigationRequest(request: any NavigationRequest) {
        if request is NavigationRequestBack {
            continuation?.yield(.back)
        } else if let request = request as? NavigationRequestQuickWorkout {
            navigateToQuickWorkout(workoutId: request.workoutId)
        } else {
            logE(message: "unhandled request: \(request)")
        }
    }

    func navigateToQuickWorkout(workoutId: Int32) {
        if let continuation = self.continuation {
            continuation.yield(.quickWorkout(workoutId: workoutId))
        } else {
            logE(message: "Continuation in NavigationHandler is nil")
        }
    }

    init() {}
}
