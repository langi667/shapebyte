//
//  WorkoutHistoryEntryView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import SwiftUI

struct WorkoutHistoryEntryView: View {

    private let cornerRadius = Theme.Spacing.L
    var body: some View {

        HStack {
            Image("Squats")
                .resizable()
                .frame(width: Theme.Spacing.L, height: Theme.Spacing.L)
                .clipShape(Circle())

            VStack(alignment: .leading) {
                Text("HIIT")
                    .h4()
                    .foregroundStyle(Color.white)

                Spacer().frame(height: Theme.Spacing.XS)
                Text("Mo. 26.08.2024")
                    .footnote()
                    .foregroundStyle(Color.white)
            }

            Spacer()
        }.padding(Theme.Spacing.XS)
            .clipShape(
                RoundedRectangle(
                    cornerRadius: cornerRadius
                )
            )
            .overlay {
                RoundedRectangle(
                    cornerRadius: cornerRadius
                )
                    .strokeBorder(Color(
                        red: 104 / 255,
                        green: 187 / 255,
                        blue: 193 / 255)
                    )
            }
    }
}

#Preview {
    ZStack {
        BackgroundView()
        WorkoutHistoryEntryView()
    }

}
