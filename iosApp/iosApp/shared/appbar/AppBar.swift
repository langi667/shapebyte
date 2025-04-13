import SwiftUI

struct AppBar: View {
    let title: String
    let spacingTrailing: Double
    let onClose: () -> Void

    private let closeButtonSize: Double = .dimensionMedium

    init(
        title: String,
        spacingTrailing: Double = .dimensionTiny,
        onClose: @escaping () -> Void = {}
    ) {
        self.title = title
        self.spacingTrailing = spacingTrailing
        self.onClose = onClose
    }

    var body: some View {
        ZStack(alignment: .topLeading) {
            HStack(alignment: .center, spacing: 0) {
                Spacer().frame(width: closeButtonSize)

                Text(title)
                    .titleMedium()
                    .lineLimit(1)
                    .truncationMode(.middle)
                    .frame(maxWidth: .infinity)

                Spacer()
                    .frame(width: closeButtonSize)
            }

            HStack(alignment: .center, spacing: 0) {
                Spacer()
                Button(
                    action: onClose
                ) {
                    Text("X")
                        .bodyLarge()
                        .padding(.top, .spacingXTiny)
                        .padding(.trailing, spacingTrailing)
                }
            }
        }
    }
}

import PreviewSnapshots
struct AppBar_Previews: PreviewProvider {
    struct Data {
        let title: String
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<Data> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Title",
                    state: .init(title: "Title")
                ),

                .init(
                    name: "Long Title",
                    state: .init(title: "Long Title")
                ),

                .init(
                    name: "Very Long Title",
                    state: .init(title: "Long Long Long Long Long Title")
                )
            ],

            configure: { data in
                ZStack(alignment: .topLeading) {
                    BackgroundView()
                    VStack {
                        Spacer()
                            .frame(
                                height: SafeAreaInfo().wrappedValue.top
                            )
                        AppBar(title: data.title)
                    }

                }.snapshotSetupFullScreen()
            }
        )
    }
}
