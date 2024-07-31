//
//  SegmentedProgressRing.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 24.07.24.
//

import SwiftUI

struct SegmentedProgressRing: View {
    let fillColor: Color
    let backgroundColor: Color

    @Binding
    var numberOfSegments: Int

    @Binding
    var progress: Progress

    var gapPercentage: Double {
        if numberOfSegments <= 1 {
            return 0.001
        }

        if numberOfSegments == 2 {
            return 0.01
        }

        // Berechnet den Prozentsatz der Lücke.
        return 0.1 / Double(numberOfSegments)
    }

    var body: some View {
        ZStack {
            let totalAngle = 1.0
            let gap = gapPercentage
            let segmentAngle = (totalAngle - gap * Double(numberOfSegments)) / Double(numberOfSegments)

            ForEach(0..<numberOfSegments, id: \.self) { index in
                let start = (Double(index) * (segmentAngle + gap)).truncatingRemainder(dividingBy: totalAngle)
                let end = (start + segmentAngle).truncatingRemainder(dividingBy: totalAngle)

                let animationColors: [Color] = Self.progressColors(
                    numberOfSegments: numberOfSegments,
                    progress: progress,
                    color: backgroundColor
                )

                Circle()
                    .trim(from: CGFloat(start), to: CGFloat(end))
                    .stroke(fillColor, lineWidth: 20)
                    .rotationEffect(.degrees(-90))

                Circle()
                    .trim(from: CGFloat(start + 0.001 ), to: CGFloat(end - 0.001))
                    .stroke(animationColors[index], lineWidth: 18)
                    .rotationEffect(.degrees(-90))
            }
        }
    }

    init(
        numberOfSegments: Binding<Int>,
        progress: Binding<Progress>,
        fillColor: Color = .red,
        backgroundColor: Color = .white
    ) {
        self._numberOfSegments = numberOfSegments
        self.fillColor = fillColor
        self.backgroundColor = backgroundColor
        self._progress = progress
    }

    private static func progressColors(
        numberOfSegments: Int,
        progress: Progress,
        color: Color
    ) -> [Color] {
        var retVal: [Color] = .init(
            repeating: color.opacity(0),
            count: numberOfSegments
        )

        let progressToSegments = progress.value * CGFloat(numberOfSegments)
        if progressToSegments.integerPart >= numberOfSegments {
            return retVal
        }

        retVal[progressToSegments.integerPart] = color.opacity(1 - progressToSegments.fractionalPart)
        let nextIndex = progressToSegments.integerPart + 1

        if nextIndex >= numberOfSegments {
            return retVal
        }

        for index in (progressToSegments.integerPart + 1)..<numberOfSegments {
            retVal[index] = color.opacity(1)
        }

        return retVal
    }
}

#Preview {
    ZStack {
        SegmentedProgressRing(
            numberOfSegments: .constant(10),
            progress: .constant(Progress(0.5)),
            fillColor: Color.accentColor
        ).padding(35)
    }
}
