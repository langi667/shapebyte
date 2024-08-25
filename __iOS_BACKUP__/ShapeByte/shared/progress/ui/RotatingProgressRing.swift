//
//  RotatingProgressRing.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 30.07.24.
//

import Foundation
import SwiftUI

struct RotatingProgressRing: View {
    @Binding var progress: CGFloat

    let primaryColor: Color = Theme.Colors.accentColor
    let lineWidth: CGFloat = 20
    let background: Color

    @State private var viewSize: CGSize = .zero

    var body: some View {
        ZStack {
            Circle()
                .stroke(background, lineWidth: self.lineWidth)

            // Progress ring
            Circle()
                .trim(from: 0.0, to: self.progress)
                .stroke(
                    AngularGradient(
                        gradient: Gradient(stops: [
                            .init(color: primaryColor.opacity(0), location: 0),
                            .init(color: primaryColor.opacity(0.5), location: 0.5),
                            .init(color: primaryColor.opacity(1.0), location: 1)
                        ]),
                        center: .center
                    ),
                    style: StrokeStyle(lineWidth: self.lineWidth, lineCap: .butt)
                )
                .rotationEffect(Angle(degrees: 270))

            HalfCircleView(
                color: primaryColor.opacity(progress),
                lineWidth: self.lineWidth
            )
            .offset(x: 0, y: viewSize.width / 2)
            .rotationEffect(Angle(degrees: (progress * 360) - (180 + (0.3 * progress))))
        }.sizeReader(size: $viewSize)
    }

    @ViewBuilder
    private func HalfCircleView(color: Color, lineWidth: CGFloat) -> some View {
        Circle()
            .trim(from: 0.0, to: 0.5)
            .fill(color)
            .rotationEffect(Angle(degrees: 90))
            .frame(width: lineWidth, height: lineWidth)
    }

    private func halfCircleColor() -> Color {
        return primaryColor.opacity(progress)
    }

}

#Preview {
    VStack(alignment: .center) {
        RotatingProgressRing(
            progress: .constant(0),
            background: Theme.Colors.backgroundColor
        ).padding(10)

        RotatingProgressRing(
            progress: .constant(0.2),
            background: Theme.Colors.backgroundColor
        ).padding(10)

        RotatingProgressRing(
            progress: .constant(0.6),
            background: Theme.Colors.backgroundColor
        ).padding(10)

        RotatingProgressRing(
            progress: .constant(1),
            background: Theme.Colors.backgroundColor
        ).padding(10)
    }
}
