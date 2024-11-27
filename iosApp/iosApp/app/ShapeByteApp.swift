import SwiftUI
import shared

@main
struct ShapeByteApp: App {
    @Env private var environment

    private var isRunningUnitTests: Bool {
        return environment.isRunningUnitTests
    }

    init() {
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
