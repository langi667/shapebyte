import SwiftUI

struct StopButton: View {
    let action: () -> Void

    var body: some View {
        RoundedImageButton(
            image: Image.stopButton,
            appearance: .medium,
            action: action
        )
    }
}

import PreviewSnapshots
struct StopButton_Previews: PreviewProvider {
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
                    StopButton { /* NO OP */ }
                }.snapshotSetupFullScreen()
            }
        )
    }
}
