//
//  WorkoutHistoryEntryView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [ShapeByte Tech] on 02.08.24.
//

import SwiftUI
import shared
import PreviewSnapshots

struct WorkoutHistoryEntryView: View {
    private let cornerRadius = Theme.Shapes.xLarge

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

struct WorkoutHistoryEntryView_Previews: PreviewProvider {
    struct State {
        let entry: WorkoutHistoryEntry =  DPI
            .shared
            .workoutHistoryEntry(
                scheduleEntry: WorkoutScheduleEntry(
                    id: "1",
                    name: "Legs",
                    date: Date(timeIntervalSince1970: 1_729_360_000).toInstant(),
                    progress: Progress(progress: 0.7)
                )
            )
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<State> {
        PreviewSnapshots(
            configurations: [
                .init(name: "Default", state: State() )
            ],

            configure: { state in
                WorkoutHistoryEntryView(entry: state.entry)
                    .snapshotSetup()
                    .background(Theme.Colors.backgroundColor)
            }
        )
    }
}
