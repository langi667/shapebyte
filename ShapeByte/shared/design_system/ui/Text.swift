//
//  Label.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import SwiftUI

extension View {
    func title() -> some View {
        self.font(Theme.Fonts.title)
    }

    func titlePrimaryColor() -> some View {
        self
            .title()
            .foregroundStyle(Theme.Colors.primaryColor)
    }

    func h1() -> some View {
        self.font(Theme.Fonts.h1)
    }

    func h1PrimaryColor() -> some View {
        self
            .h1()
            .foregroundStyle(Theme.Colors.primaryColor)
    }

    func h2() -> some View {
        self.font(Theme.Fonts.h2)
    }

    func h3() -> some View {
        self.font(Theme.Fonts.h3)
    }

    func h4() -> some View {
        self.font(Theme.Fonts.h4)
    }
}
