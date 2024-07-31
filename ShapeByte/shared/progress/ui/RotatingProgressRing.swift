//
//  RotatingProgressRing.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 30.07.24.
//

import Foundation

import SwiftUI

struct RotatingProgressRing: View {
    let lineWidth: CGFloat
    let startOpacity: CGFloat
    let background: Color
    let primaryColor: Color

    @StateObject private var stateHolder = StateHolder()
    @State private var viewSize: CGSize = .zero
    @Binding var duration: DurationWrapper

    var body: some View {
        ZStack {
            progressView()
                .sizeReader(size: $viewSize)
        }.onChange(of: duration, initial: false) { _, newDuration  in
            self.stateHolder.resetWith(duration: newDuration)
        }
    }

    init(
        duration: Binding<DurationWrapper>,
        lineWidth: CGFloat = 20,
        primaryColor: Color = Color.red,
        background: Color = Color.gray.opacity(0.3)
    ) {
        self._duration = duration
        self.lineWidth = lineWidth
        self.primaryColor = primaryColor

        self.background = background
        self.startOpacity = 0
    }

    @ViewBuilder
    func progressView() -> some View {
        ZStack {
            Circle()
                .stroke(background, lineWidth: self.lineWidth)

            // Progress ring
            Circle()
                .trim(from: 0.0, to: self.stateHolder.progress)
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

            HalfCircleView(
                color: primaryColor.opacity(stateHolder.opacity),
                lineWidth: self.lineWidth
            )
            .offset(x: 0, y: viewSize.width / 2)
            .rotationEffect(Angle(degrees: stateHolder.rotationDegree))
        }
    }

    private class StateHolder: ObservableObject {
        @Published var progress: CGFloat = 0
        @Published var opacity: CGFloat = 0
        @Published var rotationDegree: CGFloat = 0
        @Published var duration: DurationWrapper = DurationWrapper(0)

        func reset() {
            self.progress = 0
            self.rotationDegree = (progress * 360) - 180.2
            self.opacity = progress
        }

        func animate() {
            withAnimation(.linear(duration: duration.value)) {
                self.progress = 1.0
                self.rotationDegree = 179.8
                self.opacity = 1

            }
        }

        func resetWith(duration: DurationWrapper) {
            if self.duration == duration {
                return
            }

            self.duration = duration
            self.reset()
            animate()
        }
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
        RotatingProgressRing(
            duration: .constant(DurationWrapper(3.0)),
            lineWidth: 20
        ).padding(100)
    }
}
