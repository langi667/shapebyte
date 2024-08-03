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

                Button(action: {
                    self.viewModel.onCurrentWorkoutSelected()
                }) {
                    PendingExerciseView(progress: $viewModel.currWorkoutScheduleEntryProgress)
                        .padding(.top, Theme.Spacing.L)
                }.zIndex(1001)

                WorkoutHistoryEntryView()
                WorkoutHistoryEntryView()
                WorkoutHistoryEntryView()
                WorkoutHistoryEntryView()
                WorkoutHistoryEntryView()
                WorkoutHistoryEntryView()
                WorkoutHistoryEntryView()
                WorkoutHistoryEntryView()

            }
            .scrollIndicators(.hidden)
            .sizeReader(size: $size)
            .onPreferenceChange(ScrollOffsetPreferenceKey.self) { value in
                offsetY = value
                radialOffset = Self.defaultRadialOffset + offsetY
            }
        }
        .background {

        }
        .ignoresSafeArea()
    }

    @ViewBuilder
    private func HeaderView() -> some View {
        let headerHeight = self.size.height * 0.15 + safeAreaInsets.top
        let minimumHeaderHeight: CGFloat = 80
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
                    }.scaleEffect( headerScaleFrom(progress: progress), anchor: .leading)

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

//    var body: some View {
//        ZStack {
//            BackgroundView()
//            VStack(spacing: Theme.Spacing.S) {
//                HomeHeaderView()
//                    .padding(.top, Theme.Spacing.M)
//
//                Button(action: {
//                    self.viewModel.onCurrentWorkoutSelected()
//                }) {
//                    PendingExerciseView(progress: $viewModel.currWorkoutScheduleEntryProgress)
//                        .padding(.top, Theme.Spacing.XXL)
//                }
//
//                Text("Recent Workouts")
//                    .subtitle()
//                    .foregroundStyle(Color.white)
//                    .padding(.top, Theme.Spacing.XS)
//                    .frame(maxWidth: .infinity, alignment: .leading)
//
//                WorkoutHistoryEntryView()
//                WorkoutHistoryEntryView()
//                WorkoutHistoryEntryView()
//
//                Spacer()
//            }
//            .padding(.top, Theme.Spacing.XS)
//            .padding(.horizontal, Theme.Spacing.S)
//        }.onAppear {
//            viewModel.onViewAppeared()
//        }
//    }

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
