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

    private let minimumHeaderHeight: CGFloat = Theme.Spacings.XXL
    private let headerOverlayColor: Color = Theme.Colors.secondaryColor

    private var headerHeight: CGFloat {
        self.scrollViewSize.height * 0.15
    }

    private let viewTopOffset: CGFloat = 8
    private let paddingHorizontal: CGFloat = Theme.Spacings.S

    var body: some View {
        ZStack {
            BackgroundView(
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

                pendingExerciseActionView()
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

                NSLog("headerOverlayOpacity: \(headerOverlayOpacity)")
            }
        }
        .onAppear { viewModel.onViewAppeared() }
        .ignoresSafeArea(.all)
    }

    @ViewBuilder
    private func headerView() -> some View {
        GeometryReader {_ in
            ZStack {
                Rectangle()
                    .fill(headerOverlayColor.opacity(headerOverlayOpacity))

                HStack {
                    VStack(alignment: .leading) {
                        Text("Welcome back")
                            .body()
                            .foregroundStyle(Color.white)

                        Text("Stefan")
                            .h2()
                            .foregroundStyle(Color.white)
                    }
                    .scaleEffect(headerScale, anchor: .leading)

                    Spacer()

                    Image("Logo")
                        .resizable()
                        .frame(width: Theme.Spacings.XL * headerImageScale,
                               height: Theme.Spacings.XL * headerImageScale)
                        .clipShape(Circle())

                }.padding(.horizontal, paddingHorizontal)
                    .padding(.top, safeAreaInsets.top / 2)
            }
            .frame(
                height: (headerHeight + offsetY) < minimumHeaderHeight ? minimumHeaderHeight : (headerHeight + offsetY), alignment: .bottom)
            .offset(y: -offsetY)
        }.frame(height: headerHeight)
    }

    @ViewBuilder
    private func pendingExerciseActionView() -> some View {
            Button(action: {
                self.viewModel.onCurrentWorkoutSelected()
            }) {
                PendingExerciseView(progress: $viewModel.currWorkoutScheduleEntryProgress)
                    .padding(.top, Theme.Spacings.M)
                    .sizeReader(size: $pendingExerciseSize)

            }
            .scaleEffect(pendingExerciseScale(), anchor: .top)
            .offset(y: pendingExerciseOffset())
    }

    private func pendingExerciseOffset() -> CGFloat {
        let offset: CGFloat
        let threshold = (minimumHeaderHeight / 2 + safeAreaInsets.top + Theme.Spacings.XS ) * -1

        if offsetY <=  threshold {
            offset = -offsetY + threshold
        } else {
            offset = 0
        }

        return offset
    }

    private func pendingExerciseScale() -> CGFloat {
        let offset = pendingExerciseOffset()
        if offset == 0 {
            return 1
        }

        let scale = max(0.3, 1 - (offset / pendingExerciseSize.height))
        return scale
    }
}

#Preview {

    HomeRootView(
        viewModel: HomeRootViewModelWrapper()
    )
}
