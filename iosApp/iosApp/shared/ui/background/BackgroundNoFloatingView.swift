//
//  BackgroundNoFloatingView.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 13.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import PreviewSnapshots

struct BackgroundNoFloatingView<Content: View>: View {

    @ViewBuilder
    let contentView: () -> Content

    var body: some View {
        BackgroundView(
            floatingViewIsEmpty: true,
            floatingView: {_ in EmptyView() },
            contentView: contentView)
    }
}

struct BackgroundNoFloatingView_Previews: PreviewProvider {
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
                BackgroundNoFloatingView {
                    VStack {
                        ForEach(data.texts, id: \.self) {
                            Text($0)
                        }
                    }
                }
            }
        )
    }
}
