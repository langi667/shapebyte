import SwiftUI

struct PlayButton: View {
    let action: () -> Void

    var body: some View {
        RoundedImageButton(
            image: Image.playButtonLarge,
            appearance: .large,
            action: action
        )
    }
}

import PreviewSnapshots
struct PlayButton_Previews: PreviewProvider {
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
                    PlayButton { /* NO OP */ }
                }.snapshotSetupFullScreen()
            }
        )
    }
}
