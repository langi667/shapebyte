//
//  SnapshotSetup.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 13.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

struct SnapshotSetup: ViewModifier {
    // TODO: from mock
    static let iPhoneSize = CGSize(width: 393, height: 852) // iPhone 16 which is default for snapshot tests

    let height: CGFloat?
    func body(content: Content) -> some View {
        content
            .frame(
                width: CGFloat(Self.iPhoneSize.width),
                height: height
            )
            .fixedSize(horizontal: true, vertical: true)
    }
}

extension View {
    func snapshotSetup(height: CGFloat? = nil) -> some View {
        self.modifier(SnapshotSetup(height: height))
    }

    func snapshotSetupFullScreen() -> some View {
        self.modifier(SnapshotSetup(height: CGFloat(SnapshotSetup.iPhoneSize.height)))
    }
}
