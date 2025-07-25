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
        workoutId: Int32,
        navHandling: any NavigationRequestHandling
    ) {
        self.workoutId = workoutId
        self.viewModel = SharedModule.shared.timedWorkoutViewModel(
            navHandler: navHandling
        )
    }

    var body: some View {
        Group {
            switch onEnum(of: viewState) {
            case .idle:
                Text("Loading...") // TODO: loading state
            case .loading:
                Text("Loading...") // TODO: loading state
            case .content(let content):
                contentOrErrorView(
                    content: content,
                    onBack: {viewModel.intent(intent: TimedWorkoutUIIntent.OnCloseClicked()) }
                )
            }
        }
        .task {
            for await currState in viewModel.state {
                self.viewState = currState
            }
        }
        .onAppear {
            viewModel.intent(intent: TimedWorkoutUIIntent.Load(workoutId: self.workoutId))
        }
    }

    @ViewBuilder
    func contentOrErrorView(
        content: UIState.Content,
        onBack: @escaping () -> Void = {}
    ) -> some View {
        if let viewData: TimedWorkoutViewData = content.viewData() {
            TimedWorkoutContentView(
                data: viewData,
                onPlayClicked: { viewModel.intent(intent: TimedWorkoutUIIntent.Start() ) },
                onPauseClicked: viewData.pauseButtonState.onClickAction,
                onStopClicked: viewData.stopButtonState.onClickAction,
                onCloseClicked: onBack
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
    let onCloseClicked: (() -> Void)?

    @State
    private var timerViewSize: CGSize = .zero
    let animationDuration: TimeInterval = .animationDurationShort

    init (
        data: TimedWorkoutViewData,
        onPlayClicked: @escaping () -> Void = {},
        onPauseClicked: (() -> Void)? = nil,
        onStopClicked: (() -> Void)? = nil,
        onCloseClicked: (() -> Void)? = nil
    ) {
        self.data = data
        self.onPlayClicked = onPlayClicked
        self.onPauseClicked = onPauseClicked
        self.onStopClicked = onStopClicked
        self.onCloseClicked = onCloseClicked
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
                    .padding(.horizontal, .spacingSmall)
                    .sizeReader(size: $timerViewSize)
                )
            }

            HStack(alignment: .center) {
                PauseButton { self.onPauseClicked?() }
                    .opacity(data.pauseButtonVisible ? 1 : 0)
                    .animation(.easeIn(duration: animationDuration), value: data.pauseButtonVisible)
                    .transition(.scale)

                Spacer().frame(width: .spacingSmall)

                ExerciseView(
                    image: data.exerciseImage,
                    playButtonState: data.playButtonState,
                    progress: Double(data.progressTotal),
                    setDuration: Double(data.setDuration) / 1000.0,
                    animationDuration: animationDuration
                )

                Spacer().frame(width: .spacingSmall)
                StopButton {  self.onStopClicked?()  }
                    .opacity(data.stopButtonVisible ? 1 : 0)
                    .animation(.easeIn(duration: animationDuration), value: data.stopButtonVisible)
                    .transition(.scale)

            }
            .offset(
                // TODO: + Theme.spacings. can be removed once Arc is fixed
                // See: SB-63
                y: timerViewSize.height + .spacingLarge
            )

            AppBar(title: data.title) {
                onCloseClicked?()
            }
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
        animationDuration: TimeInterval = .animationDurationShort
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
                    foregroundColors: [.SBSecondary]
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
            Spacer().frame(height: .spacingLarge)
            Text(remaining)
                .displayLarge()
                .frame(maxWidth: .infinity)

            Spacer().frame(height: .spacingSmall)
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
