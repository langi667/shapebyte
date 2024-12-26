//
//  OverviewView.swift
//  ShapeByte
//
//  Created by Lang, Stefan [ShapeByte Tech] on 02.08.24.
//

import SwiftUI
import shared
import PreviewSnapshots

struct HomeRootView: View, Loggable {
    @Env
    private var environment

    @State
    private var viewModel: HomeRootViewModel

    @State
    private var viewState: UIState = UIState.Idle()

    var body: some View {
        Group {
            switch onEnum(of: viewState) {
                case .idle:
                    Text("Loading...") // TODO: loading state
                case .loading:
                    Text("Loading...") // TODO: loading state
                case .content(let content):
                    contentOrErrorView(content: content)
            }
        }.task {
            for await currState in viewModel.state {
                self.viewState = currState
            }
        }
        .onAppear { viewModel.update() }
    }

    init() {
        self.viewModel = DPI.shared.homeRootViewModel()
    }

    @ViewBuilder
    func contentOrErrorView(content: UIState.Content) -> some View {
        if let viewData: HomeRootViewData = content.viewData() {
            HomeRootContentView(
                data: viewData
            )
        } else {
            Text("Something went wrong...") // TODO: error state
        }
    }
}

struct HomeRootContentView: View {
    let data: HomeRootViewData
    private let paddingHorizontal: CGFloat = Theme.spacings.small

    @State
    private var buildPerformPersistViewSize: CGSize = .zero

    private var headerHeight: CGFloat {
        ContentViewAppearance.headerHeight
    }

    private var minimumHeaderHeight: CGFloat {
        ContentViewAppearance.minimumHeaderHeight
    }

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
    }

    @ViewBuilder
    private func content() -> some View {
        VStack(alignment: .leading, spacing: 0) {
            quickWorkoutsView(data.quickWorkouts)
            Spacer().frame(height: Theme.spacings.small + Theme.spacings.tiny)
            recentHistoryView(data.recentHistory)
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
                        .padding(.top, Theme.spacings.small)
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

                    QuickWorkoutsListView(quickWorkouts: quickWorkouts) {
                        NavigationHandler.shared.navigateToQuickWorkout(workoutId: $0.id)
                    }
                }
            }
        }
    }

    @ViewBuilder
    private func sectionTitle(_ title: String) -> some View {
        Text(title)
            .titleSmall()
            .foregroundStyle(Color.white) // TODO: define font color
    }

    @ViewBuilder
    private func buildPerformPersistView(scrollPosY: CGFloat) -> some View {
        BuildPerformPersistView()
            .padding(.top, Theme.spacings.medium)
            .sizeReader(size: $buildPerformPersistViewSize)
            .scaleEffect(buildPerformPersistViewScale(scrollPosY: scrollPosY), anchor: .top)
            .offset(y: buildPerformPersistViewOffset(scrollPosY: scrollPosY))
    }

    private func buildPerformPersistViewOffset(scrollPosY: CGFloat) -> CGFloat {
        let offset: CGFloat
        let defaultOffset = -Theme.spacings.medium
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

struct HomeRootView_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<HomeRootViewData> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Empty",
                    state: HomeRootViewData(
                        currWorkoutScheduleEntry: nil,
                        recentHistory: [],
                        quickWorkouts: []
                    )
                ),
                .init(
                    name: "History only",
                    state: HomeRootViewData(
                        currWorkoutScheduleEntry: nil,
                        recentHistory: WorkoutHistoryPreviewDataProvider
                            .shared
                            .previewData,
                        quickWorkouts: []
                    )
                ),
                .init(
                    name: "Quick Workouts only",
                    state: HomeRootViewData(
                        currWorkoutScheduleEntry: nil,
                        recentHistory: [],
                        quickWorkouts: QuickWorkoutsPreviewDataProvider
                            .shared
                            .previewData
                    )
                ),
                .init(
                    name: "All Data",
                    state: HomeRootViewData(
                        currWorkoutScheduleEntry: nil,
                        recentHistory: WorkoutHistoryPreviewDataProvider
                            .shared
                            .previewData,
                        quickWorkouts: QuickWorkoutsPreviewDataProvider
                            .shared
                            .previewData
                    )
                )
            ],
            
            configure: { data in
                HomeRootContentView(
                    data: data
                ).snapshotSetupFullScreen()
            }
        )
    }
}
