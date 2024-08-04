//
//  PendingExerciseView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import SwiftUI

struct PendingExerciseView: View {
    @Binding var progress: CGFloat

    let exerciseImageName: String = "Sprints"

    @State private var size: CGSize = .zero

    private let sizeFactor: CGFloat = 0.65
    private let imageSize: CGFloat = 220
    private let progressColor: Color = Theme.Colors.secondaryColor.opacity(0.4)

    var body: some View {
        ZStack {
            Circle()
                .strokeBorder(progressColor)
            
            VStack(spacing: 0) {
                Spacer().frame(height: Theme.Spacing.M )

                ZStack {
                    Image(exerciseImageName)
                        .resizable()
                        .frame(
                            width: imageSize,
                            height: imageSize
                        )

                        .clipShape(Circle())
                        .overlay {
                            Circle()
                                .rotation(.degrees( 180 * Double(1 - progress)))
                                .trim(from: 0, to: progress)
                                .fill(progressColor)

                        }
                }

                Spacer().frame(height: Theme.Spacing.XXS )

                Text("Legs and Core")
                    .h4()
                    .foregroundStyle(Color.white)

                Text("\(Int(progress * 100)) %")
                    .body()
                    .foregroundStyle(Color.white)
            }.frame(width: imageSize * 1.5, height: imageSize * 1.5)

        }.fixedSize()
    }

    func progressRotation() -> Double {
        return -180 * Double(1 - progress)
    }
}

#Preview {
    ZStack {
        BackgroundView()
        PendingExerciseView(progress: .constant(1))
    }
}
