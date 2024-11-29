//
//  RoundImage.swift
//  iosApp
//
//  Created by Stefan Lang on 12.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
// TODO: Move to utils
extension Image {
    @ViewBuilder
    func round() -> some View {
        self.resizable()
            .aspectRatio(contentMode: .fit)
            .clipShape(Circle())
    }
}

import PreviewSnapshots
struct RoundImage_Previews: PreviewProvider {
    struct Data {
        let image: Image
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<Data> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Default",
                    state: .init(image: Image.highIntenseExercise)
                )
            ],

            configure: { data in
                ZStack {
                    data.image.round()
                }.snapshotSetupFullScreen()
            }
        )
    }
}
