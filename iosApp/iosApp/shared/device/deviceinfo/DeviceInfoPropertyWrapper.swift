import Foundation
import shared

@propertyWrapper
@MainActor
struct Device {
    @Env private var environment
    private let deviceInfoMock = DeviceInfoProviderFake()

    var wrappedValue: DeviceInfoProvider {
        if environment.isInPreview || environment.isRunningUnitTests {
            return deviceInfoMock // TODO: this needs to be retrieved by the DPI as well
        } else {
            return SharedModule.shared.deviceInfoProvider()
        }
    }
}
