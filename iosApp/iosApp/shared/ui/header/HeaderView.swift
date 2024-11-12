 //
//  HeaderView.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 07.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import PreviewSnapshots

struct HeaderView: View {
    let overlayColor: Color
    let overlayOpacity: CGFloat
    let scale: CGFloat

    let imageScale: CGFloat
    let headerHeight: CGFloat
    let minimumHeaderHeight: CGFloat

    let offsetY: CGFloat
    let contentPaddingVertical: CGFloat
    let contentPaddingHorizontal: CGFloat = Theme.Spacings.S

    init(
        headerHeight: CGFloat = 128,
        minimumHeaderHeight: CGFloat = 102,
        offsetY: CGFloat = 0,
        overlayColor: Color = Theme.Colors.secondaryColor,
        overlayOpacity: CGFloat = 1,
        scale: CGFloat = 1,
        imageScale: CGFloat = 1,
        contentPaddingVertical: CGFloat = 20
    ) {
        self.headerHeight = headerHeight
        self.minimumHeaderHeight = minimumHeaderHeight
        self.offsetY = offsetY

        self.overlayColor = overlayColor
        self.overlayOpacity = overlayOpacity

        self.scale = scale
        self.imageScale = imageScale

        self.contentPaddingVertical = contentPaddingVertical
    }

    var body: some View {
        GeometryReader {_ in
            ZStack {
                Rectangle()
                    .fill(overlayColor.opacity(overlayOpacity))

                HStack {
                    VStack(alignment: .leading) {
                        Text("Welcome back")
                            .body()
                            .foregroundStyle(Color.white)

                        Text("Stefan")
                            .h2()
                            .foregroundStyle(Color.white)
                    }
                    .scaleEffect(scale, anchor: .leading)

                    Spacer()

                    Image("Logo")
                        .resizable()
                        .frame(width: Theme.Spacings.XL * imageScale,
                               height: Theme.Spacings.XL * imageScale)
                        .clipShape(Circle())

                }.padding(.horizontal, contentPaddingHorizontal)
                    .padding(.top, contentPaddingVertical)
            }
            .frame(
                height: (headerHeight + offsetY) < minimumHeaderHeight ? minimumHeaderHeight : (headerHeight + offsetY),
                alignment: .bottom)
            .offset(y: -offsetY)
        }.frame(height: headerHeight)
    }
}

struct HeaderView_Previews: PreviewProvider {
    struct Data {
        let headerHeight: CGFloat
        let overlayColor: Color
        let overlayOpacity: CGFloat
        let scale: CGFloat
        let imageScale: CGFloat

        init(
            headerHeight: CGFloat = 128,
            overlayColor: Color = Theme.Colors.secondaryColor,
            overlayOpacity: CGFloat = 0,
            scale: CGFloat = 1,
            imageScale: CGFloat = 1
        ) {
            self.headerHeight = headerHeight
            self.overlayColor = overlayColor
            self.overlayOpacity = overlayOpacity
            self.scale = scale
            self.imageScale = imageScale
        }
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<HeaderView_Previews.Data> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "State Expanded",
                    state: .init()
                ),

                    .init(
                        name: "Half shrinked",
                        state: Data(
                            headerHeight: 115,
                            overlayOpacity: 0.5,
                            scale: 0.5,
                            imageScale: 0.75
                        )
                    ),

                    .init(
                        name: "Fully shrinked",
                        state: .init(
                            headerHeight: 102,
                            overlayOpacity: 1,
                            scale: 0,
                            imageScale: 0.5
                        )
                    )
            ],

            configure: { state in
                ZStack(alignment: .topLeading) {
                    RadialBackgroundView()
                    HeaderView(
                        headerHeight: 128,
                        minimumHeaderHeight: 128,
                        offsetY: 0,
                        overlayColor: state.overlayColor,
                        overlayOpacity: state.overlayOpacity,
                        scale: state.scale,
                        imageScale: state.imageScale,
                        contentPaddingVertical: 64
                    ).frame(width: 390).fixedSize(horizontal: true, vertical: true)
                }.edgesIgnoringSafeArea(.all)
            }
        )
    }
}
