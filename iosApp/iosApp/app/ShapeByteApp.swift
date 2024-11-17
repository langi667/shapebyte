import SwiftUI
import shared

@main
struct ShapeByteApp: App {

    private var isRunningUnitTests: Bool {
        return ProcessInfo.processInfo.environment["XCTestConfigurationFilePath"] != nil
    }

    init() {
        KoinInitializerKt.startKoin()
        DPI.shared.assetLoader().setup(context: Bundle.main)
    }

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
                .title()
        }
    }
}
