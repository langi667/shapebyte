import SwiftUI
import shared
import PreviewSnapshots

@MainActor
struct ContentViewAppearance {
    @SafeAreaInfo static var safeAreaInsets

    static var headerHeight: CGFloat {
        (Theme.dimensions.large + safeAreaInsets.top / 2).toDimensionMax()
    }

    static var minimumHeaderHeight: CGFloat {
        headerHeight * 0.6
    }
}
