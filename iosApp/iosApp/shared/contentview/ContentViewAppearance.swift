import SwiftUI
import shared
import PreviewSnapshots

@MainActor
struct ContentViewAppearance {
    @SafeAreaInfo static var safeAreaInsets

    static var headerHeight: Double {
        (.dimensionLarge + safeAreaInsets.top / 2).toDimensionMax()
    }

    static var minimumHeaderHeight: Double {
        headerHeight * 0.6
    }
}
