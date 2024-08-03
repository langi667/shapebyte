//
//  Background.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import SwiftUI

struct BackgroundView: View {
    var topOffset = Theme.Spacing.XXXL
    var radialOffset = Theme.Spacing.XXXL

    @State private var screenSize: CGSize = .zero

    var secondaryColor: Color = Color(
        red: 104 / 255,
        green: 187 / 255,
        blue: 193 / 255
    )

    var radialColor: Color = Color(
        red: 60 / 255,
        green: 112 / 255,
        blue: 138 / 255
    )

    var body: some View {
        ZStack {
            Rectangle()
                .fill(secondaryColor.gradient)
                .sizeReader(size: $screenSize)

            VStack(spacing: 0) {
                Spacer()
                    .frame(height: topOffset)

                Arc()
                    .fill(radialColor)
                    .frame(height: radialOffset)

                Rectangle()
                    .fill(radialColor)
            }
        }.ignoresSafeArea()
    }
}

#Preview {
    BackgroundView()
}
