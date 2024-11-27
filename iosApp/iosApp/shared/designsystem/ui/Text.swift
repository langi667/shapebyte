//
//  Label.swift
//  ShapeByte
//
//  Created by Lang, Stefan [ShapeByte Tech] on 29.07.24.
//

import SwiftUI

public extension Text {
    func titleLarge() -> some View {
        self.font(Theme.Fonts.titleLarge)
    }

    func titleMedium() -> some View {
        self.font(Theme.Fonts.titleMedium)
    }

    func titleSmall() -> some View {
        self.font(Theme.Fonts.titleSmall)
    }

    func headlineLarge() -> some View {
        self.font(Theme.Fonts.headlineLarge)
    }

    func headlineMedium() -> some View {
        self.font(Theme.Fonts.headlineMedium)
    }

    func headlineSmall() -> some View {
        self.font(Theme.Fonts.headlineSmall)
    }

    func labelLarge() -> some View {
        self.font(Theme.Fonts.labelLarge)
    }

    func labelMedium() -> some View {
        self.font(Theme.Fonts.labelLarge)
    }

    func labelSmall() -> some View {
        self.font(Theme.Fonts.labelSmall)
    }

    func bodyLarge() -> some View {
        self.font(Theme.Fonts.bodyMedium)
    }

    func bodyMedium() -> some View {
        self.font(Theme.Fonts.bodyMedium)
    }

    func bodySmall() -> some View {
        self.font(Theme.Fonts.bodySmall)
    }
}

// TODO: Previews
