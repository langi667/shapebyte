//
//  Background.swift
//  ShapeByte
//
//  Created by Lang, Stefan [ShapeByte Tech] on 02.08.24.
//

import SwiftUI
import PreviewSnapshots

struct BackgroundView: View {
    static let defaultRadialOffset = Theme.spacings.xxxLarge.toDimension(max: Theme.spacings.xxxLarge)
    static let defaultTopOffset = Theme.spacings.xxxLarge.toDimension(max: Theme.spacings.xxxLarge)

    @ViewBuilder
    private let topView: () -> AnyView

    var radialOffset: CGFloat

    @State private var screenSize: CGSize

    var secondaryColor: Color
    var radialColor: Color

    init(
        topOffset: CGFloat = Self.defaultTopOffset,
        radialOffset: CGFloat = Self.defaultRadialOffset,
        screenSize: CGSize = .zero,
        secondaryColor: Color = Theme.Colors.secondaryColor,
        radialColor: Color = Theme.Colors.backgroundColor
    ) {
        self.init(
            radialOffset: radialOffset,
            screenSize: screenSize,
            secondaryColor: secondaryColor,
            radialColor: radialColor
        ) {
            AnyView(Spacer().frame(height: topOffset))
        }
    }

    init(
        radialOffset: CGFloat = Self.defaultRadialOffset,
        screenSize: CGSize = .zero,
        secondaryColor: Color = Theme.Colors.secondaryColor,
        radialColor: Color = Theme.Colors.backgroundColor,
        topView: @escaping () -> AnyView
    ) {
        self.topView = topView
        self.radialOffset = radialOffset
        self.screenSize = screenSize
        self.secondaryColor = secondaryColor
        self.radialColor = radialColor
    }

    var body: some View {
        ZStack {
            Rectangle()
                .fill(secondaryColor.gradient)
                .sizeReader(size: $screenSize)

            VStack(spacing: 0) {
                topView()

                Arc()
                    .fill(radialColor)
                    .frame(height: radialOffset)

                Rectangle()
                    .fill(radialColor)
            }
        }.ignoresSafeArea()
    }
}

struct BackgroundView_Previews: PreviewProvider {
    enum State {
        case topOffset(topOffset: CGFloat, radialOffset: CGFloat)
        case topView(radialOffset: CGFloat)
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<State> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Default",
                    state: State.topOffset(
                        topOffset: BackgroundView.defaultTopOffset,
                        radialOffset: BackgroundView.defaultRadialOffset
                    )
                ),
                .init(
                    name: "Flat",
                    state: State.topOffset(
                        topOffset: BackgroundView.defaultTopOffset,
                        radialOffset: 0.0
                    )
                ),
                .init(
                    name: "Plain",
                    state: State.topOffset(
                        topOffset: 0.0,
                        radialOffset: 0.0
                    )
                ),
                .init(
                    name: "TopView",
                    state: State.topView(radialOffset: BackgroundView.defaultRadialOffset)
                )
            ],
            configure: { state in
                switch state {
                case .topOffset(let topOffset, let radialOffset):
                    BackgroundView(
                        topOffset: topOffset,
                        radialOffset: radialOffset
                    ).snapshotSetupFullScreen()
                case .topView(let radialOffset):
                    BackgroundView(
                        radialOffset: radialOffset
                    ) {
                        AnyView(
                            Text("Hello Test!")
                                .headlineLarge()
                                .frame(maxWidth: .infinity, minHeight: 250)
                        )
                    }.snapshotSetupFullScreen()
                }
            }
        )
    }
}
