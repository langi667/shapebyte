//
//  QuickWorkoutsListView.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 14.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import PreviewSnapshots

struct QuickWorkoutsListView: View {
    let quickWorkouts: [Workout]
    let paddingHorizontal: CGFloat = Theme.spacings.small
    let horizontalClipWidth: CGFloat = Theme.spacings.small * 1.5
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
                            Theme.Colors.backgroundColor,
                            Theme.Colors.backgroundColor.opacity(0)
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
                            image: ImageAsset(assetName: "sprints"),
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
                            image: ImageAsset(assetName: "Squats"),
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
                            image: ImageAsset(assetName: "Logo"),
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
                            image: ImageAsset(assetName: "sprints"),
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
                    .background(Theme.Colors.backgroundColor)
            }
        )
    }
}
