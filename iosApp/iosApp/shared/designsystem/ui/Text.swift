//
//  Label.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 29.07.24.
//

import SwiftUI

public extension Text {
    func title() -> some View {
        self.font(Theme.Fonts.title)
    }

    func subtitle() -> some View {
        self.font(Theme.Fonts.subtitle)
    }

    func h1() -> some View {
        self.font(Theme.Fonts.h1)
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

    func body() -> some View {
        self
            .font(Theme.Fonts.body)
    }

    func footnote() -> some View {
        self.font(Theme.Fonts.footnote)
    }
}
