//
//  BackgroundWithFloatingView.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 13.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import PreviewSnapshots

struct BackgroundWithFloatingView<Content: View, FloatingContent: View>: View {
    @ViewBuilder
    let floatingView: (_ scrollOffset: CGFloat) -> FloatingContent

    @ViewBuilder
    let contentView: () -> Content

    var body: some View {
        BackgroundView(
            floatingViewIsEmpty: false,
            floatingView: floatingView,
            contentView: contentView
        )
    }
}

struct BackgroundWithFloatingView_Previews: PreviewProvider {
    struct Data {
        let texts = [
            "Oh Hello there",
            "Obi Wan",
            "Anakin Skywalker"
        ]
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<Data> {
        PreviewSnapshots(
            configurations: [
                .init(name: "Default", state: Data())
            ],

            configure: { data in
                    BackgroundWithFloatingView(
                        floatingView: {_ in BuildPerformPersistView()},
                        contentView: {
                        VStack {
                            ForEach(data.texts, id: \.self) {
                                Text($0)
                            }
                        }
                    }
                )
            }
        )
    }
}
