//
//  AnimatedProgressRing.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 23.07.24.
//

import SwiftUI

struct AnimatedProgressRing: View {
    let size: CGFloat
    let lineWidth: CGFloat
    let duration: CGFloat
    let startOpacity: CGFloat
    let background: Color
    let primaryColor: Color

    @State private var progress: CGFloat
    @State private var rotationDegree: CGFloat
    @State private var opacity: CGFloat

    var body: some View {
        ZStack {
            Circle()
                .stroke(background, lineWidth: self.lineWidth)

            // Progress ring
            Circle()
                .trim(from: 0.0, to: progress) // Trimmen basierend auf dem Fortschritt
                .stroke(
                    AngularGradient(
                        gradient: Gradient(stops: [
                            .init(color: primaryColor.opacity(self.startOpacity), location: 0),
                            .init(color: primaryColor.opacity(0.5), location: 0.5),
                            .init(color: primaryColor.opacity(1.0), location: 1)
                        ]),
                        center: .center
                    ),
                    style: StrokeStyle(lineWidth: self.lineWidth, lineCap: .butt)
                )
                .rotationEffect(Angle(degrees: 270))

            // round end of the progress circle
            // was no direct way to have a circle with a rounded ending, so creating and moving it along
            HalfCircleView(color: primaryColor.opacity(opacity), lineWidth: self.lineWidth)
                .offset(x: 0, y: size / 2)
                .rotationEffect(.degrees(rotationDegree))
        }.frame(width: size, height: size)
            .onAppear {
                withAnimation(animation(duration: (duration))) {
                    progress = 1
                    rotationDegree = 179.8 // avoiding hairline
                }

                let startDuration = (duration / 2)
                let remainingDuration = duration - startDuration

                withAnimation(animation(duration: startDuration) ) {
                    opacity = 0.5
                }

                withAnimation(animation(duration: remainingDuration).delay(startDuration)) {
                    opacity = 1
                }
            }
    }

    init(
        duration: CGFloat = 15.0,
        progress: CGFloat = 0.0,
        size: CGFloat = 200,
        lineWidth: CGFloat = 20,
        primaryColor: Color = Color.green,
        background: Color = Color.gray.opacity(0.3)
    ) {
        self.size = size
        self.duration = duration - (duration * progress)
        _progress = State(initialValue: progress)

        self.startOpacity = 0.0
        self.opacity = self.startOpacity
        self.rotationDegree = (progress * 360) -  180

        self.lineWidth = lineWidth
        self.primaryColor = primaryColor
        self.background = background
    }

    private func animation(duration: CGFloat) -> Animation {
        Animation.linear(duration: duration)
    }
}

private struct HalfCircleView: View {
    let color: Color
    let lineWidth: CGFloat
    var body: some View {
        Circle()
            .trim(from: 0.0, to: 0.5)
            .fill(color)
            .rotationEffect(Angle(degrees: 90))
            .frame(width: lineWidth, height: lineWidth)

    }
}

#Preview {
    AnimatedProgressRing(
        duration: 15,
        progress: 0.0,
        size: 200,
        lineWidth: 20
    )
}
