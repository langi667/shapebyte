//
//  ColorDescriptor+Native.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 11.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

extension ColorDescriptor {
    var color: Color {
        let color: Color
        switch onEnum(of: self) {
        case .hex:
            color = Color("none") // TODO: implement

        case .namedAsset(let namedAsset):
            color = Color(namedAsset.value)
        }

        return color
    }
}
