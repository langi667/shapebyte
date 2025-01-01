import SwiftUI
import shared

@main
struct ShapeByteApp: App {
    @Env private var environment

    private var isRunningUnitTests: Bool {
        return environment.isRunningUnitTests
    }

    init() {
        let platformDependencies = PlatformDependencyProvider(
            bundle: Bundle.main,
            coroutineScopeProviding: CoroutineScopeProvider(),
            coroutineContextProvider: CoroutineContextProvider()
        )

        DPI.shared
            .appInitializerUseCase()
            .invoke(platformDependencies: platformDependencies)
    }

    // TODO: Loading State view here
	var body: some Scene {
		WindowGroup {
            if isRunningUnitTests {
                unitTestView()
            } else {
                AppRootView()
            }
        }
	}

    @ViewBuilder
    func unitTestView() -> some View {
        ZStack {
            Text("Unit Tests running")
                .titleMedium()
        }
    }
}
