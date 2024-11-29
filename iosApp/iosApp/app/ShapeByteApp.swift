import SwiftUI
import shared

@main
struct ShapeByteApp: App {
    @Env private var environment

    private var isRunningUnitTests: Bool {
        return environment.isRunningUnitTests
    }

    init() {
        // TODO: really need a use case that initializes the App !!!!!!!
        DPI.shared.setup(
            coroutineContextProvider: CoroutineContextProvider(),
            coroutineScopeProviding: CoroutineScopeProvider()
        )

        KoinInitializerKt.startKoin()
        DPI.shared.fileAssetLoader().setup(context: Bundle.main)
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
