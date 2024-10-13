//
//  FontDescriptor+Fonts.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 10.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension FontDescriptor {
    var font: Font {
        let font: Font
        switch onEnum(of: self) {

        case .system:
            font = .system(
                size: CGFloat(self.size),
                weight: self.weight.value
            )
        }

        return font
    }
}
