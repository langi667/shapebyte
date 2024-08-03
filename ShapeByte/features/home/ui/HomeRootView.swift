//
//  OverviewView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [RTL Tech] on 02.08.24.
//

import SwiftUI

// TODO: move
struct ScrollOffsetPreferenceKey: PreferenceKey {
    typealias Value = CGFloat
    static var defaultValue: CGFloat = 0

    static func reduce(value: inout CGFloat, nextValue: () -> CGFloat) {
        value += nextValue()
    }
}

struct HomeRootView: View {
    private static let defaultRadialOffset: CGFloat = Theme.Spacing.XXXL

    @ObservedObject var viewModel: HomeRootViewModel

    @State private var offsetY: CGFloat = 0
    @State private var size: CGSize = .zero
    @State private var safeAreaInsets: EdgeInsets = .init()
    @State private var radialOffset: CGFloat = defaultRadialOffset
    @State private var pendingExerciseSize: CGSize = .zero

    let minimumHeaderHeight: CGFloat = Theme.Spacing.XXL

    private var headerHeight: CGFloat {
        self.size.height * 0.15 + safeAreaInsets.top
    }

    var body: some View {
        ZStack {
            BackgroundView(
                topOffset: Theme.Spacing.XXL,
                radialOffset: radialOffset
            )

            ScrollView {
                GeometryReader { geometry in
                    Color.clear
                        .preference(key: ScrollOffsetPreferenceKey.self, value: geometry.frame(in: .global).minY)
                        .onAppear {
                            safeAreaInsets = geometry.safeAreaInsets
                        }
                }
                .frame(height: 0).hidden()

                HeaderView()
                    .zIndex(1000)

                PendingExerciseActionView()
                    .zIndex(1001)

                ForEach (0..<15, id:\.self) { _ in
                    WorkoutHistoryEntryView()
                        .padding(.top, Theme.Spacing.S)
                }
            }
            .scrollIndicators(.hidden)
            .sizeReader(size: $size)
            .onPreferenceChange(ScrollOffsetPreferenceKey.self) { value in
                offsetY = value
                radialOffset = Self.defaultRadialOffset + offsetY
            }
        }
        .onAppear { viewModel.onViewAppeared() }
        .ignoresSafeArea()
    }

    @ViewBuilder
    private func HeaderView() -> some View {
        let progress = -offsetY / (headerHeight - minimumHeaderHeight)

        GeometryReader {_ in
            ZStack {
                Rectangle()
                    .fill(Color(
                        red: 104 / 255,
                        green: 187 / 255,
                        blue: 193 / 255)
                    ).opacity(headerBGOpacityFrom(progress: progress))

                HStack {
                    VStack(alignment: .leading) {
                        Text("Welcome back")
                            .body()
                            .foregroundStyle(Color.white)

                        Text("Stefan")
                            .h2()
                            .foregroundStyle(Color.white)
                    }
                    .scaleEffect( headerScaleFrom(progress: progress), anchor: .leading)

                    Spacer()

                    Image("Logo")
                        .resizable()
                        .frame(width: Theme.Spacing.XL, height: Theme.Spacing.XL)
                        .clipShape(Circle())
                }
            }
            .frame(height: (headerHeight + offsetY) < minimumHeaderHeight ? minimumHeaderHeight : (headerHeight + offsetY), alignment: .bottom)
            .offset(y: -offsetY)
        }.frame(height: headerHeight)
    }
    
    @ViewBuilder
    private func PendingExerciseActionView() -> some View {
            Button(action: {
                self.viewModel.onCurrentWorkoutSelected()
            }) {
                PendingExerciseView(progress: $viewModel.currWorkoutScheduleEntryProgress)
                    .padding(.top, Theme.Spacing.M)
                    .sizeReader(size: $pendingExerciseSize)

            }
            .scaleEffect(pendingExerciseScale(), anchor: .top)
            .offset(y: pendingExerciseOffset())
    }

    private func pendingExerciseOffset() -> CGFloat {
        let offset: CGFloat
        let threshold = (minimumHeaderHeight + safeAreaInsets.top + Theme.Spacing.L) * -1

        if offsetY <=  threshold {
            offset = -offsetY + threshold
        }
        else {
            offset = 0
        }

        return offset
    }

    private func pendingExerciseScale() -> CGFloat {
        let offset = pendingExerciseOffset()
        if offset == 0 {
            return 1
        }
        
        let scale = max(0.5, 1 - (offset / pendingExerciseSize.height))
        return scale
    }

    private func headerScaleFrom(progress: CGFloat) -> CGFloat {
        var scale =  1 - (progress * 0.5)

        if scale < 0 {
            scale = 0
        } else if scale > 1.2 {
            scale = 1.2
        }

        return scale
    }

    private func headerBGOpacityFrom(progress: CGFloat) -> CGFloat {
        var scale =  progress * 0.3

        if scale < 0 {
            scale = 0
        } else if scale > 1 {
            scale = 1
        }

        return scale
    }
}


struct HomeHeaderView: View {
    var body: some View {
        HStack {
            VStack(alignment: .leading) {
                Text("Welcome back")
                    .body()
                    .foregroundStyle(Color.white)

                Text("Stefan")
                    .h2()
                    .foregroundStyle(Color.white)
            }

            Spacer()

            Image("Logo")
                .resizable()
                .frame(width: Theme.Spacing.XL, height: Theme.Spacing.XL)
                .clipShape(Circle())

        }
    }
}

#Preview {
    HomeRootView(viewModel: HomeModule.homeRootViewModel())
}
