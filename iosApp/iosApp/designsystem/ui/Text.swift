import SwiftUI

public extension Text {
    // TODO: consider using Design system
    static let defaultColor: Color = .white

    func displayLarge(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.displayLarge)
            .foregroundStyle(fontColor)
    }

    func displayMedium(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.displayMedium)
            .foregroundStyle(fontColor)
    }

    func displaySmall(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.displaySmall)
            .foregroundStyle(fontColor)
    }

    func titleLarge(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.titleLarge)
            .foregroundStyle(fontColor)
    }

    func titleMedium(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.titleMedium)
            .foregroundStyle(fontColor)
    }

    func titleSmall(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.titleSmall)
            .foregroundStyle(fontColor)
    }

    func headlineLarge(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.headlineLarge)
            .foregroundStyle(fontColor)
    }

    func headlineMedium(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.headlineMedium)
            .foregroundStyle(fontColor)
    }

    func headlineSmall(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.headlineSmall)
            .foregroundStyle(fontColor)
    }

    func labelLarge(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.labelLarge)
            .foregroundStyle(fontColor)
    }

    func labelMedium(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.labelLarge)
            .foregroundStyle(fontColor)
    }

    func labelSmall(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.labelSmall)
            .foregroundStyle(fontColor)
    }

    func bodyLarge(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.bodyMedium)
            .foregroundStyle(fontColor)
    }

    func bodyMedium(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.bodyMedium)
            .foregroundStyle(fontColor)
    }

    func bodySmall(fontColor: Color = Text.defaultColor) -> some View {
        self.font(Theme.fonts.bodySmall)
            .foregroundStyle(fontColor)
    }
}

import PreviewSnapshots
struct Text_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<String> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Display",
                    state: "Display"
                ),

                .init(
                    name: "Title",
                    state: "Title"
                ),

                .init(
                    name: "Headline",
                    state: "Headline"
                ),

                .init(
                    name: "Label",
                    state: "Label"
                ),

                .init(
                    name: "Body",
                    state: "Body"
                )
            ],

            configure: { data in
                VStack(
                    alignment: .center
                ) {
                    if data == "Display" {
                        Text("DisplayLarge").displayLarge()
                        Text("DisplayMedium").displayMedium()
                        Text("DisplaySmall").displaySmall()
                    } else if data == "Title" {
                        Text("TitleLarge").titleLarge()
                        Text("TitleMedium").titleMedium()
                        Text("TitleSmall").titleSmall()
                    } else if data == "Headline" {
                        Text("HeadlineLarge").headlineLarge()
                        Text("HeadlineMedium").headlineMedium()
                        Text("HeadlineSmall").headlineSmall()
                    } else if data == "Label" {
                        Text("LabelLarge").labelLarge()
                        Text("LabelMedium").labelMedium()
                        Text("LabelSmall").labelSmall()
                    } else if data == "Body" {
                        Text("BodyLarge").bodyLarge()
                        Text("BodyMedium").bodyMedium()
                        Text("BodySmall").bodySmall()
                    }
                }
                .snapshotSetupFullScreen()
                .background(Color.gray)
            }
        )
    }
}
