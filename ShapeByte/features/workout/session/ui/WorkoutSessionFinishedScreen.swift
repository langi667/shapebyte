//
//  WorkoutSessionFinishedScreen.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 01.08.24.
//

import SwiftUI

struct WorkoutSessionFinishedScreen: View {
    var body: some View {
        ZStack {
            Circle()
                .stroke(Theme.Colors.accentColor, lineWidth: 20)

            Text("Workout Finished\nGreat Job!")
                .h1PrimaryColor()
                .multilineTextAlignment(.center)

        }
        .padding(Theme.Dimenstions.M)
    }
}

#Preview {
    WorkoutSessionFinishedScreen()
}
