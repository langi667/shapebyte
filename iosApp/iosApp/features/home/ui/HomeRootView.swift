//
//  OverviewView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [ShapeByte Tech] on 02.08.24.
//

import SwiftUI
import shared

struct HomeRootView: View {
    @ObservedObject var viewModel: HomeRootViewModelWrapper
    private var safeAreaInsets: EdgeInsets {
        SafeAreaProvider.shared.insets
    }

    @State private var buildPerformPersistViewSize: CGSize = .zero

    private var headerHeight: CGFloat {
        ContentViewAppearance.headerHeight
    }

    private var minimumHeaderHeight: CGFloat {
        ContentViewAppearance.minimumHeaderHeight
    }

    private let paddingHorizontal: CGFloat = Theme.Spacings.S

    var body: some View {
        ContentView(
            floatingViewIsEmpty: false,
            floatingView: { offsetY in
                AnyView(buildPerformPersistView(scrollPosY: offsetY))
            },
            contentView: {
                content()
            }
        )
        .onAppear { viewModel.onViewAppeared() }
        .ignoresSafeArea(.all)
    }

    @ViewBuilder
    private func content() -> some View {
        VStack(alignment: .leading, spacing: 0) {
            quickWorkoutsView(viewModel.quickWorkouts)
            Spacer().frame(height: Theme.Spacings.S + Theme.Spacings.XS)
            recentHistoryView(viewModel.recentHistory)
        }
    }

    @ViewBuilder
    private func recentHistoryView(_ recentHistory: [WorkoutHistoryEntry]) -> some View {
        if recentHistory.isEmpty {
            EmptyView()
        } else {
            LazyVStack(alignment: .leading, spacing: 0) {
                sectionTitle("Recent Workouts")

                ForEach(recentHistory) { entry in
                    WorkoutHistoryEntryView(entry: entry)
                        .padding(.top, Theme.Spacings.S)
                }
            }.padding(.horizontal, paddingHorizontal) }
    }

    @ViewBuilder
    private func quickWorkoutsView(_ quickWorkouts: [Workout]) -> some View {
        if quickWorkouts.isEmpty {
            EmptyView()
        } else {
            ZStack(alignment: .leading) {
                VStack(alignment: .leading) {
                    sectionTitle("Quick Workouts")
                        .padding(.leading, paddingHorizontal)

                    QuickWorkoutsListView(quickWorkouts: quickWorkouts)
                }
            }
        }
    }

    @ViewBuilder
    private func sectionTitle(_ title: String) -> some View {
        Text(title)
            .h3()
            .foregroundStyle(Color.white) // TODO: define font color
    }

    @ViewBuilder
    private func buildPerformPersistView(scrollPosY: CGFloat) -> some View {
        BuildPerformPersistView()
            .padding(.top, Theme.Spacings.M)
            .sizeReader(size: $buildPerformPersistViewSize)
            .scaleEffect(buildPerformPersistViewScale(scrollPosY: scrollPosY), anchor: .top)
            .offset(y: buildPerformPersistViewOffset(scrollPosY: scrollPosY))
    }

    private func buildPerformPersistViewOffset(scrollPosY: CGFloat) -> CGFloat {
        let offset: CGFloat
        let defaultOffset = -Theme.Spacings.M
        let threshold = (minimumHeaderHeight / 2 + buildPerformPersistViewSize.height / 2 + defaultOffset) * -1

        if scrollPosY <=  threshold {
            offset = -scrollPosY + threshold + defaultOffset
        } else {
            offset = defaultOffset
        }

        return offset
    }

    private func buildPerformPersistViewScale(scrollPosY: CGFloat) -> CGFloat {
        let offset = buildPerformPersistViewOffset(scrollPosY: scrollPosY)
        if offset == 0 {
            return 1
        }

        let maxScale = maxBuildPerformPersistViewScale()
        let scale = max(maxScale, 1 - (offset / buildPerformPersistViewSize.height))
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

// TODO: screenshot tests
#Preview {
    HomeRootView(
        viewModel: HomeRootViewModelWrapper()
    )
}
