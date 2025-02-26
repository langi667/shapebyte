import shared

@MainActor
@propertyWrapper
struct NavigationHandler {
    private var navigationHandler = NavigationHandlerImpl()

    var wrappedValue: NavigationHandling & NavigationDestinationProviding {
        navigationHandler
    }
}
