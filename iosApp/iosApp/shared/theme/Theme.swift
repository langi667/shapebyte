//
//  Theme.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 10.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

public struct Theme {
    public static let spacings = Spacing(
        xTiny: 4,
        tiny: 8,
        small: 16,
        medium: 32,
        large: 48,
        xLarge: 64,
        xxLarge: 84,
        xxxLarge: 128
    )

    public static let dimensions = Dimension(
        xTiny: 16,
        tiny: 24,
        small: 36,
        medium: 72,
        large: 128,
        xLarge: 192,
        xxLarge: 256,
        xxxLarge: 320
    )

    public struct Fonts {
        static let displayLarge: Font = Font.system(size: 76, weight: .black)
        static let displayMedium: Font = Font.system(size: 45, weight: .bold)
        static let displaySmall: Font = Font.system(size: 40, weight: .bold)

        static let headlineLarge: Font = Font.system(size: 36, weight: .black)
        static let headlineMedium: Font = Font.system(size: 34, weight: .bold)
        static let headlineSmall: Font = Font.system(size: 32, weight: .medium)

        static let titleLarge: Font = Font.system(size: 28, weight: .bold)
        static let titleMedium: Font = Font.system(size: 24, weight: .bold)
        static let titleSmall: Font = Font.system(size: 21, weight: .bold)

        static let bodyLarge: Font = Font.system(size: 20, weight: .regular)
        static let bodyMedium: Font = Font.system(size: 19, weight: .regular)
        static let bodySmall: Font = Font.system(size: 18, weight: .regular)

        static let labelLarge: Font = Font.system(size: 16, weight: .bold)
        static let labelmedium: Font = Font.system(size: 14, weight: .bold)
        static let labelSmall: Font = Font.system(size: 14, weight: .semibold)
    }

    public struct Colors {
        static let backgroundColor: Color = Color("BackgroundColor")
        static let primaryColor: Color = Color("PrimaryColor")
        static let secondaryColor: Color = Color("SecondaryColor")
        static let inversePrimaryColor: Color = Color("InversePrimaryColor")
    }

    public struct Shapes {
        static let small: CGFloat = 4.0
        static let medium: CGFloat = 16.0
        static let large: CGFloat = 32
        static let xLarge: CGFloat = 48
    }

    // TODO: also add to Android
    public struct AnimationDuration {
        static let short: TimeInterval = 0.3
        static let medium: TimeInterval = 0.5
        static let long: TimeInterval = 0.75
    }
}
