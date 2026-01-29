import SwiftUI
import shared
import PreviewSnapshots

struct QuickWorkoutsListView: View {
    let quickWorkouts: [Workout]
    let paddingHorizontal: CGFloat = .spacingSmall
    let horizontalClipWidth: CGFloat = .spacingSmall * 1.5
    let onWorkoutSelected: (Workout) -> Void

    var body: some View {
        ZStack(alignment: .topLeading) {
            ScrollView(.horizontal) {
                LazyHStack(spacing: 0) {
                    ForEach(quickWorkouts, id: \.self) { workout in
                        quickWorkoutEntryView(for: workout)
                    }
                }
                .scrollTargetLayout()
            }
            .scrollTargetBehavior(.viewAligned)

            HStack {
                horizontalClipView()
                Color.clear
                horizontalClipView().rotationEffect(Angle(degrees: 180))
            }
            .frame(maxWidth: .infinity)
        }
    }

    @ViewBuilder
    private func quickWorkoutEntryView(for workout: Workout) -> some View {
        QuickWorkoutEntryView(workout: workout) {
            onWorkoutSelected(workout)
        }
        .padding(.leading, paddingHorizontal)
        .if(workout == quickWorkouts.last) { view in
            view.padding(.trailing, paddingHorizontal)
        }
    }

    @ViewBuilder
    private func horizontalClipView() -> some View {
        Rectangle()
            .fill(
                LinearGradient(
                    gradient: Gradient(
                        colors: [
                            Theme.colors.background,
                            Theme.colors.background.opacity(0)
                        ]
                    ),
                    startPoint: .leading,
                    endPoint: .trailing
                )
            )
            .frame(width: horizontalClipWidth)
    }
}

struct View_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<[Workout]> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Default",
                    state: [
                        Workout(
                            id: 1,
                            name: "Workout 1",
                            shortDescription: "Workout 1 desc",
                            image: ImageResource(id: "Sprints.png"),
                            type: WorkoutTypeTimedInterval(
                                highDurationSec: 10,
                                lowDurationSec: 10,
                                rounds: 2
                            )
                        ),

                        Workout(
                            id: 2,
                            name: "Workout 2",
                            shortDescription: "Workout 2 desc",
                            image: ImageResource(id: "Squats.png"),
                            type: WorkoutTypeTimedInterval(
                                highDurationSec: 10,
                                lowDurationSec: 10,
                                rounds: 2
                            )
                        ),

                        Workout(
                            id: 3,
                            name: "Workout 3",
                            shortDescription: "Workout 3 desc",
                            image: ImageResource(id: "Logo.png"),
                            type: WorkoutTypeTimedInterval(
                                highDurationSec: 10,
                                lowDurationSec: 10,
                                rounds: 2
                            )
                        ),

                        Workout(
                            id: 4,
                            name: "Workout 4",
                            shortDescription: "Workout 4 desc",
                            image: ImageResource(id: "Sprints.png"),
                            type: WorkoutTypeTimedInterval(
                                highDurationSec: 10,
                                lowDurationSec: 10,
                                rounds: 2
                            )
                        )
                    ]
                )
            ],

            configure: { workouts in
                QuickWorkoutsListView(quickWorkouts: workouts, onWorkoutSelected: {_ in })
                    .snapshotSetup()
                    .background(Theme.colors.background)
            }
        )
    }
}
