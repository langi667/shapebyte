//
//  PlayButton.swift
//  iosApp
//
//  Created by Stefan Lang on 11.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct RoundedImageButton: View {
    let image: Image
    let appearance: RoundedImageButtonAppearance
    let action: () -> Void

    var body: some View {
        Button(action: action) {
            image
                .round()
                .frame(
                    width: appearance.size.toDimensionMax(),
                    height: appearance.size.toDimensionMax()
                )
        }
    }
}

import PreviewSnapshots
struct RoundedImageButton_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<RoundedImageButtonAppearance> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Small",
                    state: .small
                ),

                .init(
                    name: "Medium",
                    state: .medium
                ),

                .init(
                    name: "Large",
                    state: .large
                )
            ],

            configure: { data in
                VStack {
                    RoundedImageButton(
                        image: Image("Logo"),
                        appearance: data) { /* NO OP */ }
                }.snapshotSetupFullScreen()
            }
        )
    }
}

