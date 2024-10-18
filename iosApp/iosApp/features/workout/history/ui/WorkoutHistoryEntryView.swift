//
//  WorkoutHistoryEntryView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import SwiftUI

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

#Preview {
    ZStack {
        BackgroundView()
        WorkoutHistoryEntryView(
            entry: WorkoutHistoryEntry(
                entry: WorkoutScheduleEntry(
                    id: "1",
                    name: "Squats",
                    date: Date(),
                    progress: .Companion.shared.COMPLETE)
            )
        )
    }

}
