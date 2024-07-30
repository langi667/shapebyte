//
//  Color.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 27.07.24.
//

import SwiftUI

struct Theme {
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
