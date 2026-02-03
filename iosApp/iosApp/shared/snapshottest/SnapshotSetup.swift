import SwiftUI
import shared

struct SnapshotSetup: ViewModifier {
    @Device
    fileprivate var device // returns mocked size, see DeviceInfoProviderFake.ios.kt

    let height: CGFloat?
    func body(content: Content) -> some View {
        content
            .frame(
                width: CGFloat(device.screenSize.width),
                height: height
            )
            .fixedSize(horizontal: true, vertical: true)
    }

    func makeFullScreen() -> SnapshotSetup {
        return SnapshotSetup(height: CGFloat(device.screenSize.height))
    }
}

extension View {
    func snapshotSetup(height: CGFloat? = nil) -> some View {
        self.modifier(SnapshotSetup(height: height))
    }

    func snapshotSetupFullScreen() -> some View {
        self.modifier(SnapshotSetup(height: nil).makeFullScreen())
    }
}
