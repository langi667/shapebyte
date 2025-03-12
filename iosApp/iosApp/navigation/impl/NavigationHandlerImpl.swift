import Foundation
import SwiftUI
import Combine
import shared

protocol NavigationDestinationProviding {
    var destinations: AsyncStream<NavigationTarget> { get }
}

class NavigationHandlerImpl: Loggable, NavigationHandling {
    private(set) lazy var destinations: AsyncStream<NavigationTarget> = .init { continuation in
        self.continuation = continuation
    }

    private var continuation: AsyncStream<NavigationTarget>.Continuation?

    func navigate(to destination: NavigationTarget) {
        continuation?.yield(destination)
    }

    func handleNavigationRequest(request: NavigationRequest) {
        let target = SharedModule.shared.navigationRequestResolver().resolve(request: request)
        continuation?.yield( target)
    }

    init() {}
}
