import SwiftUI
import shared
import PreviewSnapshots

struct ContentView<Content: View>: View {
    @ViewBuilder
    let contentView: () -> Content

    @ViewBuilder
    let floatingView: (_ scrollOffset: Double) -> AnyView

    private let defaultRadialOffset: Double = .spacingXxxLarge

    @SafeAreaInfo
    private var safeAreaInsets: EdgeInsets

    @Env
    private var environment

    @State private var offsetY: Double = 0
    @State private var scrollViewSize: CGSize = .zero

    @State private var radialOffset: Double
    @State private var pendingExerciseSize: CGSize = .zero
    @State private var headerProgress: Double = .zero

    @State private var headerOverlayOpacity: Double = .zero
    @State private var headerScale: Double = .zero
    @State private var headerImageScale: Double = .zero

    private let headerOverlayColor: Color = .SBSecondary
    private let floatingViewIsEmpty: Bool

    private var headerHeight: Double {
        ContentViewAppearance.headerHeight
    }

    private var minimumHeaderHeight: Double {
        ContentViewAppearance.minimumHeaderHeight
    }

    private let viewTopOffset: Double = 8
    private let paddingHorizontal: Double = .spacingSmall

    init(
        floatingViewIsEmpty: Bool,
        floatingView: @escaping (_ scrollOffset: Double) -> AnyView,
        contentView: @escaping () -> Content
    ) {
        self.floatingViewIsEmpty = floatingViewIsEmpty
        self.radialOffset = defaultRadialOffset
        self.floatingView = floatingView

        self.contentView = contentView
    }

    var body: some View {
        ZStack(alignment: .topLeading) {
            BackgroundView(
                topOffset: .spacingXxLarge.toDimensionMax(),
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
                .offset(y: floatingViewIsEmpty ? .spacingXLarge : 0.0)
            }
            .scrollIndicators(.hidden)
            .sizeReader(size: $scrollViewSize)
            .onPreferenceChange(ScrollOffsetPreferenceKey.self) { value in
                self.offsetY = environment.isRunningUnitTests ? 8 : value
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

struct ContentView_Previews: PreviewProvider {
    struct Data {
        let texts = [
            "Oh Hello there",
            "Obi Wan",
            "Anakin Skywalker"
        ]

        let floatingView: Bool
    }

    static var previews: some View {
        snapshots.previews.previewLayout(.device)
    }

    static var snapshots: PreviewSnapshots<Data> {
        PreviewSnapshots(
            configurations: [
                .init(name: "No Floating View", state: Data(floatingView: false) ),
                .init(name: "With Floating View", state: Data(floatingView: true) )

            ],

            configure: { data in
                ContentView(
                    floatingViewIsEmpty: data.floatingView,
                    floatingView: {_ in
                        if data.floatingView {
                            return AnyView(BuildPerformPersistView())
                        } else {
                            return AnyView(EmptyView())
                        }
                    },
                    contentView: {
                        VStack {
                            ForEach(data.texts, id: \.self) {
                                Text($0).bodyMedium()
                            }
                        }
                    }
                ).snapshotSetupFullScreen()
            }
        )
    }
}
