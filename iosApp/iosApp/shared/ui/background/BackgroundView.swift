//
//  Background.swift
//  ShapeByte
//
//  Created by Lang, Stefan [ShapeByte Tech] on 02.08.24.
//

import SwiftUI
import PreviewSnapshots

struct BackgroundView: View {
    static let defaultRadialOffset = Theme.Spacings.XXXL.toDimension(max: Theme.Spacings.XXXL)
    static let defaultTopOffset = Theme.Spacings.XXXL.toDimension(max: Theme.Spacings.XXXL)

    var topOffset = Self.defaultTopOffset
    var radialOffset = Self.defaultRadialOffset

    @State private var screenSize: CGSize = .zero

    var secondaryColor: Color = Theme.Colors.secondaryColor
    var radialColor: Color = Theme.Colors.backgroundColor

    var body: some View {
        ZStack {
            Rectangle()
                .fill(secondaryColor.gradient)
                .sizeReader(size: $screenSize)

            VStack(spacing: 0) {
                Spacer()
                    .frame(height: topOffset)

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
    struct State {
        let radialOffset: CGFloat
        let topOffset: CGFloat
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<State> {
        PreviewSnapshots(
            configurations: [
                .init(name: "Default", state: State(
                    radialOffset: BackgroundView.defaultRadialOffset,
                    topOffset: BackgroundView.defaultTopOffset
                )),
                .init(name: "Flat", state: State(
                    radialOffset: 0.0,
                    topOffset: BackgroundView.defaultTopOffset
                )),
                .init(name: "Plain", state: State(
                    radialOffset: 0.0,
                    topOffset: 0.0
                ) )

            ],
            configure: { state in
                BackgroundView(
                    topOffset: state.topOffset,
                    radialOffset: state.radialOffset
                ).snapshotSetupFullScreen()
            }
        )
    }
}
