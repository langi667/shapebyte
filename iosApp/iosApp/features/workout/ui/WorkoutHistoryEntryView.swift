import SwiftUI
import shared
import PreviewSnapshots

struct WorkoutHistoryEntryView: View {
    private let cornerRadius = Theme.Shapes.xLarge

    private let title: String
    private let date: String
    private let imageSize = Theme.dimensions.medium

    var body: some View {
        HStack {
            Image("squats")
                .resizable()
                .frame(width: imageSize, height: imageSize)
                .clipShape(Circle())

            VStack(alignment: .leading) {
                Text(title)
                    .labelMedium()
                    .foregroundStyle(Color.white)

                Spacer().frame(height: Theme.spacings.tiny)
                Text(date)
                    .labelSmall()
                    .foregroundStyle(Color.white)
            }

            Spacer()
        }.padding(Theme.spacings.tiny)
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
        let entry: WorkoutHistoryEntry =  SharedModule
            .shared
            .workoutHistoryEntry(
                scheduleEntry: WorkoutSchedulePreviewDataProvider.shared.workoutScheduleEntry
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
                    .background(Theme.Colors.backgroundColor)
                    .snapshotSetup()
            }
        )
    }
}
