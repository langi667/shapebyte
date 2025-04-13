import SwiftUI
import shared
import PreviewSnapshots

struct WorkoutHistoryEntryView: View {
    private let cornerRadius: Double = .roundedCornerShapeExtraLarge

    private let title: String
    private let date: String
    private let imageSize: Double = .dimensionMedium

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

                Spacer().frame(height: .spacingTiny)
                Text(date)
                    .labelSmall()
                    .foregroundStyle(Color.white)
            }

            Spacer()
        }.padding(.spacingTiny)
            .clipShape(
                RoundedRectangle(
                    cornerRadius: cornerRadius
                )
            )
            .overlay {
                RoundedRectangle(
                    cornerRadius: cornerRadius
                )
                .strokeBorder(Color.SBSecondary)
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
                    .background(Theme.colors.background)
                    .snapshotSetup()
            }
        )
    }
}
