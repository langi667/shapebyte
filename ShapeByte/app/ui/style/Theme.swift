//
//  Color.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 27.07.24.
//

import SwiftUI

public struct Theme {

    public struct Spacing {
        static let XXS = CGFloat(4)
        static let XS = CGFloat(8)
        static let S = CGFloat(16)
        
        static let M = CGFloat(32)
        static let L = CGFloat(48)
        static let XL = CGFloat(64)
        
        static let XXL = CGFloat(84)
        static let XXXL = CGFloat(128)
    }

    public struct Colors {
        static let backgroundColor: Color = Color("BackgroundColor")
        static let secondaryColor: Color = Color("SecondaryColor")
        static let primaryColor: Color = Color("PrimaryColor")
        
        static let accentColor: Color = Color("AccentColor")
        static let textLight: Color = Color("TextLight")
        static let successColor: Color = Color("SuccessColor")
    }

    public struct Fonts {
        static let title: Font = .system(size: 64, weight: .black)
        static let subtitle: Font = .system(size: 32, weight: .light)

        static let h1: Font = .system(size: 28, weight: .black)
        static let h2: Font = .system(size: 24, weight: .bold)
        static let h3: Font = .system(size: 21, weight: .bold)
        static let h4: Font = .system(size: 19, weight: .bold)

        static let body: Font = .system(size: 19, weight: .regular)
        static let footnote: Font = .system(size: 16, weight: .regular)
    }
}
