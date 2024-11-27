//
//  QuickWorkoutView.swift
//  iosApp
//
//  Created by Lang, Stefan [RTL Tech] on 14.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import PreviewSnapshots

struct QuickWorkoutEntryView: View {
    let name: String
    let teaser: String
    let imageName: String

    private let cornerRadius = Theme.Shapes.large
    private let imageSize = Theme.dimensions.small + Theme.spacings.medium
    private let maxViewWidth = Theme.dimensions.xLarge
    private let itemSpacing = Theme.spacings.xTiny

    init(name: String, teaser: String, imageName: String) {
        self.name = name
        self.teaser = teaser
        self.imageName = imageName
    }

    init(workout: Workout) {
        self.init(
            name: workout.name,
            teaser: workout.shortDescription,
            imageName: workout.image.fileNameWithoutEnding
        )
    }

    var body: some View {
        VStack(spacing: itemSpacing) {
            Spacer().frame(height: itemSpacing)
            text(name, isBold: true)

            VStack(alignment: .center) {
                Image(imageName, bundle: Bundle.main)
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: imageSize, height: imageSize)
            }

            text(teaser, isBold: false)
            Spacer().frame(height: itemSpacing)

        }
        .frame(width: maxViewWidth)
        .overlay {
            RoundedRectangle(
                cornerRadius: cornerRadius
            )
            .strokeBorder(Theme.Colors.secondaryColor)
        }
    }

    @ViewBuilder
    func text(_ text: String, isBold: Bool) -> some View {
        Text(text)
            .labelSmall()
            .if(isBold) {
                $0.bold()
            }
            .foregroundStyle(Color.white)
            .lineLimit(1)
            .truncationMode(.tail)
            .frame(width: maxViewWidth -  2 * Theme.dimensions.tiny)
    }
}

struct QuickWorkoutEntryView_Previews: PreviewProvider {
    enum State: Identifiable {
        case data(name: String, teaser: String, imageName: String)
        case workout(workout: Workout)

        var id: String {
            switch self {
            case .data(let name, let teaser, let imageName):
                return name + teaser + imageName
            case .workout(let workout):
                return workout.name + workout.shortDescription + workout.image.assetName
            }
        }
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<[State]> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Default",
                    state: [
                        .data(
                            name: "HIIT Workout",
                            teaser: "20 min, legs",
                            imageName: "Squats"
                        ),

                            .data(
                                name: "HIIT Workout long text",
                                teaser: "20 min, legs",
                                imageName: "Squats"
                            ),

                            .data(
                                name: "HIIT Workout",
                                teaser: "20 min, legs, core, chest",
                                imageName: "Squats"
                            ),

                            .data(
                                name: "HIIT Workout long text",
                                teaser: "20 min, legs, core, chest",
                                imageName: "Squats"
                            ),

                            .workout(
                                workout:
                                    Workout(
                                        name: "Workout",
                                        shortDescription: "legs, core",
                                        image: ImageAsset(assetName: "Squats"))
                            )
                    ]
                )
            ],

            configure: { states in
                VStack(spacing: Theme.spacings.tiny) {
                    ForEach(states) { state in
                        switch state {
                        case .data(let name, let teaser, let imageName):
                            QuickWorkoutEntryView(name: name, teaser: teaser, imageName: imageName)
                                .snapshotSetup()
                                .background(Theme.Colors.backgroundColor)

                        case .workout(let workout):
                            QuickWorkoutEntryView(workout: workout)
                                .snapshotSetup()
                                .background(Theme.Colors.backgroundColor)
                        }
                    }
                }
            }
        )
    }
}
