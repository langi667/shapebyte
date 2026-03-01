import Foundation
import shared

@propertyWrapper
@MainActor
struct Device {
    var wrappedValue: DeviceInfoProvider {
        return SharedModule.shared.deviceInfoProvider()
    }
}
