//
//  WorkoutSessionFinishedScreen.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 01.08.24.
//

import SwiftUI

struct WorkoutSessionFinishedScreen: View {
    var body: some View {
        ZStack {
            BackgroundView()

            VStack(spacing: 0) {
                Image("Logo")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .padding(Theme.Spacing.L)

                Text("Workout Finished\nGreat Job!")
                    .h1()
                    .multilineTextAlignment(.center)

                Spacer()
            }.fixedSize()
        }
    }
}

#Preview {
    WorkoutSessionFinishedScreen()
}
