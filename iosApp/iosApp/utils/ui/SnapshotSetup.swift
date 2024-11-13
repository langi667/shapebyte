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
    func body(content: Content) -> some View {
        content
            .frame(width: CGFloat(iPhoneSizeBounds.medium.width))
            .fixedSize(horizontal: true, vertical: true)
    }
}

extension View {
    func snapshotSetup() -> some View {
        self.modifier(SnapshotSetup())
    }
}
