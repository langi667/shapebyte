import SwiftUI
import shared
import PreviewSnapshots

struct QuickWorkoutEntryView: View {
    let name: String
    let teaser: String
    let image: shared.ImageResource
    let onClicked: () -> Void

    private let cornerRadius: Double = .roundedCornerShapeLarge
    private let imageSize: Double = .dimensionSmall + .spacingMedium
    private let maxViewWidth: Double = .dimensionXLarge
    private let itemSpacing: Double = .spacingXTiny

    init(
        name: String,
        teaser: String,
        image: shared.ImageResource,
        onClicked: @escaping () -> Void = {}
    ) {
        self.name = name
        self.teaser = teaser
        self.image = image
        self.onClicked = onClicked
    }

    init(workout: Workout, onClicked: @escaping () -> Void = {}) {
        self.init(
            name: workout.name,
            teaser: workout.shortDescription,
            image: workout.image,
            onClicked: onClicked
        )
    }

    var body: some View {
        Button(
            action: onClicked,
            label: { contentView() }
        )
    }

    @ViewBuilder
    private func contentView() -> some View {
        VStack(spacing: itemSpacing) {
            Spacer().frame(height: itemSpacing)
            text(name, isBold: true)

            VStack(alignment: .center) {
                Image(image)
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
            .strokeBorder(Theme.colors.secondary)
        }
    }

    @ViewBuilder
    private func text(_ text: String, isBold: Bool) -> some View {
        Text(text)
            .labelSmall()
            .if(isBold) {
                $0.bold()
            }
            .foregroundStyle(Color.white)
            .lineLimit(1)
            .truncationMode(.tail)
            .frame(width: maxViewWidth -  2 * .dimensionTiny)
    }
}

struct QuickWorkoutEntryView_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<[Workout]> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Default",
                    state: [
                        QuickWorkoutsPreviewDataProvider.shared.hiit,
                        QuickWorkoutsPreviewDataProvider.shared.hiitLongTitle,
                        QuickWorkoutsPreviewDataProvider.shared.hiitLongDescription,
                        QuickWorkoutsPreviewDataProvider.shared.hiitLongTitleAndDesc
                    ]
                )
            ],

            configure: { states in
                VStack(spacing: .spacingTiny) {
                    ForEach(states) { workout in
                        QuickWorkoutEntryView(workout: workout)
                            .snapshotSetup()
                            .background(Theme.colors.background)
                    }
                }
            }
        )
    }
}
