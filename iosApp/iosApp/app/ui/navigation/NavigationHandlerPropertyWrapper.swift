import shared

@MainActor
@propertyWrapper
struct NavigationHandler {
    private static let navigationHandler = NavigationHandlerImpl()

    var wrappedValue: NavigationHandling & NavigationDestinationProviding {
        Self.navigationHandler
    }
}
