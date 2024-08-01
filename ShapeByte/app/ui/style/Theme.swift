//
//  Color.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import SwiftUI

struct Theme {
    // TODO: should be device screen size specific

    struct Dimenstions {
        static let XXS = CGFloat(4)
        static let XS = CGFloat(8)
        static let S = CGFloat(16)
        static let M = CGFloat(32)
        static let L = CGFloat(48)
        static let XL = CGFloat(64)
        static let XXL = CGFloat(128)
    }

    struct Colors {
        static let backgroundColor: Color = Color("BackgroundColor")
        static let primaryColor: Color = Color("PrimaryColor")
        static let accentColor: Color = Color("AccentColor")
    }

    struct Fonts {
        static let title: Font = .system(size: 64, weight: .black)
        static let h1: Font = .system(size: 28, weight: .black)
        static let h2: Font = .system(size: 24, weight: .bold)
        static let h3: Font = .system(size: 21, weight: .bold)
        static let h4: Font = .system(size: 19, weight: .bold)
    }
}
