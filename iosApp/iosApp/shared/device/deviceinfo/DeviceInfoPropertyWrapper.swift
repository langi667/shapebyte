import Foundation
import shared

@propertyWrapper
@MainActor
struct Device {
    @Env private var environment
    private let fakeDeviceInfo = DeviceInfoProviderFake()

    var wrappedValue: DeviceInfoProvider {
        if environment.isInPreview || environment.isRunningUnitTests {
            return fakeDeviceInfo // TODO: this needs to be retrieved by the DPI as well
        } else {
            return SharedModule.shared.deviceInfoProvider()
        }
    }
}
