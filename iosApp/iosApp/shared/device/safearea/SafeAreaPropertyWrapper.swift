import SwiftUI
import shared

@propertyWrapper
@MainActor
struct SafeAreaInfo {
    @Device
    private var deviceInfo

    var wrappedValue: EdgeInsets {
        let insets = deviceInfo.safeArea.insets
        return insets
    }
}
