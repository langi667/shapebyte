//
//  OverviewView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [Shape Byte Tech] on 02.08.24.
//

import SwiftUI
import shared

struct HomeRootView: View {
     private static let defaultRadialOffset: CGFloat = Theme.Spacings.XXXL

    @ObservedObject var viewModel: HomeRootViewModelWrapper
    @Environment(\.safeAreaInsets) var safeAreaInsets: EdgeInsets

    @State private var offsetY: CGFloat = 0
    @State private var scrollViewSize: CGSize = .zero

    @State private var radialOffset: CGFloat = defaultRadialOffset
    @State private var pendingExerciseSize: CGSize = .zero
    @State private var headerProgress: CGFloat = .zero

    @State private var headerOverlayOpacity: CGFloat = .zero
    @State private var headerScale: CGFloat = .zero
    @State private var headerImageScale: CGFloat = .zero

    private let headerOverlayColor: Color = Theme.Colors.secondaryColor

    private var headerHeight: CGFloat {
        (Theme.Dimensions.large + safeAreaInsets.top / 2).toDimensionMax()
    }

    private var minimumHeaderHeight: CGFloat {
        headerHeight * 0.6
    }

    private let viewTopOffset: CGFloat = 8
    private let paddingHorizontal: CGFloat = Theme.Spacings.S

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

                buildPerformPersistView()
                    .zIndex(1001)

                ForEach(viewModel.recentHistory) { entry in
                    WorkoutHistoryEntryView(entry: entry)
                        .padding(.top, Theme.Spacings.S)
                }.padding(.horizontal, paddingHorizontal)
            }
            .scrollIndicators(.hidden)
            .sizeReader(size: $scrollViewSize)
            .onPreferenceChange(ScrollOffsetPreferenceKey.self) { value in
                self.offsetY = value
                self.radialOffset = max(0, Self.defaultRadialOffset + offsetY)
                self.headerProgress = (-offsetY + viewTopOffset) / (headerHeight - minimumHeaderHeight - viewTopOffset)

                self.headerOverlayOpacity = min(max(headerProgress, 0), 1)
                self.headerScale = min(max(1 - (headerProgress * 0.5), 0), 1.2)
                self.headerImageScale = min(max(1 - (headerProgress * 0.5), 0.5), 1.2)
            }
        }
        .onAppear { viewModel.onViewAppeared() }
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

    @ViewBuilder
    private func buildPerformPersistView() -> some View {
        BuildPerformPersistView()
            .padding(.top, Theme.Spacings.M)
            .sizeReader(size: $pendingExerciseSize)
            .scaleEffect(buildPerformPersistViewScale(), anchor: .top)
            .offset(y: buildPerformPersistViewOffset())
    }

    private func buildPerformPersistViewOffset() -> CGFloat {
        let offset: CGFloat
        let threshold = (minimumHeaderHeight / 2 + pendingExerciseSize.height / 2) * -1

        if offsetY <=  threshold {
            offset = -offsetY + threshold
        } else {
            offset = 0
        }

        return offset
    }

    private func buildPerformPersistViewScale() -> CGFloat {
        let offset = buildPerformPersistViewOffset()
        if offset == 0 {
            return 1
        }

        let maxScale = maxBuildPerformPersistViewScale()
        let scale = max(maxScale, 1 - (offset / pendingExerciseSize.height))
        return scale
    }

    private func maxBuildPerformPersistViewScale() -> CGFloat {
        let screenCategory = DPI.shared.dimensionProvider().deviceSizeCategory
        let retVal: CGFloat

        switch onEnum(of: screenCategory) {
        case .xSmall:
            retVal = 0.65
        case .small:
            retVal = 0.65
        default:
            retVal = 0.6
        }

        return retVal
    }
}

#Preview {

    HomeRootView(
        viewModel: HomeRootViewModelWrapper()
    )
}
