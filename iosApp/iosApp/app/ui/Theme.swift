//
//  Theme.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 10.10.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared

public struct Theme {

    public struct Spacings {
        static let XXS = CGFloat(themeProvider.spacing.xxs)
        // swiftlint:disable:next identifier_name
        static let XS = CGFloat(themeProvider.spacing.xxs)

        // swiftlint:disable:next identifier_name
        static let S = CGFloat(themeProvider.spacing.small)

        // swiftlint:disable:next identifier_name
        static let M = CGFloat(themeProvider.spacing.medium)

        // swiftlint:disable:next identifier_name
        static let L = CGFloat(themeProvider.spacing.large)

        // swiftlint:disable:next identifier_name
        static let XL = CGFloat(themeProvider.spacing.xLarge)

        static let XXL = CGFloat(themeProvider.spacing.xxLarge)
        static let XXXL = CGFloat(themeProvider.spacing.xxxLarge)
    }

    public struct Fonts {
        static let title: Font = themeProvider.fonts.title.font
        static let subtitle: Font = themeProvider.fonts.subtitle.font

        // swiftlint:disable:next identifier_name
        static let h1: Font = themeProvider.fonts.h1.font
        // swiftlint:disable:next identifier_name
        static let h2: Font = themeProvider.fonts.h2.font
        // swiftlint:disable:next identifier_name
        static let h3: Font = themeProvider.fonts.h3.font
        // swiftlint:disable:next identifier_name
        static let h4: Font = themeProvider.fonts.h4.font

        static let body: Font = themeProvider.fonts.body.font
        static let footnote: Font = themeProvider.fonts.footnote.font
    }

    public struct Colors {
        static let backgroundColor: Color = themeProvider.colors.background.defaultColor.color
        static let primaryColor: Color = themeProvider.colors.primary.defaultColor.color
        static let secondaryColor: Color = themeProvider.colors.secondary.defaultColor.color

        static let accentColor: Color = Color("AccentColor") // TODO: check if needed and where
        static let textLight: Color = Color("TextLight") // TODO: check if needed and where
        static let successColor: Color = Color("SuccessColor") // TODO: check if needed and where
    }

    fileprivate static let themeProvider = ThemeProvider() // TODO: Inject
}
