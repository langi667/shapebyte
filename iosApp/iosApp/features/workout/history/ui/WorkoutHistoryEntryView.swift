//
//  WorkoutHistoryEntryView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import SwiftUI
import shared

struct WorkoutHistoryEntryView: View {
    private let cornerRadius = Theme.Spacings.L

    private let title: String
    private let date: String

    var body: some View {

        HStack {
            Image("Squats")
                .resizable()
                .frame(width: Theme.Spacings.L, height: Theme.Spacings.L)
                .clipShape(Circle())

            VStack(alignment: .leading) {
                Text(title)
                    .h4()
                    .foregroundStyle(Color.white)

                Spacer().frame(height: Theme.Spacings.XS)
                Text(date)
                    .footnote()
                    .foregroundStyle(Color.white)
            }

            Spacer()
        }.padding(Theme.Spacings.XS)
            .clipShape(
                RoundedRectangle(
                    cornerRadius: cornerRadius
                )
            )
            .overlay {
                RoundedRectangle(
                    cornerRadius: cornerRadius
                )
                .strokeBorder(Theme.Colors.secondaryColor)
            }
    }

    init(entry: WorkoutHistoryEntry) {
        self.title = entry.name
        self.date = entry.dateString
    }
}

// TODO: snapshot tests
 #Preview {
    ZStack {
        RadialBackgroundView()
        WorkoutHistoryEntryView(
            entry: DPI
                .shared
                .workoutHistoryEntry(
                    scheduleEntry: WorkoutScheduleEntry(
                        id: "1",
                        name: "Legs",
                        date: Date(timeIntervalSince1970: 1_729_360_000).toInstant(),
                        progress: Progress(progress: 0.7)
                    )
                )
            )
        }

 }
