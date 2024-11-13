//
//  BackgroundView.swift
//  iosApp
//
//  Created by Lang, Stefan [ShapeByte Tech] on 13.11.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import shared
import PreviewSnapshots

struct BackgroundView<Content: View, FloatingContent: View>: View {
    @ViewBuilder
    let contentView: () -> Content

    @ViewBuilder
    let floatingView: (_ scrollOffset: CGFloat) -> FloatingContent

    private let defaultRadialOffset: CGFloat = Theme.Spacings.XXXL
    private var safeAreaInsets: EdgeInsets {
        BackgroundViewAppearance.safeAreaInsets
    }

    @State private var offsetY: CGFloat = 0
    @State private var scrollViewSize: CGSize = .zero

    @State private var radialOffset: CGFloat
    @State private var pendingExerciseSize: CGSize = .zero
    @State private var headerProgress: CGFloat = .zero

    @State private var headerOverlayOpacity: CGFloat = .zero
    @State private var headerScale: CGFloat = .zero
    @State private var headerImageScale: CGFloat = .zero

    private let headerOverlayColor: Color = Theme.Colors.secondaryColor
    private let floatingViewIsEmpty: Bool

    private var headerHeight: CGFloat {
        BackgroundViewAppearance.headerHeight
    }

    private var minimumHeaderHeight: CGFloat {
        BackgroundViewAppearance.minimumHeaderHeight
    }

    private let viewTopOffset: CGFloat = 8
    private let paddingHorizontal: CGFloat = Theme.Spacings.S

    init(
        floatingViewIsEmpty: Bool,
        floatingView: @escaping (_ scrollOffset: CGFloat) -> FloatingContent,
        contentView: @escaping () -> Content
    ) {
        self.floatingViewIsEmpty = floatingViewIsEmpty
        self.radialOffset = defaultRadialOffset
        self.floatingView = floatingView

        self.contentView = contentView
    }

    var body: some View {
        ZStack {
            RadialBackgroundView(
                topOffset: Theme.Spacings.XXL.toDimensionMax(),
                radialOffset: radialOffset.toDimensionMax()
            )

            ScrollView {
                GeometryReader { geometry in
                    Color.clear
                        .preference(key: ScrollOffsetPreferenceKey.self, value: (geometry.frame(in: .global).minY) )

                }
                .frame(height: 0)
                .offset(y: viewTopOffset)

                headerView()
                    .zIndex(1000)

                floatingView(self.offsetY)
                    .zIndex(1001)

                ZStack {
                    self.contentView()
                }
                .offset(y: floatingViewIsEmpty ? Theme.Spacings.XL : 0.0)
            }
            .scrollIndicators(.hidden)
            .sizeReader(size: $scrollViewSize)
            .onPreferenceChange(ScrollOffsetPreferenceKey.self) { value in
                self.offsetY = value
                self.radialOffset = max(0, defaultRadialOffset + offsetY)
                self.headerProgress = (-offsetY + viewTopOffset) / (headerHeight - minimumHeaderHeight - viewTopOffset)

                self.headerOverlayOpacity = min(max(headerProgress, 0), 1)
                self.headerScale = min(max(1 - (headerProgress * 0.5), 0), 1.2)
                self.headerImageScale = min(max(1 - (headerProgress * 0.5), 0.5), 1.2)
            }
        }
        .ignoresSafeArea(.all)
    }

    @ViewBuilder
    private func headerView() -> some View {
        HeaderView(
            headerHeight: headerHeight,
            minimumHeaderHeight: minimumHeaderHeight,
            offsetY: offsetY,
            overlayColor: headerOverlayColor,
            overlayOpacity: headerOverlayOpacity,
            scale: headerScale,
            imageScale: headerImageScale,
            contentPaddingVertical: safeAreaInsets.top / 2
        )
    }
}

// TODO: screenshot tests
struct BackgroundView_Previews: PreviewProvider {
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
                .init(name: "Default", state: Data() )
            ],

            configure: { data in
                BackgroundView(
                    floatingViewIsEmpty: true,
                    floatingView: {_ in EmptyView()},
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
