import SwiftUI
import shared

@main
struct ShapeByteApp: App {

    private var isRunningUnitTests: Bool {
        return ProcessInfo.processInfo.environment["XCTestConfigurationFilePath"] != nil
    }

    init() {
        KoinInitializerKt.startKoin()
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
