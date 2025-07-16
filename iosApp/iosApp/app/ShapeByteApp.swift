import SwiftUI
import shared

@main
struct ShapeByteApp: App {
    @Env private var environment

    private var isRunningUnitTests: Bool {
        return environment.isRunningUnitTests
    }

    init() {
        // TODO: check if that needs to be launched when using unit tests
        let platformDependencies = PlatformDependencyProvider(
            bundle: Bundle.main,
            appInfo: appInfo(),
            appContextProvider: ContextProvider(appContext: self),
            appResourceProvider: AppResourceProvider()
        )

        SharedModule
            .shared
            .start(platformDependencies: platformDependencies)
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

    private func appInfo() -> AppInfo {
        let plistReader = InfoPlistReader()
        let retVal = AppInfo(
            packageName: plistReader.bundleIdentifier,
            versionName: plistReader.appVersion,
            versionCode: Int32(plistReader.buildVersion),
            debugMode: isDebugBuild()
        )

        return retVal
    }

    private func isDebugBuild() -> Bool {
        #if DEBUG
        return true
        #else
        return false
        #endif
    }
}
