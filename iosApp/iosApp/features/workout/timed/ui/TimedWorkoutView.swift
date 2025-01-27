//
//  TimedWorkoutView.swift
//  iosApp
//
//  Created by Stefan Lang on 09.12.24.
//  Copyright © 2024 orgName. All rights reserved.
//

import SwiftUI
import PreviewSnapshots
import shared

// TODO: on disappear: stop
@MainActor
struct TimedWorkoutView: View {
    @State
    private var viewModel: TimedWorkoutViewModel

    @State
    private var viewState: UIState = UIState.Idle()

    let workoutId: Int32

    init(
        workoutId: Int32
    ) {
        self.workoutId = workoutId
        self.viewModel = DPI.shared.timedWorkoutViewModel()
    }

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
        }
        .task {
            for await currState in viewModel.state {
                self.viewState = currState
            }
        }
        .onAppear {
            viewModel.load(workoutId: self.workoutId)
        }
    }

    @ViewBuilder
    func contentOrErrorView(content: UIState.Content) -> some View {
        if let viewData: TimedWorkoutViewData = content.viewData() {
            TimedWorkoutContentView(
                data: viewData,
                onPlayClicked: { viewModel.start() },
                onPauseClicked: viewData.pauseButtonState.onClickAction,
                onStopClicked: viewData.stopButtonState.onClickAction
            )
        } else {
            Text("Something went wrong...") // TODO: error state
        }
    }
}

struct TimedWorkoutContentView: View {
    @SafeAreaInfo
    var safeAreaInfo

    let data: TimedWorkoutViewData
    let onPlayClicked: () -> Void
    let onPauseClicked: (() -> Void)?
    let onStopClicked: (() -> Void)?

    @State
    private var timerViewSize: CGSize = .zero
    let animationDuration: TimeInterval = Theme.AnimationDuration.short

    init (
        data: TimedWorkoutViewData,
        onPlayClicked: @escaping () -> Void = {},
        onPauseClicked: (() -> Void)? = nil,
        onStopClicked: (() -> Void)? = nil
    ) {
        self.data = data
        self.onPlayClicked = onPlayClicked
        self.onPauseClicked = onPauseClicked
        self.onStopClicked = onStopClicked
    }

    var body: some View {
        ZStack(alignment: .top) {
            BackgroundView(
                radialColor: data.backgroundColor.color
            ) {
                AnyView(
                    TimerView(
                        title: self.data.title,
                        remaining: self.data.remaining,
                        elapsedTotal: self.data.elapsedTotal,
                        remainingTotal: self.data.remainingTotal
                    )
                    .padding(.top, safeAreaInfo.top)
                    .padding(.horizontal, Theme.spacings.small)
                    .sizeReader(size: $timerViewSize)
                )
            }

            HStack(alignment: .center) {
                PauseButton { self.onPauseClicked?() }
                    .opacity(data.pauseButtonVisible ? 1 : 0)
                    .animation(.easeIn(duration: animationDuration), value: data.pauseButtonVisible)
                    .transition(.scale)

                Spacer().frame(width: Theme.spacings.small)

                ExerciseView(
                    image: data.exerciseImage,
                    playButtonState: data.playButtonState,
                    progress: Double(data.progressTotal),
                    setDuration: Double(data.setDuration) / 1000.0,
                    animationDuration: animationDuration
                )

                Spacer().frame(width: Theme.spacings.small)
                StopButton {  self.onStopClicked?()  }
                    .opacity(data.stopButtonVisible ? 1 : 0)
                    .animation(.easeIn(duration: animationDuration), value: data.stopButtonVisible)
                    .transition(.scale)

            }
            .offset(
                // TODO: + Theme.spacings. can be removed once Arc is fixed
                // See: SB-63
                y: timerViewSize.height + Theme.spacings.large
            )

            AppBar(title: data.title) {}
        }
    }
}

private struct ExerciseView: View {
    let image: ImageAsset?
    let playButtonState: ButtonState
    let progress: Double
    let setDuration: TimeInterval
    let animationDuration: TimeInterval

    @State var opacity: Double = 1
    @State private var timer = CountdownTimer()
    @State private var setProgress: Double = 0

    let controlSize: CGFloat = RoundedImageButtonAppearance
        .large
        .size
        .toDimensionMax()

    init(
        image: ImageAsset?,
        playButtonState: ButtonState,
        progress: Double,
        setDuration: TimeInterval,
        animationDuration: TimeInterval = Theme.AnimationDuration.short
    ) {
        self.image = image
        self.playButtonState = playButtonState

        self.progress = progress
        self.animationDuration = animationDuration
        self.setDuration = setDuration
    }

    var body: some View {
        ZStack {
            PlayButton(
                action: {
                    self.playButtonState.onClickAction?()
                }
            ) .opacity(opacity)

            ZStack {
                if let imageNotNull = image {
                    Image(imageAsset: imageNotNull)
                        .round()
                        .frame(
                            width: controlSize - (12.0).toDimensionMax(),
                            height: controlSize - (12.0).toDimensionMax()
                        )
                }

                PercentageRing(
                    ringWidth: 6,
                    percent: progress * 100,
                    backgroundColor: .clear,
                    foregroundColors: [Theme.Colors.secondaryColor]
                )

            }.opacity(1 - opacity)
                .frame(
                    width: RoundedImageButtonAppearance.large.size.toDimensionMax(),
                    height: RoundedImageButtonAppearance.large.size.toDimensionMax()
                )

        }.onChange(of: playButtonState, initial: true) { _, newState in
            withAnimation(.easeInOut(duration: animationDuration)) {
                self.opacity = newState.isVisible ? 1 : 0
            }
        }
    }
}

private struct TimerView: View, Loggable {
    let title: String
    let remaining: String
    let elapsedTotal: String
    let remainingTotal: String

    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            Spacer().frame(height: Theme.spacings.large)
            Text(remaining)
                .displayLarge()
                .frame(maxWidth: .infinity)

            Spacer().frame(height: Theme.spacings.small)
            HStack(alignment: .top) {
                TimeLabel(
                    text: elapsedTotal,
                    label: "Elapsed",
                    alignment: .leading
                )

                Spacer()

                TimeLabel(
                    text: remainingTotal,
                    label: "Remaining",
                    alignment: .trailing
                )
            }.frame(maxWidth: .infinity)
        }
    }
}

private struct TimeLabel: View {
    let text: String
    let label: String
    let alignment: HorizontalAlignment
    var body: some View {
        VStack(alignment: alignment, spacing: 0) {
            Text(text).bodyMedium()
            Text(label).labelSmall()
        }
    }
}

// TODO: snapshot tests
struct TimedWorkoutView_Previews: PreviewProvider {
    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<TimedWorkoutViewData> {
        PreviewSnapshots(
            configurations: [
                .init(
                    name: "Loaded",
                    state: TimedWorkoutViewData(
                        title: "HIIT Workout",
                        remaining: "02:00",
                        setDuration: 2,
                        elapsedTotal: "00:00",
                        remainingTotal: "02:00",
                        progressTotal: 0,
                        playButtonState: ButtonState.Visible(onClick: {}),
                        pauseButtonState: ButtonState.Hidden.shared,
                        stopButtonState: ButtonState.Hidden.shared,
                        exercise: ExerciseExecutionInfo(
                            exercise: Exercise.companion.None,
                            intervalExerciseInfo: IntervalExerciseInfo.none
                        ),
                        backgroundColor: ColorDescriptorBackground.shared, launchState: .idle
                    )
                )
            ],

            configure: { data in
                TimedWorkoutContentView(
                    data: data
                ).snapshotSetupFullScreen()
            }
        )
    }
}
