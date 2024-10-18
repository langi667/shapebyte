//
//  Background.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import SwiftUI

struct BackgroundView: View {
    var topOffset = Theme.Spacings.XXXL.toDimension(max: Theme.Spacings.XXXL)
    var radialOffset = Theme.Spacings.XXXL.toDimension(max: Theme.Spacings.XXXL)

    @State private var screenSize: CGSize = .zero

    var secondaryColor: Color = Theme.Colors.secondaryColor
    var radialColor: Color = Theme.Colors.backgroundColor

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
