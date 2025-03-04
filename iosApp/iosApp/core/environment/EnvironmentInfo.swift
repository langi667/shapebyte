import Foundation

struct EnvironmentInfo {
    var isRunningUnitTests: Bool {
        return ProcessInfo.processInfo.environment["XCTestConfigurationFilePath"] != nil
    }

    var isInPreview: Bool {
        return ProcessInfo.processInfo.environment["XCODE_RUNNING_FOR_PREVIEWS"] == "1"
    }
}

@propertyWrapper
struct Env {
    var wrappedValue: EnvironmentInfo = .init()
}
