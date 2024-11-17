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
    let paddingHorizontal: CGFloat = Theme.Spacings.S
    let horizontalClipWidth: CGFloat = Theme.Spacings.S * 1.5

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
        QuickWorkoutEntryView(workout: workout)
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
                            name: "Workout 1",
                            shortDescription: "Workout 1 desc",
                            image: ImageAsset(assetName: "sprints")
                        ),

                        Workout(
                            name: "Workout 2",
                            shortDescription: "Workout 2 desc",
                            image: ImageAsset(assetName: "Squats")
                        ),

                        Workout(
                            name: "Workout 3",
                            shortDescription: "Workout 3 desc",
                            image: ImageAsset(assetName: "Logo")
                        ),

                        Workout(
                            name: "Workout 4",
                            shortDescription: "Workout 4 desc",
                            image: ImageAsset(assetName: "sprints")
                        )
                    ]
                )
            ],

            configure: { workouts in
                QuickWorkoutsListView(quickWorkouts: workouts)
                    .snapshotSetup()
                    .background(Theme.Colors.backgroundColor)
            }
        )
    }
}
