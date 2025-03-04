//
//  PlayButton.swift
//  iosApp
//
//  Created by Stefan Lang on 11.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI

struct PauseButton: View {
    let action: () -> Void

    var body: some View {
        RoundedImageButton(
            image: Image.pauseButton,
            appearance: .medium,
            action: action
        )
    }
}

import PreviewSnapshots
struct PauseButton_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<Any?> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Default",
                    state: nil
                )
            ],

            configure: { _ in
                VStack {
                    PauseButton { /* NO OP */ }
                }.snapshotSetupFullScreen()
            }
        )
    }
}
