import Foundation
import Combine
import shared

// TODO: needs to implement protocol from shared
// TODO: must come from shared
enum NavigationDestination: Hashable {
    case back
    case quickWorkout(workoutId: Int32)
}

// TODO: Factory
// TODO: provide by injection, not singleton
class NavigationHandler: Loggable, NavigationHandling {

    static let shared = NavigationHandler()

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
        } else if let request = request as? QuickWorkoutNavigationRequest {
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

    private init() {}
}
