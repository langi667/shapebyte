//
//  AnimatedProgressRing.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 23.07.24.
//

import SwiftUI

struct AnimatedProgressRing: View {
    let lineWidth: CGFloat
    let startOpacity: CGFloat
    let background: Color
    let primaryColor: Color
    let progress: Progress
    let roundedEnd: Bool

    @State private var rotationDegree: CGFloat
    @State private var opacity: CGFloat
    @State private var viewSize: CGSize = .zero

    var body: some View {
        ZStack {
            progressView(progress: self.progress.value)
                .sizeReader(size: $viewSize)
            }
    }

    init(
        progress: Progress = Progress(0),
        lineWidth: CGFloat = 20,
        primaryColor: Color = Color.red,
        background: Color = Color.gray.opacity(0.3),
        roundedEnd: Bool = true
    ) {
        self.progress = progress
        self.startOpacity = 0

        let data = Self.updateVisuals(progress: progress.value)

        self.opacity = data.opacity
        self.rotationDegree = data.rotationDegree

        self.lineWidth = lineWidth
        self.primaryColor = primaryColor
        self.background = background
        self.roundedEnd = roundedEnd
    }

    @ViewBuilder
    func progressView(progress: CGFloat) -> some View {
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

            if roundedEnd {
                HalfCircleView(color: primaryColor.opacity(opacity), lineWidth: self.lineWidth)
                    .offset(x: 0, y: viewSize.width / 2)
                    .rotationEffect(.degrees(self.rotationDegree))
            }

        }.onAppear {
            updateVisuals()
        }.onChange(of: progress, initial: false) { _, _  in
            updateVisuals() // Update visuals every time progress changes
        }
    }

    private func updateVisuals() {
        let data = Self.updateVisuals(progress: self.progress.value)

        self.rotationDegree = data.rotationDegree
        self.opacity = data.opacity
    }

    private static func updateVisuals(
        progress: CGFloat
    ) -> (rotationDegree: CGFloat, opacity: CGFloat) {
        return ((progress * 360) - 180.2, progress)
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
    HStack(alignment: .center) {
        AnimatedProgressRing(
            progress: Progress(1),
            lineWidth: 20
        ).padding(100)
    }
}
